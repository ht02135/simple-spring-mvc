package simple.packages.spring.mvc.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import simple.packages.spring.pojo.Customer;

/*
create wrapper for travel between light client and controller via json mechanic
stick with default java bean default contructor and get/set...  why make life difficult??
*/
public class TransactionRequest implements Serializable {

	@JsonProperty
	private Customer customer = null;
	
	@JsonProperty
	private List<Customer> customers = new ArrayList<Customer>();
	
	@JsonProperty
	private String item = null;

	public TransactionRequest() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "TransactionRequest [customer=" + customer + ", customers=" + customers + ", item=" + item + "]";
	}

}
