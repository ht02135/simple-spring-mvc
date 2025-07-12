
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
	//vars or setup
	
	/*
	#id
	Selects an element with the specified id.
	*/
	$("#id_btn").click(function(){
	    $("#id_div").css("background-color", "yellow");
	});
	
	/*
	.class
	Selects all elements with the specified class.
	//////////////////////
	multiple classes
	$(".class1,.class2, ...")
	*/
	$(".class_btn").click(function(){
	    $(".class_p").css("background-color", "yellow");
		$(".one, .two, .three").css("background-color", "yellow");
	});

	/*
	element
	Selects all elements with the specified tag name.
	*/
	$("#element_btn").click(function(){
		console.log("element_btn clicked");
	    $('p').css("font-weight", "bold");
		$('button').css("font-weight", "bold");
	});

	/*
	:first-child
	Selects every element that is the first child of its parent.
	*/
	$("#first_child_btn").click(function(){
		console.log("first_child_btn clicked");
	    $("#first_child_p, p:first").css("background-color", "red");
	});

	/*
	parent > child
	Selects all child elements that are a direct child of the parent.
	("parent > child")
	*/
	$("#parent_child_btn").click(function(){
		console.log("parent_child_btn clicked");
	    $("div > p").css("background-color", "green");
	});

	/*
	parent descendant
	Selects all descendant elements that are a descendant of the parent.
	("parent descendant")
	*/
	$("#parent_descendant_btn").click(function(){
		console.log("parent_descendant_btn clicked");
	    $("div span").css("background-color", "blue");
	});
	
	/*
	:eq(index)
	Selects the element with the specified index.
	//////////////
	$(":eq(index)")
	*/
	$("#eq_btn").click(function(){
		console.log("eq_btn clicked");
		$("p:eq(2)").css("font-weight", "bold");
	});
	
	/*
	multiple selector
	1>OR ==> '.classA, .classB'
	2>AND ==> '.classA, .classB'
	////////////////
	.find( selector )
	A string containing a selector expression to match elements against.
	//////////////////
	Commas separate selectors and therefore behave as a logical OR. 
	Chaining expressions into a single selector behave as a logical AND.
	*/
	
	//----------------------------
	//Event handler
	
}); // document.ready



