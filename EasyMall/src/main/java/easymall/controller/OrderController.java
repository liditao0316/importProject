package easymall.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import easymall.po.*;
import easymall.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.pojo.MyCart;
import easymall.pojo.OrderInfo;
import easymall.service.CartService;
import easymall.service.OrderService;
import easymall.service.ProductsService;
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{ 
	@Autowired
	private CartService cartService; 
	@Autowired
	private OrderService orderService; 
	@Autowired
	private ProductsService productsService;
	@Autowired
	private TestServiceImpl testService;

	@RequestMapping("/order_add")
	public String order_add(String cartIds,Model model){
		String[] arrCartIds=cartIds.split(",");
		List<MyCart> carts=new ArrayList<MyCart>();
		for(String cid:arrCartIds){
			Integer cartID=Integer.parseInt(cid);
			MyCart cart=cartService.findByCartID(cartID); 
			carts.add(cart);
		}
		model.addAttribute("carts",carts); 
		model. addAttribute("cartIds",cartIds); 
		return "order_add";
	}
	@RequestMapping("/addOrder")
	public String addorder(String receiverinfo,String cartIds,HttpSession session) throws JsonProcessingException {
		User user=(User)session.getAttribute("user");
		//数据打包成json文件
		RabbitmqData rabbitmqData = new RabbitmqData(receiverinfo, cartIds, user);
		ObjectMapper objectMapper = new ObjectMapper();
		String s = objectMapper.writeValueAsString(rabbitmqData);
		System.out.println(s);
		testService.send(s);
		return "forward:/order/showorder";

	}
	@RequestMapping("/showorder")
	public String showorder(HttpSession session,Model model){
		User user=(User)session.getAttribute("user");
		List<OrderInfo> orderInfoList=findOrderInfoByUserId(user.getId());
		model.addAttribute("orderInfos",orderInfoList);
		return "order_list";
	}
	private List<OrderInfo> findOrderInfoByUserId(Integer userId){
		 List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
		 List<Orders> orderList = orderService.findOrderByUserId(userId);
		 
		 for(Orders order:orderList){
			 List<OrderItem> orderItems=orderService.orderitem(order.getId());
			 Map<Products,Integer> map=new HashMap<Products,Integer>();
			 for(OrderItem orderItem:orderItems){
				 Products product=productsService.oneProduct(orderItem.getProduct_id());
				 map.put(product, orderItem.getBuynum());
			 }
			 OrderInfo orderInfo=new OrderInfo();
			 orderInfo.setOrder(order);
			 orderInfo.setMap(map);

			 orderInfoList.add(orderInfo);
		 }
		 return orderInfoList;
	}
	@RequestMapping("/delorder")
	public String delorder(String id,Model model){
		orderService.delorder(id);
		return "redirect:/order/showorder";
	}
	@RequestMapping("/payorder")
	public String payorder(String id,Model model) {
		// TODO Auto-generated method stub
		orderService.payorder(id);
		List<OrderItem> orderItems = orderService.orderitem(id);
		for (OrderItem orderItem :orderItems){
			Salas salas = new Salas();
			salas.setNum(orderItem.getBuynum());
			salas.setPid(orderItem.getProduct_id());
			productsService.updateSalas(salas);
		}
		return "redirect:/order/showorder";
	}

	@RequestMapping("/setstate")
	public String getstate(String id,Model model){
		orderService.setstate(id);
		return "redirect:/order/showorder";
	}
}
