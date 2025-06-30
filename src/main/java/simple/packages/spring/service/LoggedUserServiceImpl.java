package simple.packages.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;

import simple.packages.spring.util.*;

public class LoggedUserServiceImpl implements LoggedUserService {

	private List<LoggedUserListener> loggedUserListeners = null;
	
	//cant autowire static
    public static LoggedUserService instance = null;

    public static synchronized LoggedUserService getInstance() {
    	if (instance == null)
    		instance = new LoggedUserServiceImpl();
    	return instance;
    }
    
    public LoggedUserServiceImpl() {
    	super();
    	loggedUserListeners = new ArrayList<LoggedUserListener>();
    }

    //get current session HttpSession getSession();
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {

		InternalLogger.info("call LoggedUserServiceImpl.onLogoutSuccess()");

		HttpSession session = (HttpSession) request.getSession();
        if (session != null){
        	String username = request.getUserPrincipal().getName();
        	session.removeAttribute("username");
        	
    		HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, username);
    		LoggedUserListener loggedUserListener = new LoggedUserListener(username);
    		loggedUserListener.valueUnbound(event);
        }   
	}

	//get current session HttpSession getSession();
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {

		InternalLogger.info("call LoggedUserServiceImpl.onAuthenticationSuccess()");
		HttpSession session = (HttpSession) request.getSession(false);
        if (session != null) {
        	String username = request.getUserPrincipal().getName();
        	session.setAttribute("username", username);
        	
    		HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, username);
    		LoggedUserListener loggedUserListener = new LoggedUserListener(username);
    		loggedUserListener.valueBound(event);
            
        }	
	}

}
