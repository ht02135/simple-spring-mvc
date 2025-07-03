
// Loads all the templates defined with the tag data-template-src.
// Returns a promise that fulfills when all the loads are done.
function loadTemplates() {
    var templateLoads = [];

    $('[data-template-src]').each(function () {
        var templateElement = $(this);
        var templateUrl = templateElement.attr('data-template-src');

        // Load the template into the element and push the promise to do that into the templateLoads array
        templateLoads.push(
            $.get(templateUrl)
            .done(function (data) {
                templateElement.html(data);
            })
        );
    });

    return $.when.apply($, templateLoads);
}

// When all templates are loaded, apply bindings
loadTemplates().done(function () {
	function MyViewModel() {
		var self = this;

		//--------------------------------------
		// Declaration
			
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
