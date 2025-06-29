package simple.packages.spring.mvc.service;

import java.util.ArrayList;
import java.util.List;

import simple.packages.spring.util.*;

public class ActiveUserServiceImpl implements ActiveUserService {

    public List<String> userNames;
    
    //cant autowire static
    public static ActiveUserService instance = null;

    public static synchronized ActiveUserService getInstance() {
    	if (instance == null)
    		instance = new ActiveUserServiceImpl();
    	return instance;
    }
    
    public ActiveUserServiceImpl() {
    	super();
    	Logger.info("call ActiveUserServiceImpl()");
        userNames = new ArrayList<String>();
        userNames.add("root"); // so at least i have a user
    }
    
	@Override
	public synchronized void add(String userName) {
		Logger.info("call ActiveUserServiceImpl.add()");
		if (!userNames.contains(userName))
			userNames.add(userName);
	}

	@Override
	public synchronized boolean remove(String userName) {
		Logger.info("call ActiveUserServiceImpl.remove()");
		return userNames.remove(userName);
	}

	@Override
	public synchronized boolean contains(String userName) {
		Logger.info("call ActiveUserServiceImpl.contains()");
		return userNames.contains(userName);
	}
	
	public synchronized List<String> getUserNames() {
		Logger.info("call ActiveUserServiceImpl.getUserNames()");
		return userNames;
	}

	public List<String> getUsers() {
		return userNames;
	}

	public void setUsers(List<String> users) {
		this.userNames = users;
	}

	@Override
	public String toString() {
		return "ActiveUserServiceImpl [users=" + userNames + "]";
	}
    
}
