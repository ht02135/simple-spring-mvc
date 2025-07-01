package simple.packages.spring.mvc.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import simple.packages.spring.pojo.Customer;

/*
create wrapper for travel between light client and controller via json mechanic
stick with default java bean default contructor and get/set...  why make life difficult??
*/
public class TransactionResponse implements Serializable {

	@JsonProperty
	private Customer customer = null;
	
	@JsonProperty
	private String item = null;

	public TransactionResponse() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "TransactionResponse [customer=" + customer + ", item=" + item + "]";
	}



}
