
function AppViewModel() {
	//-----------------------------------------------------------------
	// Declaration
	
	var self = this;	//this is ViewModel

	self.people = ko.observableArray([ 			//foreach binding
		{ name: 'Bert', city: null },
	    { name: 'Charles', city: "Boston" },
	    { name: 'Denise', city: "Chicago"  }
	]);
	
	self.display = ko.observable(false);	//checkbox checked binding
	
	self.cityCoords = ko.observable(
		{ city: 'London', latitude:  51.5001524, longitude: -0.1262362 }
	);
	
	self.twitterAccount = ko.observable('@example');	//input value binding
	self.resultData = ko.observable(); // No initial value
	
	self.numberOfClicks = ko.observable(0);	//click binding
	
	self.detailsEnabled = ko.observable(false),	//visible binding
	
	self.hasCellphone = ko.observable(false);
	
	/*
	this one has value binding, but we not making it observable...
	that just mean if self.cellphoneNumber got updated, then it will
	not propagate to front
	*/
	self.cellphoneNumber = "111-1111";
	
	self.userName = ko.observable(""),        	// Initially blank
	self.userPassword = ko.observable("") 		// Initially blank
	self.loginInfo = ko.computed(function() { 
		console.log("called loginInfo");
		return self.userName() + " " + self.userPassword();
	}, self);
	
	self.isSelected = ko.observable(false);		//hasFocus binding
	
	self.agreeFlag =  ko.observable(false);
	
	self.spamFlavor = ko.observable("almond");

	//-----------------------------------------------------------------
	// Operations

	self.addPerson = function() {				//a link click bindinig
		console.log("called addPerson");
		self.people.push({ name: "New at " + new Date() });
	};

	/*
	pass in $data which in this case is an entry of people array?
	*/
	self.removePerson = function(person) {		//button click bindinig
		console.log("called removePerson person="+person);
		if (self.people.indexOf(person) >= 0) {
			console.log("remove person().name="+person.name);
			self.people.remove(person);
		}
	};
	
	self.getTweets = function() {
	    var account = self.twitterAccount();
	    var simulatedResults = [
			/*
			These bindings differ in how they deal with a value of null or undefined:
			1>in case of with binding, it will not show null
			2>in case of using binding, it pop Uncaught ReferenceError in console.
			translate why would anyone use using bindin? pretty MESS binding...
			*/
	    	{ text: null },
	        { text: account + ' Building some cool apps.' },
	        { text: account + ' Just saw a famous celebrity eating lard. Yum.' }
	    ];
	    self.resultData({ retrievalDate: new Date(), topTweets: simulatedResults });
	};
	
	self.clearResults = function() {			//button click binding
		self.resultData(undefined);
	};
	
	self.incrementClickCounter = function() {	//button binding
		var previousCount = self.numberOfClicks();
	    self.numberOfClicks(previousCount + 1);
	};
	
	/*
	could leave param empty
	but by default, event binding, kn pass data and event obj...
	from debugger, data seems to be $root
	*/
	self.enableDetails = function(data, event) {			//event binding
	    self.detailsEnabled(true);
		console.log("call enableDetails");
		console.log("data="+data);
		console.log("event.type="+event.type);
	};
		   
	self.disableDetails = function(data, event) {			//event binding
		self.detailsEnabled(false);
		console.log("call disableDetails");
		console.log("data="+data);
		console.log("event.type="+event.type);
	};
	
	self.setIsSelected = function() { 
		self.isSelected(true);
	};
	
	self.finish = function() { 
		console.log("call finish");
	};
	
	self.selectFlavor = function(flavor) { 
		console.log("call selectFlavor flavor="+flavor);
		console.log("call selectFlavor self.spamFlavor()="+self.spamFlavor());
	}
}

ko.applyBindings(new AppViewModel());