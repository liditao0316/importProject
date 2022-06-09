package easymall.controller;

import javax.servlet.http.HttpSession;
import javax.sound.midi.MidiMessage;

import easymall.po.User;
import easymall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.mail.javamail.*;

@Controller("indexController")
public class IndexController {

	@Autowired
	private UserService userService;

	@RequestMapping("/index/login")
	public String login(){
		return "login";
	}
	@RequestMapping("/index/regist")
	public String register(){
		return "regist";
	}
	@RequestMapping("/index/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	//邮箱验证
	@RequestMapping("/index/activation")
	public String sendEmail(String id){
		userService.activation(Integer.valueOf(id));
		return "login";
	}
}
