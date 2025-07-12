
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
	
	//----------------------------
	//Declaration
	
	/*
	https://www.w3schools.com/js/js_let.asp
	1>Variables declared with let have Block Scope
	2>Variables declared with the var always have Global Scope.
	*/
	//Variables declared inside a { } block cannot be accessed from outside the block:
	{
	  let i_am_block_level = 2;
	}
	
	//Variables declared with varinside a { } block can be accessed from outside the block:
	{
	  var i_am_global_level = 2;
	}

	//----------------------------
	//Event handler
	
	/*
	bind:
	$('#mydiv').bind('click', function() {
	});
	////////////////////////////
	click:
	$('#mydiv').click(function() {
	}
	////////////////////////////
	on:
	$('mydiv').on('click', function() {
	}
	////////////////////////////
	The direct methods are preferable because your code will be less 
	stringly typed. You will get immediate error when you mistype an 
	event name rather than a silent bug. In my opinion, it's also easier 
	to write and read click than on("click"
	translate below is better
	$('#mydiv').click(function() {
	}
	*/
	
	/*
	bind()
	click vs click()
	upload
	mouseenter vs mouseenter()
	mouseleave vs mouseleave()
	scroll
	blur, focus, etc.  vs blur()
	//////////////
	$(selector).bind( eventType [, eventData ], handler )
	$('button').bind('click' [, eventData ], handler )
	///////////////
	avoid .bind and .on and other crap since
	1>there is direct method (short clean less type)
	2>according to doc, it can handle multiple event, but so big deal?
	  event_type param seem only can specify one event_type
	  is not if you can specify more than one event_type for one
	  handler and not sure if to be able to do that is a good iead
	3>avoid .on too, because .bind just wrap .on
	*/
	
	/*
	change()
	The change event occurs (or is triggered) when the value of an element is changed
	<input>
	<textarea>
	<select>
	$(selector).change(function)
	*/
	$('#change_i').change(function(){
		console.log("change_i change");
	});
	
	/*
	dblclick()
	///////////////////
	in summary, on your computer desktop:
	Click once (called a click) using the left mouse button to select an icon.
	Click twice (called a double click) on an icon using the left mouse button to 
	open a program or folder.
	////////////////////
	Double-clicking on the web should be avoided because it goes against the general 
	practice of single-clicking links, and would likely be confusing.
	...double-click must die since it causes novice users great difficulties and since 
	it conflicts with the single-click interaction style of the Web
	/////////////////////
	translate double click is dead
	*/
	
	/*
	delegate()
	$(selector).delegate(childSelector, event, data, function)
	/////////////////////////////
	The jQuery event delegate() method is used to attach one or more 
	event handlers to the specified element that are child elements of 
	the specified elements, and speicify a function to run (execute) 
	when an events occur.
	*/
	$("#select_h3").click(function(){
		console.log("select_h3 click");
	    $("ul li h3").css("background-color", "yellow");
		$("#members li p").css("background-color", "red");
		
		//child
		/*
		translate
		1>ul > li returns list of p
		2>then p:eq(2) operate on that p list
		*/
		//$("ul > li p:eq(2)").css("background-color", "green");
		//$("ul > li p:eq(3)").css("background-color", "red");
		//$("ul > li p:eq(4)").css("background-color", "blue");
		
		//this is fine
		//$("ul > li").css("background-color", "green");
		
		//descendant
		//$("ul li p:eq(2)").css("background-color", "blue");
		
		//this is fine
		//$("ul li").css("background-color", "blue");
	});
	
	/*
	isDefaultPrevented()
	event.isDefaultPrevented()
	allows you to determine whether the default action of an event has been prevented
	//////////////////////
	preventDefault()
	event.preventDefault()
	when you wantÂ to prevent a browser from executing its default behavior
	//////////////////////
	I'm learning more about handling forms in JavaScript, and I keep seeing 
	event.preventDefault() being used in form submit handlers
	-------------
	it prevents the default behavior of the event handler (which is it bubbles 
	up and submits the form action automatically). This is useful when you 
	want to do anything custom other than that default redirect. Basically 
	it allows you to make the form submission an async post request
	--------------
	Submitting a form will typically involve a POST request and a page reload. 
	If you're handling the submission on the front end (e.g. complex validation 
	rules, and/or by asyncronously posting the data via fetch), you'd want 
	to call preventDefault to stop that default behavior so you can handle 
	it yourself. 
	*/
	
	//This code will prevent the browser from redirecting the page to another PHP file.
	$("#preventDefault_a").click(function (event) {
		/*
		this seems to abort default behavior of element after .click event
		handler returns...
		*/
		event.preventDefault();
	    console.log("a click event prevented");
		console.log("Was preventDefault() called event.defaultPrevented=" + event.defaultPrevented);
	});
	
	/*
	Preventing browser actions
	There are two ways to tell the browser we don’t want it to act:
	1>The main way is to use the event object. There’s a method event.preventDefault().
	2>If the handler is assigned using on<event> (not by addEventListener), then 
	returning false also works the same.
	/////////////////////
	So we use <a> in the markup. But normally we intend to handle clicks in JavaScript. 
	So we should prevent the default browser action.
	*/
	$("a").click(function (event) {
		console.log("event.target.nodeName="+event.target.nodeName);
		if (event.target.nodeName != 'A') return;
		let href = event.target.getAttribute('href');
		console.log("href="+href); // ...can be loading from the server, UI generation etc
		
		event.preventDefault();
		return false; //false does not seem to prevent redirect
	});
	
	/*
	select()
	triggers that event when the text within the <input> or <textarea> element is selected.
	////////////////
	to be honest
	change() >>>>>>>>>>>>>>>>>>>>>>>> select()
	*/
	$('#select_change_i').change(function(){
		console.log("select_change_i change");
		var value = $('#select_change_i').value;
		console.log("value="+value);
	});
	$('#select_change_i').select(function(){
		console.log("select_change_i select");
		var value = $('#select_change_i').value;
		console.log("value="+value);
		console.log("i_am_global_level="+i_am_global_level);
		/*
		i_am_also_global_level_defined_god_knows_where is var
		but var does not mean true global...  scope still matters...
		*/
		//console.log("i_am_also_global_level_defined_god_knows_where="+i_am_also_global_level_defined_god_knows_where);
	});
	
	/*
	trigger() method is a method that is used to trigger a specified 
	event handler on the selected element
	*/
	$("#btn1").click(function () {
	    Increase($(".box-1>h1"))
	})

	$("#btn2").click(function () {
	    $("#btn1").trigger("click");
	    Increase($(".box-2>h1"))
	})

	function Increase(obj) {
		var i_am_also_global_level_defined_god_knows_where = 22;
		
	    let text = parseInt(obj.text(), 10);
	    obj.text(text + 1);
		//i_am_global_level
		console.log("i_am_global_level="+i_am_global_level);
	}
	
	/*
	1>e.stopImmediatePropagation() stops any further handler from being called for 
	this event, no exceptions
	2>e.stopPropagation() is similar, but does still call all handlers for this phase 
	on this element if not called already
	///////////////////
	.g. a click event will always first go all the way down the DOM (called “capture 
	phase”), finally reach the origin of the event (“target phase”) and then bubble 
	up again (“bubble phase”). And with addEventListener() you can register multiple 
	handlers for both capture and bubble phase independently. (Target phase calls 
	handlers of both types on the target without distinguishing.)
	////////////////////
	by default here is event propagation
	First event handler on #child
	Second event handler on #child
	Event handler on div: #child
	Event handler on div: #parent
	////////////////////
	state.stopPropagation=true => does not reach parent
	First event handler on #child
	Second event handler on #child
	Event handler on div: #child
	//////////////////////
	state.stopImmediatePropagation=true => no more propagation
	First event handler on #child
	*/
	// Enable/disable propogation
	var state = {
		stopPropagation: false, 		//id of stopPropagation button
		stopImmediatePropagation: false //id of stopPropagation button
	};
	
	function handlePropagation(event) {
		//console.log("=== handlePropagation begin ===========================");
		//console.log("handlePropagation called");
		//console.log("state.stopPropagation="+state.stopPropagation);
		//console.log("state.stopImmediatePropagation="+state.stopImmediatePropagation);
		
		//stopPropagation() is similar, but does still call all handlers for this phase 
		//on this element if not called already
		if (state.stopPropagation) {
			event.stopPropagation();
		}

		//stopImmediatePropagation() stops any further handler from being called for 
		//this event, no exceptions
		if (state.stopImmediatePropagation) {
			event.stopImmediatePropagation();
		}
		//console.log("=== handlePropagation end ===========================");
	}

	//order of declare click matters
	$("#child").click(function(e) {
		handlePropagation(e);
		console.log("First event handler on #child");
	});

	//order of declare click matters
	$("#child").click(function(e) {
		handlePropagation(e);
		console.log("Second event handler on #child");
	});

	// First this event will fire on the child element, then propogate up and
	// fire for the parent element.
	$("div").click(function(e) {
		handlePropagation(e);
		console.log("Event handler on div: #" + this.id);
	});
	
	$(".propagation_btn").click(function() { //class='propagation_btn'
		console.log("propagation_btn click");
		
		var objectId = this.id;
		//The toggleClass() method toggles between adding and removing one or more class names from the selected elements.
		//this toggle=add/remove class=active
		$(this).toggleClass('active');
		//The .hasClass() method will return true if the class is assigned to an element
		state[objectId] = $(this).hasClass('active');
		
		/*
		propagation_btn click
		index.js:305 objectId=stopPropagation
		index.js:306 state[objectId]=true
		index.js:315 state.stopPropagation=true
		index.js:316 state.stopImmediatePropagation=false
		*/
		//console.log("objectId="+objectId);
		//console.log("state[objectId]="+state[objectId]);
		
		/*
		both declared as
		state.stopPropagation = false
		state.stopPropagation = false
		//////////////////
		it will toggle once clicked on button
		*/
		console.log("state.stopPropagation="+state.stopPropagation);
		console.log("state.stopImmediatePropagation="+state.stopImmediatePropagation);
	});
	
}); // document.ready



