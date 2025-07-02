
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
	    	{ text: account + ' What a nice day.' },
	        { text: account + ' Building some cool apps.' },
	        { text: account + ' Just saw a famous celebrity eating lard. Yum.' }
	    ];
	 
	    self.resultData({ retrievalDate: new Date(), topTweets: simulatedResults });
	};
	
	self.clearResults = function() {
		self.resultData(undefined);
	};
}

ko.applyBindings(new AppViewModel());