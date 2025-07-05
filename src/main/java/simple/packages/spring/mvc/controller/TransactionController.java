package simple.packages.spring.mvc.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import simple.packages.spring.mvc.pojo.TransactionResponse;
import simple.packages.spring.mvc.pojo.TransactionRequest;
import simple.packages.spring.pojo.Customer;
import simple.packages.spring.pojo.NickName;
import simple.packages.spring.util.InternalLogger;

@RestController
@RequestMapping(value = "/customer-order", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	public TransactionController() {
		super();
	}
	
	public TransactionResponse getInnerCustomer(long customerId) {
		Customer customer = new Customer();
		customer.setName("John");
		customer.setAge(1);
		customer.setHobby("weird_hobby");

		NickName nickName = new NickName();

		nickName.setFirst("Jo");
		nickName.setLast("Jo");
		customer.setNickName(nickName);
		System.out.println("customer" + customer);
		
		TransactionResponse response = new TransactionResponse();
		response.setCustomer(customer);
		
		return response;
	}
	
	public TransactionResponse getInnerCustomers() {
		Customer customer = new Customer();
		customer.setName("John");
		customer.setAge(1);
		customer.setHobby("weird_hobby");

		NickName nickName = new NickName();

		nickName.setFirst("Jo");
		nickName.setLast("Jo");
		customer.setNickName(nickName);
		System.out.println("customer" + customer);
		
		TransactionResponse response = new TransactionResponse();
		response.getCustomers().add(customer);
		response.getCustomers().add(customer);
		response.getCustomers().add(customer);
		
		return response;
	}

	public TransactionResponse getInnerItem(long itemId) {
		TransactionResponse response = new TransactionResponse();
		response.setItem("fake item");
		
		return response;
	}

	public TransactionResponse setInnerTransaction(TransactionRequest transactionRequest) {
		Customer customer = transactionRequest.getCustomer();
		String item = transactionRequest.getItem();
		
		TransactionResponse transactionResponse = new TransactionResponse();
		
		if (customer != null) {
			transactionResponse.setCustomer(customer);  //too lazy just for now
		}
		if (item != null) {
			transactionResponse.setItem(item);  //too lazy just for now
		}
		
		return transactionResponse;
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/customers")
	public ResponseEntity<TransactionResponse> getCustomers() {
		InternalLogger.info("TransactionController.getCustomers()");
		TransactionResponse transactionResponse = getInnerCustomers();
		
		if (transactionResponse != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("getCustomer transactionResponse="+mapper.writeValueAsString(transactionResponse));
				System.out.println("getCustomer transactionResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionResponse));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ResponseEntity.ok(transactionResponse);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

    @RequestMapping(method = RequestMethod.GET, value = "/customer/{id}")
	public ResponseEntity<TransactionResponse> getCustomer(@PathVariable("id") long customerId) {
		InternalLogger.info("TransactionController.getCustomer()");
		InternalLogger.info("customerId=" + customerId);
		TransactionResponse transactionResponse = getInnerCustomer(customerId);

		if (transactionResponse != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("getCustomer transactionResponse="+mapper.writeValueAsString(transactionResponse));
				System.out.println("getCustomer transactionResponse="+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactionResponse));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ResponseEntity.ok(transactionResponse);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/item/{id}")
	public ResponseEntity<TransactionResponse> getIem(@PathVariable("id") long itemId) {
		InternalLogger.info("TransactionController.getIem()");
		InternalLogger.info("itemId=" + itemId);
		TransactionResponse transactionResponse = getInnerItem(itemId);

		if (transactionResponse != null) {
			return ResponseEntity.ok(transactionResponse);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/set", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionResponse> setTransaction(@RequestBody TransactionRequest transactionRequest) {
		InternalLogger.info("TransactionController.setTransaction()");
		InternalLogger.info("transactionRequest=" + transactionRequest);
		TransactionResponse transactionResponse = setInnerTransaction(transactionRequest);

		if (transactionResponse != null) {
			return ResponseEntity.ok(transactionResponse);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/set2", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionResponse> setTransaction(@RequestBody String jsonTransactionRequest) {
		InternalLogger.info("TransactionController.setTransaction()");
		InternalLogger.info("jsonTransactionRequest=" + jsonTransactionRequest);
		
		ObjectMapper mapper = new ObjectMapper();
		TransactionRequest transactionRequest;
		try {
			transactionRequest = mapper.readValue(jsonTransactionRequest, TransactionRequest.class);
			TransactionResponse transactionResponse = setInnerTransaction(transactionRequest);

			if (transactionResponse != null) {
				return ResponseEntity.ok(transactionResponse);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

}
