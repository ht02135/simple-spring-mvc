
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
		var currentVal = this.lastName();        
		this.lastName(currentVal.toUpperCase()); 
	};
}

function onReady() {
	console.log("before applyBindings");
	/*
	step3
	Activates knockout.js
	*/
	ko.applyBindings(new AppViewModel());
	console.log("after applyBindings");
}

if (document.readyState !== "loading") {
	console.log("readyState != loading");
	console.log("before call onReady");
    onReady(); 
	console.log("after call onReady");
} else {
	console.log("else readyState == loading");
	console.log("before call addEventListener");
    document.addEventListener("DOMContentLoaded", onReady);
	console.log("after call addEventListener");
}
