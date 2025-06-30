package simple.packages.spring.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import simple.packages.spring.service.ActiveUserServiceImpl;
import simple.packages.spring.util.*;

@Controller
public class LoggedUserController {

	@Autowired
	public LoggedUserController() {
		super();
	}
    
    /*
    /loggedUsers
    ////////////////////////////
    server.servlet.context-path=/mvc
    /////////////////////////////
    http://localhost:8080/loggedUsers <-- RESTTful call
    */

	/*
	@GetMapping is a composed annotation that acts as a shortcut 
	for @RequestMapping(method = RequestMethod.GET).
	*/
    @RequestMapping(value={"/mvc/loggedUsers", "/loggedUsers"}, method=RequestMethod.GET)
    public String getLoggedUsers(ModelMap model) {
    	//http://localhost:8080/loggedUsers
    	InternalLogger.info("/loggedUsers called getLoggedUsers()"); 
    	InternalLogger.info("call LoggedUserController.getLoggedUsers()"); 	
        model.addAttribute("users", 
        	ActiveUserServiceImpl.getInstance().getUserNames());
        InternalLogger.info("/loggedUsers ActiveUserServiceImpl.getInstance().getUserNames()="+
        	ActiveUserServiceImpl.getInstance().getUserNames());
        return "users"; // this returns the view users.html reference ${user}
    }
    
    /*
	1>@Controller is used in Spring MVC applications to handle web requests and return views.
	  return 'users' -> return users.html or users.jsp
	2>@RestController is a specialized version of @Controller that combines @ResponseBody
	    return object
    */
    
	@RequestMapping(value={"/mvc/loggedUsers2", "/loggedUsers2"}, method=RequestMethod.GET)
	public String getLoggedUsers2(ModelMap model) {
		//http://localhost:8080/loggedUsers2
    	InternalLogger.info("/loggedUsers2 called getLoggedUsers2()"); 
    	InternalLogger.info("call LoggedUserController.getLoggedUsers2()"); 	
        model.addAttribute("users", 
        	ActiveUserServiceImpl.getInstance().getUserNames());
        InternalLogger.info("/loggedUsers2 ActiveUserServiceImpl.getInstance().getUserNames()="+
        	ActiveUserServiceImpl.getInstance().getUserNames());
        return "users"; // this returns the view users.html reference ${user}
	}
	
	@RequestMapping(value={"/mvc/check", "/check"}, method=RequestMethod.GET)
	public String getLoggedUsers3(ModelMap model) {
		//http://localhost:8080/check
    	InternalLogger.info("/check called getLoggedUsers3()"); 
    	InternalLogger.info("call LoggedUserController.check()"); 
    	
    	List<String> userNames;
    	userNames = new ArrayList<String>();
        userNames.add("root3"); // so at least i have a user
        
        model.addAttribute("users", 
        		userNames);
        InternalLogger.info("/check userNames="+userNames);
        return "users"; // "Path with "WEB-INF" or "META-INF": [WEB-INF/jsp/users.jsp]"
	}
}
