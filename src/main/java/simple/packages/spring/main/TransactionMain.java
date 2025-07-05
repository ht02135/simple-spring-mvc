package simple.packages.spring.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import simple.packages.spring.mvc.controller.TransactionController;
import simple.packages.spring.mvc.pojo.TransactionRequest;
import simple.packages.spring.mvc.pojo.TransactionResponse;
import simple.packages.spring.pojo.Customer;
import simple.packages.spring.pojo.NickName;

public class TransactionMain {

	public static void main(String[] args) throws Exception {
		
		/*
		ObjectMapper expects getter (and setter, for deserialization) methods for all fields.
		*/
		ObjectMapper mapper = new ObjectMapper();
		
		TransactionController controller = new TransactionController();
		
		/*
		/customer-order/customer/5
		path = http://localhost:8080/mvc/mvc/customer-order/customer/5
		return = {"customer":{"age":1,"Hobby":"weird_hobby","full_name":"John","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":null}
		*/
		TransactionResponse customerResponse = controller.getInnerCustomer(1);	
		System.out.println("getInnerCustomer customerResponse="+mapper.writeValueAsString(customerResponse));
		System.out.println("getInnerCustomer customerResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerResponse));
		
		TransactionResponse customersResponse = controller.getInnerCustomers();	
		System.out.println("getInnerCustomers customersResponse="+mapper.writeValueAsString(customersResponse));
		System.out.println("getInnerCustomers customersResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customersResponse));
		
		/*
		/customer-order/customer/5
		path = http://localhost:8080/mvc/mvc/customer-order/item/5
		return = {"customer":null,"item":"fake item"}
		*/
		TransactionResponse itemResponse = controller.getInnerItem(1);	
		System.out.println("getInnerItem itemResponse="+mapper.writeValueAsString(itemResponse));
		System.out.println("getInnerItem itemResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(itemResponse));
		
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setCustomer(customerResponse.getCustomer());
		transactionRequest.setItem(itemResponse.getItem());
		/*
		harvest json
		transactionRequest={"customer":{"age":1,"Hobby":"weird_hobby","full_name":"John","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":"fake item"}
		transactionRequest=
		{
  			"customer" : {
    			"age" : 1,
    			"Hobby" : "weird_hobby",
    			"full_name" : "John",
    			"first_nick_name" : "Jo",
    			"last_nick_name" : "Jo",
    			"weird_hobby" : "weird_hobby"
  			},
  			"item" : "fake_item"
		}
		//////////////////////
		/customer-order/set
		do PUT
		path = http://localhost:8080/mvc/mvc/customer-order/set
		path = http://localhost:8080/mvc/mvc/customer-order/set2
		header Content-Type=application/json
		body >> raw >> json
		{
  			"customer" : {
    			"age" : 1,
    			"Hobby" : "weird_hobby",
    			"full_name" : "John",
    			"first_nick_name" : "Jo",
    			"last_nick_name" : "Jo",
    			"weird_hobby" : "weird_hobby"
  			},
  			"item" : "fake_item"
		}
		/////////////////////////
		return
		{
    		"customer": {
        		"age": 1,
        		"Hobby": "weird_hobby",
        		"full_name": "John",
        		"first_nick_name": "Jo",
        		"last_nick_name": "Jo",
        		"weird_hobby": "weird_hobby"
    		},
    		"item": "fake_item"
		}
		*/
		System.out.println("transactionRequest="+mapper.writeValueAsString(transactionRequest));
		System.out.println("transactionRequest="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionRequest));
		
		//for sanity check if we can creat obj from json
		String json = mapper.writeValueAsString(transactionRequest);
		TransactionRequest transactionRequest2 = mapper.readValue(json, TransactionRequest.class);
		System.out.println("transactionRequest2="+mapper.writeValueAsString(transactionRequest2));
		System.out.println("transactionRequest2="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionRequest2));
		
		
		TransactionResponse transactionResponse = controller.setInnerTransaction(transactionRequest);
		System.out.println("transactionResponse="+mapper.writeValueAsString(transactionResponse));
		System.out.println("transactionResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionResponse));
	}

}
