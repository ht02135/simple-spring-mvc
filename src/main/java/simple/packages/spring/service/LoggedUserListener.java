package simple.packages.spring.service;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import simple.packages.spring.util.*;

public class LoggedUserListener implements HttpSessionBindingListener, Serializable {
	
	//The logger must be static; this would make it non-serializable.
	private static final long serialVersionUID = 1L;
    
	private String username; 
    
    public LoggedUserListener(String username) {
    	super();
        this.username = username;
    }
    
    //HttpSessionBindingEvent(HttpSession session, String name) constructor
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
    	InternalLogger.info("call LoggedUserListener.valueBound()");
        LoggedUserListener user = (LoggedUserListener) event.getValue();
        if (!ActiveUserServiceImpl.getInstance().contains(user.getUsername())) {
        	ActiveUserServiceImpl.getInstance().add(user.getUsername());
        }
    }

    //HttpSessionBindingEvent(HttpSession session, String name) constructor
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
    	InternalLogger.info("call LoggedUserListener.valueUnbound()");
        LoggedUserListener user = (LoggedUserListener) event.getValue();
        if (ActiveUserServiceImpl.getInstance().contains(user.getUsername())) {
        	ActiveUserServiceImpl.getInstance().remove(user.getUsername());
        }
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "LoggedUserListener [username=" + username + "]";
	}
}
