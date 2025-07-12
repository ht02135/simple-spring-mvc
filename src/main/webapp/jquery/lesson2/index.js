
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
	//vars
	
	/*
	jQuery append() Method
	The jQuery append() method adds the content at the end of the matched 
	each element(s)
	*/
	$( ".inner2" ).append( "<p>Zara2</p>" );
	
	/*
	$(content).appendTo(selector);
	////////////////////////
	The jQuery appendTo() method performs the same task as done by appendTo(). 
	The major difference is in the syntax-specifically, in the placement of the content and target.
	*/
	$("<p>Zara3</p>").appendTo(".inner3");	// to be stupid dup
	
	/*
	jQuery after() Method
	The jQuery after() method adds the content after the matched each element(s)
	/////////////////
	$(selector).after(content, [content]);
	$(content).insertAfter(selector); 
	*/
	$( ".inner4" ).after( "<p>Zara4</p>" );
	$( "<p>Zara5</p>" ).insertAfter( ".inner5" ); // to be stupid dup
	
	/*
	jQuery remove() Method
	The jQuery remove() method removes the selected element(s) and it's child 
	elements from the document.
	$(selector).remove();
	*/
	$( ".hello" ).remove();
	
	/*
	Remove with Filter
	same as prev code
	1>selector=div
	2>filter=.hello
	*/
	$("div").remove(".hello");
	
	//----------------------------
	//Event handler
	
	/*
	jQuery - Adding CSS Classes
	jQuery provides addClass() method to add a CSS class
	////////////////////
	$(selector).addClass(className);
	$(selector).addClass("Class1 Class2");
	*/
	$("#add_css").click(function(){
		console.log( "#add_css clicked");
		//result <div class="hello4 big">Remove Hello</div>
		$( ".hello4" ).addClass("big" );
		$( ".goodbye4" ).addClass("small" );
	});
	
	$("#remove_css").click(function(){
		console.log( "#remove_css clicked");
		$( ".hello4" ).removeClass("big" );
		//result add/delete 'small' <div class="hello4 big small">Remove Hello</div>
		$( ".goodbye4" ).removeClass("small" );
	});
	
	/*
	Toggle CSS Classes
	$(selector).toggleClass(className);
	////////////////////
	If an element in the matched set of elements already has 
	the class, then it is removed; if an element does not have the class, then it is added.
	*/
	$("#toggle_css").click(function(){
		console.log( "#toggle_css clicked");
		//$( ".hello4" ).addClass("big" );
		//$( ".goodbye4" ).addClass("small" );
		
		$( ".big" ).toggleClass("small" ); //search '.big' and toggle into 'small'
	});
	
	/*
	https://www.tutorialspoint.com/jquery/jquery-dimensions.htm
	width() - This method gets or sets the width of an element
	height() - This method gets or sets the height of an element
	innerWidth() - This method gets or sets the inner width of an element.
	innerHeight() - This method gets or sets the inner height of an element.
	outerWidth() - This method gets or sets the outer width of an element.
	outerHeight() This method gets or sets the outer height of an element.
	*/
	$("#b1").click(function(){
	   console.log("Box width = " + $("#my_dimension").width());
	   console.log("Box height = " + $("#my_dimension").height());
	});
	$("#b2").click(function(){
	   $("#my_dimension").width("400px");
	   $("#my_dimension").height("100px");
	});
	
	/*
	jQuery - Get CSS Properties
	jQuery css() method can be used to get the value of a CSS property 
	associated with the first matched HTML element
	////////////////////
	$(selector).css(propertyName);
	*/
	$("#b3").click(function(){
		/*
		background-color = rgba(0, 0, 0, 0)
		background-color = undefined
		////////////////////
		$("div").css("background-color"));
		you need combo 'selector' and 'filter'
		*/
		console.log("background-color = " + $("#d3a").css("background-color"));
		console.log("background-color = " + $(".background-color").css(""));
	});
	
	/*
	jQuery - Set CSS Properties
	jQuery css() method can be used to set the value of one or more CSS properties 
	associated with the matched HTML element
	$(selector).css(propertyName, value);
	*/
	$("#b4").click(function(){
		var color = $("#d4a").css("background-color");
		console.log("background-color = " + $("#d4a").css("background-color"));
		console.log("color = " + color);
		/*
		before click
		<p id="p4">Click the below button to see the result:</p>
		after click, adfrf css
		<p id="p4" style="color: rgb(0, 0, 255);">Click the below button to see the result:</p>
		*/
		$("#p4").css("color", color);
		
		/*
		jQuery - Set Multiple CSS Properties
		////////////////////
		<p id="p4" 
		   style="color: rgb(0, 0, 255); 
		          background-color: rgb(251, 124, 124); 
				  font-size: 25px;
		   ">Click the below button to see the result:</p>
		*/
		$("#p4").css({
			"color":color,
			"background-color":"#fb7c7c", 
			"font-size": "25px"
		});
	});
	
	/*
	$(selector).hide( [speed, callback] );
	$(selector).show( [speed, callback] );
	/////////////////////
	speed − An optional string representing one of the three 
	predefined speeds ("slow", "normal", or "fast") or the number of 
	milliseconds to run the animation (e.g. 1000).
	*/
	$("#show").click(function(){
		console.log("show");
	   	//$("#box").show(1000);
		$("#box").show(1000, function(){
			console.log("#box shown now");
		});
	});
	$("#hide").click(function(){
		console.log("hide");
	   	//$("#box").hide(1000);
		
		/*
		A jQuery Callback Function is a function that will be executed only after the 
		current effect gets completed
		$(selector).effectName(speed, callback);
		*/
		$("#box").hide(1000, function(){
			console.log("#box hidden now");
		});
	});
	
	/*
	jQuery gives us two methods - fadeIn() and fadeOut() to fade the DOM 
	elements in and out of visibility.
	$(selector).fadeIn( [speed, callback] );
	$(selector).fadeOut( [speed, callback] );
	*/
	$("#fade_in").click(function(){
		console.log("fadeIn");
	   	$("#box2").fadeIn(1000);
	});
	$("#fade_out").click(function(){
		console.log("fadeOut");
	   	$("#box2").fadeOut(1000);
	});
	$("#fade_out_in").click(function(){
		console.log("fadeOut then fadeIn");
		/*
		jQuery Method Chaining
		jQuery method chaining allows us to call multiple jQuery methods using a s
		ingle statement on the same element(s).
		*/
		$("#box2").css("color", "#fb7c7c").fadeOut(1000).fadeIn(1000);
	});
	
	/*
	jQuery animate() Method
	$(selector).animate({ properties }, [speed, callback] );
	properties − A required parameter which defines the CSS properties 
	to be animated and this is the only mandatory parameter of the call.
	//////////////////
	When we click on Right Move button, this <div> starts moving towards 
	the right direction till it reaches a left property value to 250px, 
	at the same time element's opacity reduces to 0.2 and width & height 
	of the box decreases to 100px. Next, when we click on the Left Move 
	button, this box returns to its initial position and size.
	*/
	$("#right").click(function(){
		console.log("animate left 250px");
		//to animate multiple CSS properties
	   	$("#box3").animate(
			{left: '250px', width:'100px', height:'100px', opacity:0.2}, 
			1000, function(){
			console.log("I have reached to the right");
		});
	});
	$("#left").click(function(){
		console.log("animate  left 0px");
	   	$("#box3").animate({left: '0px',width:'180px', height:'100px', opacity:1.0}, 
			1000, function(){
			console.log("I have reached to the left");
		});
	});
	
	/*
	jQuery parentsUntil() Method
	The jQuery parentsUntil() method returns all the ancestor elements available 
	between two selectors
	$(selector1).parentsUntil([selector2][,filter])
	*/
	$("#parentsUntil").click(function(){
		/*
		parent and grand-parent will add border: 2px solid red;
		<div style="width:525px;" class="great-grand-parent">
			<div style="width: 500px; border: 2px solid red;" class="grand-parent">
				<ul class="parent" style="border: 2px solid red;">
		*/
		$( ".child-two" ).parentsUntil(".great-grand-parent").css( "border", "2px solid red" );
	});
	
	//$(selector).children([filter])
	$("#children").click(function(){

		/*
		<div style="width:525px;" class="great-grand-parent">
			<div style="width: 500px; border: 2px dashed blue;" class="grand-parent">
				<ul class="parent">
					<li class="child-one">Child One</li>
		            <li class="child-two">Child Two</li>
				</ul>
			</div>
		</div>
		/////////////////
		weird seems not all children is impacted???
		*/
		//$( ".great-grand-parent" ).children().css( "border", "2px dashed blue" );
		 
		/*
		jQuery find() Method
		The jQuery find() method returns all descendants of the matched element
		*/
		$( ".grand-parent" ).find("li").css( "border", "2px dashed blue" );
	});
	
}); // document.ready



