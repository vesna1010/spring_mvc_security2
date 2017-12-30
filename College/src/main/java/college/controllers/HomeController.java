package college.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	
	@RequestMapping("/")
	public ModelAndView renderHomePage() {
		return new ModelAndView("home");
	}

	@RequestMapping("/login")
	public ModelAndView renderLoginPage() {
		return new ModelAndView("loginForm");
	}

	@RequestMapping("/denied")
	public ModelAndView renderDeniedPage() {
		return new ModelAndView("denied");
	}

}
