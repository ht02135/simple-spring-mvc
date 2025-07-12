package simple.packages.spring.service;

import java.io.Serializable;
import java.util.List;

public interface ActiveUserService extends Serializable {
	
	public void add(String userName);
	
	public boolean remove(String userName);
	
	public boolean contains(String userName);

	public List<String> getUserNames();
}
