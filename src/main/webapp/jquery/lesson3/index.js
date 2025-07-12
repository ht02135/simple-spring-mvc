
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
	The Drag-able function can be used with interactions in JqueryUI.This 
	function can enable drag-able functionality on any DOM element.
	*/
	//class="ui-widget-content ui-draggable ui-draggable-handle"
	$( "#draggable" ).draggable();
	
	//class="ui-widget-header ui-droppable ui-state-highlight"
	$( "#droppable" ).droppable({
		drop: function( event, ui ) {
			/*
			chained
			1>this is div contains p
			1>add ui-state-highlight to div
			2>find and update p to Dropped
			*/
			$( this ).addClass( "ui-state-highlight" ).find( "p" ).html( "Dropped!" );
		} //callback
	});
	
	$( "#resizable-14" ).resizable({
		create: function( event, ui ) {
			console.log("create called");
			$("#resizable-15").text("I'm Created!!");
		},
		
		resize: function (event, ui) {
			console.log("resize called");
			$("#resizable-16").text("top = " + ui.position.top +
				", left = " + ui.position.left +
				", width = " + ui.size.width +
				", height = " + ui.size.height);
		}
	});
	
	//----------------------------
	//Event handler
	

	
}); // document.ready



