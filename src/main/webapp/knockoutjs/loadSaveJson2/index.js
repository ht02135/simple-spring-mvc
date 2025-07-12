$(function() {
	infuser.defaults.templateUrl = "../../templates";

	function CustomerPojo(age, Hobby, full_name, first_nick_name, last_nick_name, weird_hobby) {
		return {
			age: ko.observable(age),
			Hobby: ko.observable(Hobby),
			full_name: ko.observable(full_name),
			first_nick_name: ko.observable(first_nick_name),
			last_nick_name: ko.observable(last_nick_name),
			weird_hobby: ko.observable(weird_hobby)
		}	//return
	};	//function

	function ViewModel() {
		return {
			//--------------------------------------
			//Declare
			propertyPlaceHolder: ko.observable(),

			customers: ko.observableArray([
				new CustomerPojo("5", "Hobby", "full_name", "first_nick_name", "last_nick_name", "weird_hobby"),
				new CustomerPojo("5", "Hobby2", "full_name2", "first_nick_name2", "last_nick_name2", "weird_hobby2")
			]),

			anotherObservableArray: ko.observableArray([
				{ name: "Bungle", type: "Bear" },
				{ name: "George", type: "Hippo" },
				{ name: "Zippy", type: "Unknown" }
			]),

			customerFullName: ko.observable("default"),

			customer: ko.observable(new CustomerPojo("5", "default", "default", "default", "default", "default")),

			//--------------------------------------
			//Declare
			operationPlaceHolder: function() {
				console.log("called operationPlaceHolder");
			},

			/*
			/customer/{id}
			http://localhost:8080/mvc/mvc/customer-order/customer/5
			//////////////////////
			1>jQuery.post() = jQuery.ajax() + method = PUT
			2>jQuery.ajax() by default method = GET
			////////////////////////
			$.ajax()    // Performs an async AJAX request
			1>crossDomain (default: false for same-domain requests, true for cross-domain requests)
			2>dataType (default: Intelligent Guess (xml, json, script, or html))
			The type of data that you're expecting back from the server. If none is 
			specified, jQuery will try to infer it based on the MIME type of the 
			response (an XML MIME type will yield XML, in 1.4 JSON will yield a 
			JavaScript object
			3>method (default: 'GET')
			The HTTP method to use for the request (e.g. "POST", "GET", "PUT"). (version added: 1.9)
			////////////////////////
			$.get()     // Loads data from a server using an AJAX HTTP GET request
			/////////////////////////
			$.post()    // Loads data from a server using an AJAX HTTP POST request
			1>data : PlainObject or String
			2>dataType: The type of data expected from the server. Default: Intelligent 
			Guess (xml, json, script, text, html).
			*/
			getCustomer: function() {
				console.log("called getCustomer");
				this.sanityCheckJSON();
				this.getCustomerFromServer();
			},
			
			getCustomers: function() {
				console.log("called getCustomer");
				this.sanityCheckJSON();
				this.getCustomersFromServer();
			},

			postCustomer: function() {
				console.log("called getCustomer");
				this.sanityCheckJSON();
				this.postCustomerToServer();
			},

			capitalizeLastName: function() {			//click data-binding
				var currentVal = this.customerFullName();        	// Read the current value
				this.customerFullName(currentVal.toUpperCase()); 	// Write back a modified value
			},

			sanityCheckJSON: function() {
				console.log("called sanityCheckJSON");

				//JSON.parse() takes a JSON string and transforms it into a JavaScript object.
				var userJSONStr = '{"name":"Sammy","email":"sammy@example.com","plan":"Pro"}';
				console.log("userJSONStr=" + userJSONStr);
				var userObj = JSON.parse(userJSONStr);

				//JSON.stringify() takes a JavaScript object and transforms it into a JSON string.
				var userJSONStr2 = JSON.stringify(userObj);
				console.log("userJSONStr2=" + userJSONStr2);

				var customerJSONStr = '{"customer":{"age":1,"Hobby":"weird_hobby","full_name":"John","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":null}'
				console.log("customerJSONStr=" + customerJSONStr);
				var customerObj = JSON.parse(customerJSONStr);
			},

			testMapping: function() {
				console.log("=== testMapping BEGIN ================================");
				console.log("called testMapping");

				var requestJSON = '{"customer":{"age":11,"Hobby":"weird_hobby","full_name":"John2","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":"fake item"}';
				var requestObj = JSON.parse(requestJSON);
				
				var customerObj = requestObj.customer;
				ko.mapping.fromJS(customerObj, {}, this.customer);
				console.log("after ko.mapping.fromJS customerObj -> this.customer");
				
				this.customers.push(new CustomerPojo("5", "Hobby3", "full_name3", "first_nick_name3", "last_nick_name3", "weird_hobby3"));
				console.log("this.customers=" + JSON.stringify(this.customers));
				console.log("this.customers()=" + JSON.stringify(this.customers()));
				
				var customersObj = JSON.parse(JSON.stringify(this.customers()));
				customersObj.push(new CustomerPojo("5", "Hobby4", "full_name4", "first_nick_name4", "last_nick_name4", "weird_hobby4"));
				console.log("customersObj=" + JSON.stringify(customersObj));

				ko.mapping.fromJS(customersObj, {}, this.customers());
				console.log("after ko.mapping.fromJS customersObj -> this.customers");
				
				console.log("=== testMapping END ================================");
			},

			getCustomerFromServer: function() {
				console.log("called getCustomerFromServer");

				var customerId = "5";
				/*
				/customer/{id}
				http://localhost:8080/mvc/mvc/customer-order/customer/5
				return TransactionResponse in json format
				*/
				$.ajax({
					type: "GET",
					crossDomain: "true",
					contentType: "application/json; charset=utf-8",
					dataType: "json", //The type of data that you're expecting back from the server.
					url: "../../mvc/customer-order/customer/" + customerId,
					//data: "",
					//success: handleCustomerSuccess,
				}).success(function(result, status, xhr) {
					//success : Function( Anything data, String textStatus, jqXHR jqXHR )
					console.log("getCustomer success");
					console.log("getCustomer result=" + result);
					externalHandleCustomerSuccess(result);
				}).error(function(result, status, xhr) {
					console.log("getCustomer error");
				});
			},

			getCustomersFromServer: function() {
				console.log("called getCustomerFromServer");

				$.ajax({
					type: "GET",
					crossDomain: "true",
					contentType: "application/json; charset=utf-8",
					dataType: "json", //The type of data that you're expecting back from the server.
					url: "../../mvc/customer-order/customers",
					//data: "",
					//success: handleCustomerSuccess,
				}).success(function(result, status, xhr) {
					//success : Function( Anything data, String textStatus, jqXHR jqXHR )
					console.log("getCustomers success");
					console.log("getCustomers result=" + result);
					externalHandleCustomerSuccess2(result);
				}).error(function(result, status, xhr) {
					console.log("getCustomers error");
				});
			},
			
			postCustomerToServer: function() {
				console.log("called postCustomerToServer");

				var requestJSON = '{"customer":{"age":11,"Hobby":"weird_hobby","full_name":"John2","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":"fake item"}';
				console.log("postCustomerToServer before JSON.parse requestJSON=" + requestJSON);
				var requestObj = JSON.parse(requestJSON);
				console.log("postCustomerToServer after JSON.parse requestObj=" + requestObj);
				var requestJSONStr = JSON.stringify(requestObj);
				console.log("postCustomerToServer after JSON.stringify requestJSONStr=" + requestJSONStr);

				$.ajax({
					type: "PUT",
					crossDomain: "true",
					contentType: "application/json",
					dataType: "json", //The type of data that you're expecting back from the server.
					processData: false,
					url: "../../mvc/customer-order/set",
					data: requestJSONStr,
					cache: false,
					//success: handleCustomerSuccess,
				}).success(function(result, status, xhr) {
					//success : Function( Anything data, String textStatus, jqXHR jqXHR )
					console.log("postCustomerToServer success");
					console.log("postCustomerToServer result=" + result);
					externalHandleCustomerSuccess2(result);
				}).error(function(result, status, xhr) {
					console.log("postCustomerToServer error");
					console.log("postCustomerToServer result=" + result);
					console.log("postCustomerToServer status=" + status);
					console.log("postCustomerToServer xhr=" + xhr);
				});
			}
		}	//return
	}; //function

	//------------------------------------------------
	//callback function
	
	var externalHandleCustomerSuccess = function(result) {
		//result in GET/POST is TransactionResponse
		console.log("externalHandleCustomerSuccess");

		console.log("==== externalHandleCustomerSuccess BEGIN ===============================");
		console.log("handleCustomerSuccess");
		console.log("result=" + result);

		var resultStr = JSON.stringify(result);
		console.log("resultStr=" + resultStr);

		var responseObj = JSON.parse(resultStr);
		console.log("responseObj=" + responseObj);
		console.log("JSON.stringify(responseObj)=" + JSON.stringify(responseObj));

		console.log("JSON.stringify(responseObj.customer)=" + JSON.stringify(responseObj.customer));
		console.log("JSON.stringify(responseObj.customers)=" + JSON.stringify(responseObj.customers));

		var customerObj = JSON.parse(JSON.stringify(responseObj.customer));

		if (customerObj) {
			viewModel.customerFullName(customerObj.full_name);
			viewModel.customer().age(customerObj.age);
			viewModel.customer().Hobby(customerObj.Hobby);
			viewModel.customer().full_name(customerObj.full_name);
			viewModel.customer().first_nick_name(customerObj.first_nick_name);
			viewModel.customer().last_nick_name(customerObj.last_nick_name);
			viewModel.customer().weird_hobby(customerObj.weird_hobby);
		}

		console.log("=== externalHandleCustomerSuccess ENC ================================");
	};

	var externalHandleCustomerSuccess2 = function(result) {
		//result in GET/POST is TransactionResponse
		console.log("externalHandleCustomerSuccess2");

		console.log("=== externalHandleCustomerSuccess2 BEGIN ================================");
		console.log("handleCustomerSuccess2");
		console.log("result=" + result);

		var resultStr = JSON.stringify(result);
		console.log("resultStr=" + resultStr);

		var responseObj = JSON.parse(resultStr);
		console.log("responseObj=" + responseObj);
		console.log("JSON.stringify(responseObj)=" + JSON.stringify(responseObj));

		console.log("JSON.stringify(responseObj.customer)=" + JSON.stringify(responseObj.customer));
		console.log("JSON.stringify(responseObj.customers)=" + JSON.stringify(responseObj.customers));

		var customerObj = JSON.parse(JSON.stringify(responseObj.customer));
		var customersObj = JSON.parse(JSON.stringify(responseObj.customers));

		if (customerObj) {
			ko.mapping.fromJS(customerObj.full_name, {}, viewModel.customerFullName);
			ko.mapping.fromJS(customerObj, {}, viewModel.customer);
			console.log("after ko.mapping.fromJS customerObj -> this.customerObj");
		}
		if (customersObj) {
			ko.mapping.fromJS(customersObj, {}, viewModel.customers);
			console.log("after ko.mapping.fromJS customersObj -> this.customers");
		}

		console.log("=== externalHandleCustomerSuccess2 END ================================");
	};
	
	var viewModel = new ViewModel();
	ko.applyBindings(viewModel);
});