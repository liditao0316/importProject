package easymall.controller;

import easymall.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaiduMapController {
    @Autowired
    private TestServiceImpl testService;

    @RequestMapping("/baidu_map")
    public String baidu_map(){
        return "baidu_map";
    }

    @RequestMapping("/test")
    public void test(){
        testService.send("123");
    }
}
