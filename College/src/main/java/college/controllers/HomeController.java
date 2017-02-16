package college.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	//show the home page
	@RequestMapping("/")
	public ModelAndView showHome(){
		return new ModelAndView("home");
	}
	
	//show the login page
	@RequestMapping("/login")
	public ModelAndView showLogin(){
		return new ModelAndView("login");
	}
	
	//show the denied page
	@RequestMapping("/denied")
	public ModelAndView showDenied(){
		return new ModelAndView("denied");
	}
	
}
