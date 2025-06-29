package simple.packages.spring.mvc.controller;

import java.util.Map;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import simple.packages.spring.util.InternalLogger;

@Controller
public class HomeController {
  
	public HomeController() {
		super();
	}

	@RequestMapping(value = "/mvc/", method = RequestMethod.GET)
	public String displayHome(Map<String, Object> model) {
		InternalLogger.info("/ called displayHome()"); 
		return "index";	// return 'home' tile definition
	}
}
