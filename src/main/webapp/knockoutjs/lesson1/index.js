
/*
step2
define ViewModel
*/
function AppViewModel() {
	/*
	step2
	Update your viewmodel to make the firstName and lastName properties observable 
	using ko.observable
	*/
	var self = this;								//this is ViewModel
	self.firstName = ko.observable("Bert");			//text data-binding
	self.lastName = ko.observable("Bertington");	//text data-binding
	self.becomeVisible = ko.observable(true);		//visible data-binding
	self.becomeHidden = ko.observable(true);		//hidden data-binding
	
	self.currentProfit = ko.observable(20);			//text data-binding
	self.profitStatus = ko.pureComputed(function() {		//class data-binding
		//return one of .css style..
		return self.currentProfit() < 0 ? "profitWarning" : "profitPositive";
	}, self);
	
	self.url = ko.observable("http://www.yahoo.com");
	self.details = ko.observable("lets go http://www.yahoo.com");

	//computed obversable
	self.fullName = ko.computed(function() {		//text data-binding
		return self.firstName() + " " + self.lastName();
	}, self);
	
	self.anotherObservableArray = ko.observableArray([
	    { name: "Bungle", type: "Bear" },
	    { name: "George", type: "Hippo" },
	    { name: "Zippy", type: "Unknown" }
	]);
	
	/*
	pure computed
	pureComputed has some performance optimizations, and tries to prevent 
	memory leaks, by being smart about who's tracking its changes.
	You can safely replace computed with pureComputed
	*/
	self.fullName2 = ko.pureComputed({				//textInput data-binding
	    read: function () {
	        return self.firstName() + " " + this.lastName();
	    },
	    write: function (value) {
			/*
			if there is ' ' in value, then 
			take first part to update self.firstName()
			and tale 2md part to update self.lastName()
			*/
	        var lastSpacePos = value.lastIndexOf(" ");
			console.log("fullName2 value="+value);
			console.log("fullName2 lastSpacePos="+lastSpacePos);
	        if (lastSpacePos > 0) { // Ignore values with no space character
	            self.firstName(value.substring(0, lastSpacePos)); // Update "firstName"
	            self.lastName(value.substring(lastSpacePos + 1)); // Update "lastName"
	        }
	    },
	    owner: self
	});

	//-----------------------------------------------------------------
	// Operations
	
	self.capitalizeLastName = function() {			//click data-binding
		var currentVal = self.lastName();        	// Read the current value
		self.lastName(currentVal.toUpperCase()); 	// Write back a modified value
	};
	
	self.testObservables = function() {				//click data-binding
		//ko.observable objects are actually functions
		//To read the observable’s current value
		self.firstName();
		console.log("testObservables self.firstName()="+self.firstName());
		
		//To write a new value to the observable
		//ko.observable is a function so you need to set the value like this
		self.firstName('changed by testObservables');
		console.log("testObservables changed self.firstName()="+self.firstName());
		
		console.log("testObservables self.anotherObservableArray().length="
			+self.anotherObservableArray().length);
		console.log("testObservables self.anotherObservableArray().[0]="
			+self.anotherObservableArray()[0]);
			
		// visible bindings
		self.becomeVisible(false); // ... now it's hidden
		
		// hidden bindings
		self.becomeHidden(false); // ... now it's visible
		
		//class data-binding
		self.currentProfit(-50);
		
		// "attr" binding
		self.url("www.google.com")
		self.details("lets go www.google.com");
	};
}

/*
step3
Activates knockout.js
*/
ko.applyBindings(new AppViewModel());