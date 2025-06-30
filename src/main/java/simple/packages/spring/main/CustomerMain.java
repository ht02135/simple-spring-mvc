package simple.packages.spring.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import simple.packages.spring.pojo.Customer;
import simple.packages.spring.pojo.NickName;

public class CustomerMain {

	public static void main(String[] args) throws Exception {
		
		/*
		ObjectMapper expects getter (and setter, for deserialization) methods for all fields.
		*/
		ObjectMapper mapper = new ObjectMapper();
		
		// create a Java object
		Customer customer = new Customer();
        customer.setName("John");
        customer.setAge(1);
        customer.setHobby("weird_hobby");
        
        // i only have @JsonProperty on param construct, would this work?
        NickName nickName = new NickName(); 
        System.out.println("nickName"+nickName);
        
        nickName.setFirst("Jo");
        nickName.setLast("Jo");
        customer.setNickName(nickName);
        System.out.println("customer"+customer);
        
        // convert the Java object to JSON
        String json = mapper.writeValueAsString(customer);
        System.out.println("json"+json);

		// convert the JSON string to a Java object
		Customer customer2 = mapper.readValue(json, Customer.class);
		System.out.println("customer2"+customer2);
		
		//pretty JSON
		//newer
		System.out.println("pretty json customer2"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer2));
		System.out.println("pretty json customer2"+mapper.writer().withDefaultPrettyPrinter().writeValueAsString(customer2));
	}

}
