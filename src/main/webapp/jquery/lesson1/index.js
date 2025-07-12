
var myVar = "global";     // ==> Declare a global variable

//--------------------------------------------------
/*
if your function depend on document ready, then probably 
good idea to defind all function in ready???
///////////////////////////////////////////
before we execute any jQuery statement, we would like to wait for the 
document to be fully loaded. This is because jQuery works on DOM and if 
complete DOM is not available before executing jQuery statements, then 
we will not get desired result
*/
$(document).ready(function() {
	console.log("document is ready");
	
	/*
	JavaScript (jQuery) supports Object concept very well. You can create an object 
	using the object literal
	*/
	var emp = {
	   name: "Zara",
	   age: 10
	};
	emp.name = "updated name";	//set
	console.log("emp.name="+emp.name); //get
	
	var x = [1, 2, 3, 4, 5];
	var y = [];

	for (var i = 0; i < x.length; i++) {
	   // Do something with x[i]
	   y[i] = x[i];
	}
	console.log("x="+x);	//show x=1,2,3,4,5
	console.log("y="+y);	//show y=1,2,3,4,5
	
	//----------------------------
	// functions

	function someFunction() {
	   var myVar = "local";   // ==> Declare a local variable
	   console.log("this.myVar="+this.myVar); 	//this.myVar=global
	   
	   console.log("myVar="+myVar);				//myVar=local
	}

	/*
	JavaScript (jQuery) variable arguments is a kind of array which has length property
	*/
	function func(x) {
	   console.log("type="+typeof x, "len="+arguments.length, "x="+x);
	}

	/*
	A function in JavaScript (jQuery) can be either named or anonymous. 
	A named function can be defined using function keyword as follows
	*/
	function namedFunction() {
		console.log("namedFunction called");
	}

	/*
	An anonymous function can be defined in similar way as a normal function but 
	it would not have any name.
	A anonymous function can be assigned to a variable or passed to a method as shown below.
	*/
	var anonyFunctionVar = function () {
		console.log("anonyFunctionVar called");
	}

	/*
	JavaScript (jQuery) closures are created whenever a variable that is defined 
	outside the current scope is accessed from within some inner scope.
	/////////////////
	variable counter is visible within the create, increment, and print functions
	*/
	function create() {
	   var counter = 0;

	   return {
	      increment: function() {
	         counter++;
	      },
		   print: function() {
	         console.log("counter"+counter);
	      }
	   }
	}
	
	//----------------------------
	//Event handler
	
	/*
	we register a ready event for the document
	///////////////////////////////
	A jQuery Event is the result of an action that can be detected by jQuery 
	(JavaScript). When these events are triggered, you can then use a custom 
	function to do pretty much whatever you want with the event. These custom 
	functions are called Event Handlers.
	/////////////////////////////
	lists some of the important DOM events.
	1>Mouse Events
	click, hover
	2>Form Events
	submit, change, select, focusin
	3>load, ready, unload
	*/
	$('#myButton').click(function() {
		console.log("Button clicked!");
		namedFunction();
		anonyFunctionVar();
		
		/*
		type=undefined len=0 x=undefined
		index.js:33 type=number len=1 x=1
		index.js:33 type=number len=2 x=1
		index.js:33 type=string len=3 x=1
		*/
		func();                
		func(1);               
		func(1,2);
		func("1", "2", "3");   
		
		someFunction();
		
		var c = create();
		c.increment();
		c.print(); 
		
		/*
		Any jQuery statement starts with a dollar sign $ and then we put 
		a selector inside the braces (). This syntax $(selector) is enough 
		to return the selected HTML elements, but if you have to perform 
		any action on the selected element(s) then action() part is required.
		*/
		//$("p").hide();
		$("h1").css("color", "red");
		
		/*
		A jQuery selector starts with a dollar sign $ and then we put a 
		selector inside the braces (). Here $() is called factory function, 
		which makes use of following three building blocks while selecting 
		elements in a given document
		1>The element Selector
		The element Selector $('p') selects all paragraphs
		2>The #id Selector
		$('#some-id') selects the single element in the document that has 
		some-id as element Id
		3>The .class Selector
		$('.some-class') selects all elements in the document that have a 
		class of some-class.
		*/
		$("p").css("background-color", "yellow");
		$("#foo").css("background-color", "blue");
		$(".bar").css("background-color", "orange");
		
	});
	
	/*
	jQuery Event Binding Syntax follow by function handler
	////////////////
	*/
	//got feeliing this is origiinal event hanler setup
	$("div").bind('click', function(eventObj){ //to bind a click event with <div>
		console.log("div clicked!");
		
		/*
		Query Event Object
		Whenever a jQuery event is fired, jQuery passes an Event Object to every 
		event handler function.The event object provides various useful information 
		about the event.
		/////////////////////
		1>data
		The value, if any, passed as the second parameter to the bind() command 
		when the handler was established.
		2>target
		Identifies the element for which the event was triggered.
		3>type
		For all events, specifies the type of event that was triggered (for example, click).
		/////////////////////////
		eventObj.data=null
		eventObj.target=[object HTMLSpanElement]
		eventObj.type=click
		*/
		console.log("eventObj.data="+eventObj.data);
		console.log("eventObj.target="+eventObj.target);
		console.log("eventObj.type="+eventObj.type);
		
		//this Keyword in Event Handler
		//$(this).text()=This is bar span tag
		console.log("$(this).text()="+$(this).text());
	});
	
	//gut feeling this is shorthand version..
	$('h1').click(function() { //to bind a click event with h1
		console.log("hi clicked!");
	});
	
	/*
	jQuery attr() method is used to fetch the value of any standard attribute 
	from the matched HTML element(s). We will use jQuery Selectors to match 
	the desired element(s) and then we will apply attr() method to get the 
	attribute value for the element
	*/
	$("#homeButton").click(function(){
		console.log( "#homeButton clicked");
		
		/*
		Href = index.htm
		Title = Tutorials Point
		*/
		console.log( "Href = " + $("#home").attr("href"));
	    console.log( "Title = " + $("#home").attr("title"));
		
		/*
		jQuery - Set Standard Attributes
		jQuery attr(name, value) method is used to set the value of any standard 
		attribute of the matched HTML element(s)
		*/
		$("#home").attr("title", "updated title attribute to this");
		
	});
	
	$("#driver").click(function(event){
		console.log( "#driver clicked");
		
		/*
		Here load() initiates an Ajax request to the specified URL 
		/result.html file. After loading this file, all the content would 
		be populated inside <div> tagged with ID stage
		/////////////////////
		[selector].load( URL, [data], [callback] );
		blocked by CORS policy
		need to deploy WAR
		*/
	    $('#stage').load('result.html');
		
		//[selector].getJSON( URL, [data], [callback] );
		$.getJSON('result.json', function(jd) {
		   $('#stage2').html('<p> Name: ' + jd.name + '</p>');
		   $('#stage2').append('<p>Age : ' + jd.age+ '</p>');
		   $('#stage2').append('<p> Sex: ' + jd.sex+ '</p>');
		});
		
		/*
		Passing Data to the Server
		This example demonstrate how can pass user input to a web 
		server script which would send the same result back and we would print it
		$(selector).load(url,data,function(response,status,xhr))
		*/
		//jQuery val() method is used to get the value from any form field
		var name = $("#name").val();
		$("#stage3").load('result.json', {"name":name} );	//data={"name":name}
		
		/*
		JQuery AJAX Methods
		1>jQuery.ajax( options )
		Load a remote page using an HTTP request.
		2>jQuery.get( url, [data], [callback], [type] )
		Load a remote page using an HTTP GET request.
		3>jQuery.getJSON( url, [data], [callback] )
		Load JSON data using an HTTP GET request.
		4>jQuery.post( url, [data], [callback], [type] )
		Load a remote page using an HTTP POST request.
		5>load( url, [data], [callback] )
		Load HTML from a remote file and inject it into the DOM.
		///////////////////
		You can go through all the AJAX Events.
		6 event but in reality what really matter is these 2
		1>ajaxSuccess( callback )
		Attach a function to be executed whenever an AJAX request completes successfully.
		2>ajaxError( callback )
		Attach a function to be executed whenever an AJAX request fails.
		*/
				
		/*
		jQuery val() method is also used to set the value from any form field
		$(selector).val(val);
		*/
		$("#name").val("this is updated from js script");
	});

	/*
	jQuery provides html() and text() methods to extract the content of the 
	matched HTML element.
	$(selector).html();
	$(selector).text();
	/////////////////////////////
	jQuery - Set Content
	$(selector).html(val, [function]);
	$(selector).text(val, [function]);
	*/		
	$("#textButton").click(function(){
		console.log( "#textButton clicked");
		$("#tobeupdated").text("The quick <b>brown fox</b> jumps over the <b>lazy dog</b>");
	});
	$("#htmlButton").click(function(){
		console.log( "#htmlButton clicked");
		$("#tobeupdated").html("The quick <b>brown fox</b> jumps over the <b>lazy dog</b>");
	});
	
	$("#replaceButton").click(function(){
		console.log( "#replaceButton clicked");
		$("#replaceP").replaceWith('<h2 id = "replaceP">This is another heading</h2>');
	});
});



