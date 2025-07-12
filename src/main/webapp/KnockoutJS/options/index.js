
function Country(name, population) {
    var self = this;

	self.countryName = name;
	self.countryPopulation = population;  
}

function CountryViewModel() {
	//-----------------------------------------------------------------
	// Declaration
	
    var self = this;
	
	self.availableCountries2 = ko.observableArray(['France', 'Germany', 'Spain'])
	self.selectedCountry2 = ko.observable();
	
	self.availableCountries = ko.observableArray([
		new Country("UK", 65000000),
		new Country("USA", 320000000),
		new Country("Sweden", 29000000)
	]);
	self.selectedCountry = ko.observable();
	
	//-----------------------------------------------------------------
	// Operations
		
	self.additionalRender = function(option, item) {
		console.log("called additionalRender");
		console.log("called option="+option);
		console.log("called item="+item);
		if (self.selectedCountry()) {
			console.log("called self.selectedCountry()="+self.selectedCountry());
		}
		
		var countryIndex = self.availableCountries.indexOf(option);
		if (countryIndex >= 0) {
			console.log("loading countryIndex="+countryIndex+" for option="+option);
		}
		countryIndex = self.availableCountries.indexOf(item);
		/*
		check if item is Country entry? if yes, then console log item.countryName
		1>notice i used item.countryName <-- because item is not ko not function
		2>if i do item().countryName <-- this will run into error...
		*/
		if (countryIndex >= 0) {
			console.log("loading countryIndex="+countryIndex+" for item="+item);
			console.log("loading countryIndex="+countryIndex+" for item.countryName="+item.countryName);
		}
	};
}

ko.applyBindings(new CountryViewModel());