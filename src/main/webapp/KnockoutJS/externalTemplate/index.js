
// Loads all the templates defined with the tag data-template-src.
// Returns a promise that fulfills when all the loads are done.
function loadTemplates() {
    var templateLoads = [];

	/*
	.each( function )
	1>iterate thru matching element
	2>grab 'data-template-src' value and add to templateLoads
	*/
    $('[data-template-src]').each(function () {
        var templateElement = $(this);
        var templateUrl = templateElement.attr('data-template-src');

        // Load the template into the element and push the promise to do that into 
		// the templateLoads array
        templateLoads.push(
            $.get(templateUrl)
            .done(function (data) {
                templateElement.html(data);
            })
        );
    });

	/*
	$.when.apply($, [def1, def2])
	1>is the same as: $.when(def1, def2)
	2>$.when takes any number of parameters and resolves when all of these have resolved.
	*/
    return $.when.apply($, templateLoads);
}

// When all templates are loaded, apply bindings
loadTemplates().done(function () {
	function MyViewModel() {
		var self = this;

		//--------------------------------------
		// Declaration
		
		self.buyer = { name: 'Franklin', credits: 250 };
		self.seller = { name: 'Mario', credits: 5800 };
			
		self.people = ko.observableArray([ 			//foreach binding
			{ name: 'Bert', city: null },
	    	{ name: 'Charles', city: "Boston" },
	    	{ name: 'Denise', city: "Chicago"  }
		]);
		
		//----------------------------------------------
			// Operations
			
		self.addPerson = function() {				//a link click bindinig
			console.log("called addPerson");
			self.people.push({ name: "New at " + new Date() });
		};
		
		self.removePerson = function(person) {		//button click bindinig
			console.log("called removePerson person="+person);
			if (self.people.indexOf(person) >= 0) {
				console.log("remove person().name="+person.name);
				self.people.remove(person);
			}
		};
	}
	
    ko.applyBindings(new MyViewModel(), document.getElementById("htmlDoc"));
});
