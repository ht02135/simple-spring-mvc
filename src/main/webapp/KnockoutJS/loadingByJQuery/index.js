
/*
Observer Design Pattern
Subject: The object that holds the state and notifies the observers.
Observer: Objects that listen for state changes in the subject

The Subject will have methods to register, remove, and notify observers.
*/

$(document).ready(function() {

	function viewModel() {

		/*
		1>Then if we want to make the value of text to updates dynamically then 
		we have to declare it in our view model as an observable.
		2>Knockout implements observable properties by wrapping object properties 
		with a custom function named ko.observable().
		this.property = ko.observable('value')
		*/
		this.dayOfWeek = ko.observable('Sunday'); //declare this.dayOfWeek observable
		this.activity = ko.observable('rest');

		//implements observable properties by wrapping object properties
		this.day = ko.observable('24');
		this.month = ko.observable('02');
		this.year = ko.observable('2012');

		this.fullDate = ko.computed(function() {
			return this.day() + "/" + this.month() + "/" + this.year();
		}, this);
	};

	/*
	The data-bind attribute (explained later) isn’t native to HTML, 
	and the browser doesn’t know what it means. So in order to take 
	effect Knockout has to be activated by inserting ko.applyBindings() 
	function at the end of the script. 

	Also if you use external JavaScript file or your script is placed 
	in the head tag of your document you need to wrap the Knockout code 
	in a jQuery ready function in order to work properly.
	*/
	
	/*
	1>Observer Design Pattern
	  adding/binding view/observer to model/subject
	2>KnockJS 
	  binding viewModel to UI
	  Calling the ko.applyBindings() method and passing in viewModel
	  tells Knockout to bind the specified viewModel to our UI
	3>The first parameter says what view model object you want to use 
	  with the declarative bindings
	4>The second parameter is optional and it defines which part of 
	  the document you want to search for data-bind attributes
	EX
	ko.applyBindings(viewModel, document.getElementById('container'))
	*/
	ko.applyBindings(new viewModel()); // This makes Knockout get to work

});