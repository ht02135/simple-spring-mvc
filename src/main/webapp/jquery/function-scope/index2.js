
/*
in term of loading
1>since most of js will be based on jquery or knockout. 
  if we run into $ not defined, then is shit show
2>since most of jquery depend on access dom. if we run
  not able to access dom element, then is shit show
////////////////////////
document.addEventListener a3= HTMLCollection [a]
index2.js:10 globalLambda called
index2.js:12 globalLambda() a= HTMLCollection [a]
index2.js:31 eventListenerLambda called
index2.js:33 eventListenerLambda() a= HTMLCollection [a]
index2.js:17 function() a= HTMLCollection [a]
index2.js:22 (document).ready(function() a2= HTMLCollection [a]
////////////////////////
seems like the way i load js, setup function within ready or dom loaded is 
not necessary....  fine with me...
*/
const globalLambda = () => {
	console.log("globalLambda called");
	let a = document.getElementsByTagName('a');
	console.log("globalLambda() a=", a);
};

$(function() {
	let a = document.getElementsByTagName('a');
	console.log("function() a=", a);
});

$(document).ready(function() {
	let a2 = document.getElementsByTagName('a');
	console.log("(document).ready(function() a2=", a2);
});

document.addEventListener('DOMContentLoaded', () => {
	 let current = 1;
	 let a3 = document.getElementsByTagName('a');
	 console.log("document.addEventListener a3=", a3);
	 
	 const eventListenerLambda = () => {
	 	console.log("eventListenerLambda called");
		let a = document.getElementsByTagName('a');
		console.log("eventListenerLambda() a=", a);
	 };
	 
	 globalLambda();
	 eventListenerLambda();
	 
});