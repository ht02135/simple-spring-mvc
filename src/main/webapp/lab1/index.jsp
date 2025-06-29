
<%-- dont use this way of comment --%>
<!-- 
1>use this way of comment which is also used in xml 
2>run thru https://www.sitepoint.com/beginners-guide-to-knockoutjs-part-1/
-->

<script type='text/javascript' src='jquery-1.7.1.min.js'></script>
<script type="text/javascript" src="../libs/jquery.tmpl.min.js"></script>
<script type='text/javascript' src='knockout-2.0.0.js'></script>

<!-- our model.js -->
<script src="model.js" type="text/javascript"></script>

<!--
1>you can bind data to a DOM element by including a data-bind attribute in 
the markup which specifies the data-binding to perform
2> In the following example we add text data-bind attribute to span element 
like this:

syntax: data-bind="bindingName: bindingValue"
bindingName=text bindingValue=dayOfWeek
bindingName=text bindingValue=activity
-->
<p>The day of the week is <span data-bind="text: dayOfWeek"></span>. It's time for <span data-bind="text: activity"></span></p>

<p>Day: <input data-bind="value: day" /></p>
<p>Month: <input data-bind="value: month" /></p>
<p>Year: <input data-bind="value: year" /></p>
<p>The current date is <span data-bind="text: fullDate"></span></p>



