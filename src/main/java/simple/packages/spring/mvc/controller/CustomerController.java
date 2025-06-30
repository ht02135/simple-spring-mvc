package simple.packages.spring.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import simple.packages.spring.pojo.Customer;
import simple.packages.spring.pojo.NickName;
import simple.packages.spring.util.InternalLogger;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
	
	private Customer getCustomerFromService(long customerId) {
		InternalLogger.info("/ called CustomerController.getCustomerFromService()"); 
		
		//Customer customer = customerService.getCustomer(customerId);
		NickName nickName = new NickName(); 
		nickName.setFirst("Jo");
        nickName.setLast("Jo");
        
		Customer customer = new Customer();
		customer.setNickName(nickName);
        customer.setName("John");
        customer.setAge(1);
        customer.setHobby("weird_hobby");
        
        return customer;
	}
	
	private long addCustomerFromService(Customer customer) {
		InternalLogger.info("/ called CustomerController.addCustomerFromService()"); 
		
		InternalLogger.info("added customer="+customer); 
        
        return 100;
	}
	
	private Customer setHobby(long customerId, String Hobby) {
		InternalLogger.info("/ called CustomerController.setHobbyAndAge()"); 
		
		Customer customer = getCustomerFromService(customerId);
		customer.setHobby(Hobby);
		
		return customer;
	}
	
	private Customer setHobby(long customerId, String Hobby, long age) {
		InternalLogger.info("/ called CustomerController.setHobbyAndAge()"); 
		
		Customer customer = getCustomerFromService( customerId);
		customer.setHobby(Hobby);
		customer.setAge(age);
        
        return customer;
	}

	//Using GET Method
	//The request mapping has been set to only accept GET requests
	/*
	URL: http://localhost:8080/mvc/mvc/customer/info/4
	*/
	@RequestMapping(method = RequestMethod.GET, value = "/info/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") long customerId) {
		InternalLogger.info("/ GET called CustomerController.getCustomer()"); 
		InternalLogger.info("customerId="+customerId); 
		
		Customer customer = getCustomerFromService(customerId);
	    if (customer != null) {
	        return ResponseEntity.ok(customer);
	    }
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	//Using PUT Method
	/*
	we specify that we only consume a JSON formatted request body. We also add the 
	annotation @RequestBody which automatically maps a JSON request to Student object
	////////////////
	use postman
	path = http://localhost:8080/mvc/mvc/customer/add
	header Content-Type=application/json
	body >> raw >> json
	{
		"age" : 1,
		"Hobby" : "weird_hobby",
		"full_name" : "John",
		"first_nick_name" : "Jo",
		"last_nick_name" : "Jo",
		"weird_hobby" : "weird_hobby"
	}
	*/
	@RequestMapping(method = RequestMethod.PUT, value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addCustomer(@RequestBody Customer customer) {
		InternalLogger.info("/ PUT called CustomerController.addCustomer()"); 
		InternalLogger.info("customer="+customer); 
		
	    long id = addCustomerFromService(customer);
	    return ResponseEntity.ok(id);
	}
	
	//Using POST Method
	/*
    use postman
    You can specify path and query parameters for a request using the URL box or 
    the Params tab.  Query parameters are appended to the end of the request URL, 
    following ? and listed in key-value pairs, separated by & as follows: ?id=1&type=new
    
    translate english
	use postman
	pick POST and make sure set data >> JSON
	Path = http://localhost:8080/mvc/mvc/customer/set/4
	header Content-Type=application/json
	body=none
	add param thru param tab
	hobby=strange
	additional param just keep adding?
    as i add them, url changed 
    http://localhost:8080/mvc/mvc/customer/set/4?hobby=strange
    ////////////////////
	*/
	@RequestMapping(method = RequestMethod.POST, value = "/set/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> addHobbyAndAge(
		@PathVariable("id") long customerId,
	    @RequestParam("hobby") String hobby) 
	{
		InternalLogger.info("/ POST called CustomerController.addHobbyAndAge()"); 
		InternalLogger.info("customerId="+customerId); 
		if (hobby != null) InternalLogger.info("hobby="+hobby);
		
		Customer customer = setHobby(customerId, hobby);
	    return ResponseEntity.ok(customer);
	}
	
	/*
	use postman
	pick POST and make sure set data >> JSON
	Path = http://localhost:8080/mvc/mvc/customer/set2/4
	header Content-Type=application/json
	body=none
	add param thru param tab
	hobby=strange
	age=10
	/////////////////////////
	http://localhost:8080/mvc/mvc/customer/set2/4?hobby=strange&age=1
	*/
	@RequestMapping(method = RequestMethod.POST, value = "/set2/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> addHobbyAndAge2(
		@PathVariable("id") long customerId,
	    @RequestParam("hobby") String hobby,
	    /*
	    normally year should be integer right?
	    but try to pass integer from postman or curl, good luck on that
	    better take as string and then press it....
	    */
	    @RequestParam(required = false, name = "age",  defaultValue = "1") String age
	    ) 
	{
		InternalLogger.info("/ POST called CustomerController.addHobbyAndAge()"); 
		InternalLogger.info("customerId="+customerId); 
		if (hobby != null) InternalLogger.info("hobby="+hobby);
		InternalLogger.info("age="+age);
		
		Customer customer = setHobby(customerId, hobby, Long.valueOf(age));
	    return ResponseEntity.ok(customer);
	}
}
