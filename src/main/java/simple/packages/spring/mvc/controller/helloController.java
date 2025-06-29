package simple.packages.spring.mvc.controller;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import simple.packages.spring.util.InternalLogger;

@Controller
public class HelloController {

    public HelloController() {
		super();
	}

	/*
    type into browser
    http://localhost:8080/hello
    ///////////////////////
    we can do multiple value
    @RequestMapping(value={"/formA.html", "/formB.html", "/formC.html"})
    */
    @RequestMapping(value={"/mvc/hello", "/hello"}) // value={"/mvc/hello", "/hello"}
    @ResponseBody
    public String sayHello()
    {
        return "index"; // redirect to view in jsp AKA WEB-INF/jsp/index.jsp
    }
    
    @RequestMapping(value={"/mvc/greeting", "/greeting"}, method = RequestMethod.GET)
    public String getGreeting(ModelMap model) {
    	InternalLogger.info("/greeting called getGreeting()"); 
    	//http://localhost:8080/greeting
        /*
        URL redirection = URL forwarding
        /////////////////////////
        1>redirect will respond with a 302 and the new URL in the Location header; 
        the browser/client will then make another request to the new URL.
		forward happens entirely on a server side. The Servlet container forwards 
		the same request to the target URL; the URL won’t change in the browser.
        */
        model.addAttribute("message", "Hello, World!");
        return "greeting"; // "Path with "WEB-INF" or "META-INF": [WEB-INF/jsp/greeting.jsp]"
    }
    
	/*
	Context Path
	1>By default, Spring Boot serves the content on the root context path (“/”).
	So, any Boot application with default configuration can be accessed as:
	http://localhost:8080/
	2>it can be changed to 
	server.servlet.context-path=/mvc
	/////////////////////
	Servlet Path
	1>The servlet path represents the path of the main DispatcherServlet. The 
	DispatcherServlet is an actual Servlet, and it inherits from HttpSerlvet 
	base class. The default value is similar to the context path, i.e. (“/”):
	spring.mvc.servlet.path=/
	2>it can be changed to
	spring.mvc.servlet.path=/training
	//////////////////////
	*/

    @RequestMapping(value={"/mvc/redirectGreeting", "/redirectGreeting"}, method = RequestMethod.GET)
    public String redirectGreeting(ModelMap model) {
    	InternalLogger.info("/redirectGreeting called redirectGreeting()"); 
        model.addAttribute("message", "redirect Hello, World!");
        return "redirect:greeting";
    }

}
