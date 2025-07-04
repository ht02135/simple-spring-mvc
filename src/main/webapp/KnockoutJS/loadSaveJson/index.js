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
				/*
				The JSON object, available in all modern browsers, has two useful methods 
				to deal with JSON-formatted content: parse and stringify.
				*/

				//JSON.parse() takes a JSON string and transforms it into a JavaScript object.
				var userJSONStr = '{"name":"Sammy","email":"sammy@example.com","plan":"Pro"}';
				console.log("sanityCheckJSON before JSON.parse userJSONStr=" + userJSONStr);
				var userObj = JSON.parse(userJSONStr);
				console.log("sanityCheckJSON after JSON.parse userObj=" + userObj);

				//JSON.stringify() takes a JavaScript object and transforms it into a JSON string.
				var userJSONStr2 = JSON.stringify(userObj);
				console.log("sanityCheckJSON aftwr userJSONStr2=" + userJSONStr2);

				var customerJSONStr = '{"customer":{"age":1,"Hobby":"weird_hobby","full_name":"John","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":null}'
				console.log("sanityCheckJSON before JSON.parse customerJSONStr=" + customerJSONStr);
				var customerObj = JSON.parse(customerJSONStr);
				console.log("sanityCheckJSON after JSON.parse customerObj=" + customerObj);
			},

			testMapping: function() {
				console.log("called testMapping");

				this.customers.push(new CustomerPojo("5", "Hobby3", "full_name3", "first_nick_name3", "last_nick_name3", "weird_hobby3"));

				var customersStr = JSON.stringify(this.customers());
				/*
				JSON.stringify(this.customers())=[{},{},{}]
				index.js:119 JSON.stringify(this.customers)=undefined
				index.js:120 customersStr=undefined
				*/
				console.log("JSON.stringify(this.customers())=" + JSON.stringify(this.customers()));
				console.log("JSON.stringify(this.customers)=" + JSON.stringify(this.customers));
				console.log("customersStr=" + customersStr);

				var requestJSON = '{"customer":{"age":11,"Hobby":"weird_hobby","full_name":"John2","first_nick_name":"Jo","last_nick_name":"Jo","weird_hobby":"weird_hobby"},"item":"fake item"}';
				var requestObj = JSON.parse(requestJSON);
				var customerObj = requestObj.customer;
				console.log("customerObj=" + customerObj);
				/*
				ko.toJSON - i think convert JSobj to json string
				ko.toJS - i think json string to JSobj
				////////////
				JSON.parse() - static method parses a JSON string, constructing the JavaScript obj
				JSON.stringify() - convert JSobj to json string
				*/
				var customersObj = JSON.parse(customersStr);
				console.log("before push customersObj=" + customersObj);
				customersObj.push(new CustomerPojo("5", "Hobby4", "full_name4", "first_nick_name4", "last_nick_name4", "weird_hobby4"));
				console.log("after push customersObj=" + customersObj);

				/*
				Specifying the update target
				ko.mapping.fromJS(data, {}, someObject); // overwrites properties on someObject
				*/
				ko.mapping.fromJS(customerObj, {}, this.customer);
				console.log("after run ko.mapping.fromJS customerObj -> this.customer");

				/*
				test array
				1>JSON.stringify() static method converts a JavaScript value to a JSON string
				*/
				ko.mapping.fromJS(customersObj, {}, this.customers());
				console.log("after run ko.mapping.fromJS customersObj -> this.customers");
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
					url: "http://localhost:8080/mvc/mvc/customer-order/customer/" + customerId,
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
					url: "http://localhost:8080/mvc/mvc/customer-order/set",
					data: requestJSONStr,
					cache: false,
					//success: handleCustomerSuccess,
				}).success(function(result, status, xhr) {
					//success : Function( Anything data, String textStatus, jqXHR jqXHR )
					console.log("postCustomerToServer success");
					console.log("postCustomerToServer result=" + result);
					externalHandleCustomerSuccess(result);
				}).error(function(result, status, xhr) {
					console.log("postCustomerToServer error");
					console.log("postCustomerToServer result=" + result);
					console.log("postCustomerToServer status=" + status);
					console.log("postCustomerToServer xhr=" + xhr);
				});
			},

			handleCustomerSuccess2: function(result) {
				console.log("handleCustomerSuccess2");
				console.log("handleCustomerSuccess result=" + result);

				var customerObj = result.customer;

				this.customerFullName(customerObj.full_name);

				/*
				Specifying the update target
				ko.mapping.fromJS(data, {}, someObject); // overwrites properties on someObject
				*/
				ko.mapping.fromJS(customerObj, {}, this.customer);
			},

			handleCustomerSuccess: function(result) {
				console.log("handleCustomerSuccess");
				console.log("handleCustomerSuccess result=" + result);

				var responseJSON = result;
				console.log("handleCustomerSuccess responseJSON=" + responseJSON);

				var parsedResponseStr = JSON.stringify(responseJSON);
				console.log("handleCustomerSuccess after JSON.stringify parsedResponseStr=" + parsedResponseStr);

				var parsedResponseObj = JSON.parse(parsedResponseStr);
				console.log("handleCustomerSuccess after JSON.parse parsedResponseObj=" + parsedResponseObj);

				var parsedCustomerObj = parsedResponseObj.customer;
				console.log("handleCustomerSuccess parsedCustomerObj=" + parsedCustomerObj);

				//---------------------

				console.log("before set viewModel.customerFullName()=" + this.customerFullName());
				this.customerFullName(parsedCustomerObj.full_name);
				console.log("after set this.customerFullName()=" + this.customerFullName());

				console.log("before set viewModel.customer().age()=" + this.customer().age());
				this.customer().age(parsedCustomerObj.age);
				console.log("after set viewModel.customer().age()=" + this.customer().age());

				console.log("before set viewModel.customer().Hobby()=" + this.customer().Hobby());
				this.customer().Hobby(parsedCustomerObj.Hobby);
				console.log("after set viewModel.customer().Hobby()=" + this.customer().Hobby());

				console.log("before set viewModel.customer().full_name()=" + this.customer().full_name());
				this.customer().full_name(parsedCustomerObj.full_name);
				console.log("after set viewModel.customer().full_name()=" + this.customer().full_name());

				this.customer().first_nick_name(parsedCustomerObj.first_nick_name);
				this.customer().last_nick_name(parsedCustomerObj.last_nick_name);
				this.customer().weird_hobby(parsedCustomerObj.weird_hobby);
			}
		}	//return
	}; //function

	var viewModel = new ViewModel();

	//callback function
	var externalHandleCustomerSuccess = function(result) {
		//result in GET/POST is TransactionResponse
		console.log("externalHandleCustomerSuccess");
		viewModel.handleCustomerSuccess(result);
		viewModel.handleCustomerSuccess2(result);
	};

	ko.applyBindings(viewModel);
});