$(function() {
	/*
	not doing template, so comment out...
	1>loading template involving ajax call which run into "Cross-Origin Request Blocked error
	2>to go around that, need to deploy WAR to tomcat which is huzzle
	3>or just dont load template unless is needed in the page
	//////////////////
    infuser.defaults.templateUrl = "../../templates";
	*/
	
	/*
	TransactionResponse =
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
	var customerPojo = function (age, Hobby, full_name, first_nick_name, last_nick_name, weird_hobby) 
	{
		this.age = ko.observable(age);
		this.Hobby = ko.observable(Hobby);
		this.full_name = ko.observable(full_name);
		this.first_nick_name = ko.observable(first_nick_name);
		this.last_nick_name = ko.observable(last_nick_name);
		this.weird_hobby = ko.observable(weird_hobby);
	};
	
    var viewModel = {
		//--------------------------------------
		//Declare
		propertyPlaceHolder: ko.observable(),
		
		customerFullName: ko.observable("default"),
		
		customer: ko.observable(new customerPojo("5", "", "", "", "", "")),

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
		
		capitalizeLastName: function() {			//click data-binding
			var currentVal = this.customerFullName();        	// Read the current value
			this.customerFullName(currentVal.toUpperCase()); 	// Write back a modified value
		},
		
		sanityCheckJSON: function() {
			/*
			The JSON object, available in all modern browsers, has two useful methods 
			to deal with JSON-formatted content: parse and stringify.
			*/
			
			//JSON.parse() takes a JSON string and transforms it into a JavaScript object.
			var userJSONStr = '{"name":"Sammy","email":"sammy@example.com","plan":"Pro"}';
			console.log("sanityCheckJSON before JSON.parse userJSONStr="+userJSONStr);
			var userObj = JSON.parse(userJSONStr);
			console.log("sanityCheckJSON after JSON.parse userObj="+userObj);
			
			//JSON.stringify() takes a JavaScript object and transforms it into a JSON string.
			var userJSONStr2 = JSON.stringify(userObj);
			console.log("sanityCheckJSON aftwr userJSONStr2="+userJSONStr2);
			
			var customerJSONStr = '{"customer":{"age":1,"Hobby":"weird_hobby","full_name":"John","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":null}'
			console.log("sanityCheckJSON before JSON.parse customerJSONStr="+customerJSONStr);
			var customerObj = JSON.parse(customerJSONStr);
			console.log("sanityCheckJSON after JSON.parse customerObj="+customerObj);
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
			    url:"http://localhost:8080/mvc/mvc/customer-order/customer/"+customerId,
				//data: "",
			    //success: handleCustomerSuccess,
			}).success(function(result, status, xhr) {
				//success : Function( Anything data, String textStatus, jqXHR jqXHR )
				console.log("getCustomer success");
				console.log("getCustomer result="+result);
				/*
				TypeError: _this.handler.handle is not a function error
				*/
				externalHandleCustomerSuccess(result);
			}).error(function (result, status, xhr) {
				console.log("getCustomer error");
			});
		},
		
		handleCustomerSuccess: function(result) {
			console.log("handleCustomerSuccess");
			console.log("handleCustomerSuccess result="+result);
			var responseJSON = result;
			console.log("handleCustomerSuccess responseJSON="+responseJSON);

			var parsedResponseStr = JSON.stringify(responseJSON);
			console.log("handleCustomerSuccess after JSON.stringify parsedResponseStr="+parsedResponseStr);
						
			var parsedResponseObj = JSON.parse(parsedResponseStr);
			console.log("handleCustomerSuccess after JSON.parse parsedResponseObj="+parsedResponseObj);

			var parsedCustomerObj = parsedResponseObj.customer;
			console.log("handleCustomerSuccess parsedCustomerObj="+parsedCustomerObj);

			//---------------------

			console.log("before set viewModel.customerFullName()="+this.customerFullName());
			this.customerFullName(parsedCustomerObj.full_name);
			console.log("after set this.customerFullName()="+this.customerFullName());

			console.log("before set viewModel.customer().age()="+this.customer().age());
			this.customer().age(parsedCustomerObj.age);
			console.log("after set viewModel.customer().age()="+this.customer().age());

			console.log("before set viewModel.customer().Hobby()="+this.customer().Hobby());
			this.customer().Hobby(parsedCustomerObj.Hobby);
			console.log("after set viewModel.customer().Hobby()="+this.customer().Hobby());

			console.log("before set viewModel.customer().full_name()="+this.customer().full_name());
			this.customer().full_name(parsedCustomerObj.full_name);
			console.log("after set viewModel.customer().full_name()="+this.customer().full_name());
						
			this.customer().first_nick_name(parsedCustomerObj.first_nick_name);
			this.customer().last_nick_name(parsedCustomerObj.last_nick_name);
			this.customer().weird_hobby(parsedCustomerObj.weird_hobby);

			//---------------------
		}
			
    };
	
	//callback function
	var externalHandleCustomerSuccess = function (result) {
		console.log("externalHandleCustomerSuccess");
		viewModel.handleCustomerSuccess(result);
	};

    ko.applyBindings(viewModel);
});