
function AppViewModel() {
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

function onReady() {
	console.log("before applyBindings");
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
