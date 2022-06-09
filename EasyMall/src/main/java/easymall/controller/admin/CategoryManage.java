package easymall.controller.admin;

import easymall.po.ResponseStatus;
import easymall.pojo.Category;
import easymall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryManage {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/oneCategory")
    public String oneCategory(String name,Model model){
        Category category = categoryService.selectByName(name);
        model.addAttribute("category",category);
        return "/admin/add_category";
    }
    @RequestMapping("/getCategory")
    public String getCategory(Model model){
        List<Category> categories = categoryService.selectAll();
        model.addAttribute("categories",categories);
        return "/admin/category_list";
    }
    @RequestMapping("/deleteCategory")
    public String deleteCategory(String name){
        categoryService.delete(name);
        return "redirect:/admin/getCategory";
    }
    @RequestMapping("/updateCategory")
    @ResponseBody
    public ResponseStatus updateCategory(@RequestBody Category category){
        categoryService.update(category);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus("success");
        return responseStatus;
    }
    @RequestMapping("/insertCategory")
    @ResponseBody
    public ResponseStatus insertCategory(@RequestBody Category category){
        System.out.println(category);
        categoryService.insert(category);
        System.out.println(category);
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setStatus("success");
        return responseStatus;
    }
}
