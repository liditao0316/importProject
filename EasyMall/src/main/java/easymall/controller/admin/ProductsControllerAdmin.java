package easymall.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import easymall.pojo.Massege;
import easymall.pojo.UpdateProduct;
import easymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.controller.BaseController;
import easymall.po.Products;
import easymall.pojo.MyProducts;
import easymall.service.ProductsService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SuppressWarnings("all")
@RequestMapping("/admin")
public class ProductsControllerAdmin extends BaseController{
	@Autowired
	private ProductsService productsService;
	@Autowired
	private UserService userService;

	@RequestMapping("/prodlist")
	public String prodlist(Model model){
		double _minPrice=0;
		double _maxPrice=Double.MAX_VALUE;
		Map<String,Object> map=new HashMap<>();
		map.put("name", "");
		map.put("category", "");
		map.put("minPrice", _minPrice);
		map.put("maxPrice", _maxPrice);
		List<Products> products = productsService.prodlist(map);
		System.out.println(products.size());
		model.addAttribute("products",products);
		return "/admin/prod_list";	
	}
	@RequestMapping("/salasList")
	public String salasList(Model model){
		double _minPrice=0;
		double _maxPrice=Double.MAX_VALUE;
		Map<String,Object> map=new HashMap<>();
		map.put("name", "");
		map.put("category", "");
		map.put("minPrice", _minPrice);
		map.put("maxPrice", _maxPrice);
		List<Products> products = productsService.prodlist(map);
		System.out.println(products.size());
		model.addAttribute("products",products);
		return "/admin/salas_list";
	}
	@RequestMapping("/addprod")
	public String addprod(Model model){
		List<String> categories = productsService.allcategories();
		model.addAttribute("categories",categories);
		model.addAttribute("myproducts",new MyProducts());
		return "/admin/add_prod";
	}
	@RequestMapping("/save")
	public String save(@Valid @ModelAttribute MyProducts myproducts,
			HttpServletRequest request, Model model)
			throws Exception {
		String msg=productsService.save(myproducts,request);
		model.addAttribute("msg",msg);
		return "redirect:/admin/addprod";
	}
	@RequestMapping("/delprod")
	public String delprod(String id){
		Products products = productsService.oneProduct(id);
		File file = new File(products.getImgurl());
		file.delete();
		productsService.delprod(id);
		return "redirect:/admin/prodlist";
	}

	@RequestMapping("/update")
	public String update(@Valid @ModelAttribute UpdateProduct updateProduct,
						  HttpServletRequest request, Model model){
		productsService.update(updateProduct,request);
		return "redirect:/admin/prodlist";
	}

	@RequestMapping("/getProduct")
	public String prodInfo(String id,Model model){
		Products product=productsService.oneProduct(id);
		List<String> allcategories = productsService.allcategories();
		model.addAttribute("categories",allcategories);
		model.addAttribute("updateProduct",product);
		return "/admin/update_product";
	}
}
