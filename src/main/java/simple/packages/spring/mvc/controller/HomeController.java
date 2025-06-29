package simple.packages.spring.mvc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import simple.packages.spring.util.Logger;

@Controller
public class HomeController {
  
	public HomeController() {
		super();
	}

	@RequestMapping(value = "/mvc/", method = RequestMethod.GET)
	public String displayHome(Map<String, Object> model) {
		Logger.info("/ called displayHome()"); 
		return "redirect:index";	// return 'home' tile definition
	}
}
