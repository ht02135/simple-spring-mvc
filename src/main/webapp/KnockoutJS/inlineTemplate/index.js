function MyViewModel() {
var self = this;

   self.buyer = { name: 'Franklin', credits: 250 };
   self.seller = { name: 'Mario', credits: 5800 };
 
	self.people = [
		{ name: 'Franklin', credits: 250 },
		{ name: 'Mario', credits: 5800 }
	]
}

ko.applyBindings(new MyViewModel());