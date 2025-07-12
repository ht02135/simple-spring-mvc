
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
	The Widget accordion function can be used with widgets in JqueryUI.Accordion is 
	same like as Tabs,When user click headers to expand content that is broken into logical sections.
	/////////////////
	https://api.jqueryui.com/accordion/
	*/
	$( "#accordion" ).accordion();
	
	//this is not jquery button or input
	//this is to initialize https://api.jqueryui.com/button/
	$( "input[type = submit], a, button" ) // interesting with 3 selectors
	.button()
	.click(function( event ) {
	   console.log("clicked event="+event);
	});
	
	/*
	https://api.jqueryui.com/datepicker/
	call onSelect
	dateText=07/02/2025
	date=07/02/2025
	*/
	$('#datepicker').datepicker( {
		onSelect: function(dateText, inst) {
			console.log("call onSelect");
			console.log("dateText="+dateText);
			var date = $(this).val();
			console.log("date="+date);
		},
		selectWeek: true,
	    inline: true,
	    startDate: '01/01/2000',
	    firstDay: 1,
	});
	
	/*
	clicked datepicker_btn
	index.js:47 date=07/02/2025
	*/
	$("#datepicker_btn").click(function(){
		console.log("clicked datepicker_btn");
		var date = $( "#datepicker" ).val();
		console.log("date="+date);
	});
	
	// https://api.jqueryui.com/dialog/
	$("#dialog").dialog({
	    autoOpen: false,		//autoOpen is Options
	    show: {					//show is Options
	        effect: "blind",
	        duration: 1000
	    },
	    hide: {					//hide is Options
	        effect: "explode",
	        duration: 1000
	    }
	});

	$("#opener").click(function () {
		console.log("clicked opener");
	    $("#dialog").dialog("open"); //call dialog open method
	});
	
	$('#saver').on('click', function () {
		console.log("clicked saver");
	    $('#dialogTarget').text($('#dialogInput').val());
	    $("#dialog").dialog('close'); //call dialog close method
	});
	
	//https://api.jqueryui.com/progressbar/
	$("#progressbar").progressbar({
	    value: 5
	});

	$("#incrementBtn").on('click', function () {
		//getter
	    var val = $("#progressbar").progressbar("value");
		val = val + 5;
	    console.log("Progressbar val: " + val);
		// Setter
		$("#progressbar").progressbar( "option", "value", val );
		console.log("Progressbar increased val: " + val);
	});
	
	//Select Menu
	//https://api.jqueryui.com/selectmenu/
	//$( "#speed" ).selectmenu();
	$( "#speed" ).selectmenu({
	  select: function( event, ui ) {
		console.log("called selectmenu.select");
		console.log("ui="+ui);
		
		//For single select dom elements, to get the currently selected value:
		var selectedVal = $('#speed option:selected').val();
		console.log("selectedVal="+selectedVal); //selectedVal=Slower
		
		//To get the currently selected text:
		var selectedText = $('#speed option:selected').text();
		console.log("selectedText="+selectedText); //selectedText=Slower
	  }
	});
	
	//tabs
	$( "#tabs" ).tabs();
	
	//tooltip
	$( document ).tooltip();
	
	//----------------------------
	//Event handler
	
}); // document.ready



