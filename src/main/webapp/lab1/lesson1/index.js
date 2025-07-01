
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
	this.firstName = ko.observable("Bert");
	this.lastName = ko.observable("Bertington");

	this.fullName = ko.computed(function() {
		return this.firstName() + " " + this.lastName();
	}, this);

	this.capitalizeLastName = function() {
		var currentVal = this.lastName();        // Read the current value
		this.lastName(currentVal.toUpperCase()); // Write back a modified value
	};
}

/*
step3
Activates knockout.js
*/
ko.applyBindings(new AppViewModel());