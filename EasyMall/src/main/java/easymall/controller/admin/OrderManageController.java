package easymall.controller.admin;

import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.po.Products;
import easymall.po.User;
import easymall.pojo.OrderInfo;
import easymall.service.OrderService;
import easymall.service.ProductsService;
import easymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class OrderManageController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private UserService userService;

    @RequestMapping("orderList")
    public String orderList(Model model){
        List<OrderInfo> orderInfos = selectBysend();
        model.addAttribute("orderSend_lists",orderInfos);
        return "admin/orderSend_list";
    }

    @RequestMapping("/sendstate")
    public String ordermanage(String id,Model model){
        orderService.sendstate(id);
        return "redirect:/admin/ordermanage";
    }

    @RequestMapping("/ordermanage")
    public String ordermanage(Model model){
        List<OrderInfo> orderInfoByUserId = findOrderInfoByUserId();
        model.addAttribute("orderLists",orderInfoByUserId);
        return "/admin/order_list";
    }

    private List<OrderInfo> findOrderInfoByUserId(){
        List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
        List<Orders> orderList = orderService.selectAll();
        for(Orders order:orderList){
            List<OrderItem> orderItems=orderService.orderitem(order.getId());
            Map<Products,Integer> map=new HashMap<Products,Integer>();
            for(OrderItem orderItem:orderItems){
                Products product=productsService.oneProduct(orderItem.getProduct_id());
                map.put(product, orderItem.getBuynum());
            }
            OrderInfo orderInfo=new OrderInfo();
            orderInfo.setUsername(order.getUsername());
            orderInfo.setOrder(order);
            orderInfo.setMap(map);

            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }

    private List<OrderInfo> selectBysend(){
        List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
        List<Orders> orderList = orderService.selectSend();
        for(Orders order:orderList){
            List<OrderItem> orderItems=orderService.orderitem(order.getId());
            Map<Products,Integer> map=new HashMap<Products,Integer>();
            for(OrderItem orderItem:orderItems){
                Products product=productsService.oneProduct(orderItem.getProduct_id());
                map.put(product, orderItem.getBuynum());
            }
            OrderInfo orderInfo=new OrderInfo();
            orderInfo.setUsername(order.getUsername());
            orderInfo.setOrder(order);
            orderInfo.setMap(map);

            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }
}
