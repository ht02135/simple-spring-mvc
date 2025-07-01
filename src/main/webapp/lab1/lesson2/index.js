
/*
Class to represent a row in the seat reservations grid
SeatReservation, a simple JavaScript class constructor that stores a passenger 
name with a meal selection
*/
function SeatReservation(name, initialMeal) {
    var self = this;
    self.name = name;
    self.meal = ko.observable(initialMeal);  //meal property is an observable

    self.formattedPrice = ko.computed(function() {
		/*
		because the meal property is an observable, it's important to invoke meal() as 
		a function (to obtain its current value) before attempting to read sub-properties
		*/
        var price = self.meal().price;
        return price ? "$" + price.toFixed(2) : "None";        
    });    
}

/*
Overall viewmodel for this screen, along with initial state
ReservationsViewModel, a viewmodel class that holds:
1>availableMeals, a JavaScript object providing meal data
2>seats, an array holding an initial collection of SeatReservation instances. 
Note that it's a ko.observableArray
*/
// Overall viewmodel for this screen, along with initial state
function ReservationsViewModel() {
    var self = this;

    // Non-editable catalog data - would come from the server
    self.availableMeals = [
        { mealName: "Standard (sandwich)", price: 0 },
        { mealName: "Premium (lobster)", price: 34.95 },
        { mealName: "Ultimate (whole zebra)", price: 290 }
    ];    

    // Editable data
    self.seats = ko.observableArray([ //seats is an observable array
        new SeatReservation("Steve", self.availableMeals[0]),
        new SeatReservation("Bert", self.availableMeals[0])
    ]);

    // Computed data
    self.totalSurcharge = ko.computed(function() {
       var total = 0;
       for (var i = 0; i < self.seats().length; i++)
           total += self.seats()[i].meal().price;
       return total;
    });    

	//-----------------------------------------------------------------
    // Operations
	/*
	implement that addSeat function, making it push an extra entry into the 
	seats array
	*/
    self.addSeat = function() {
		/*
		seats is an observable array, so adding or removing items will trigger 
		UI updates automatically
		*/
        self.seats.push(new SeatReservation("", self.availableMeals[0]));
    }
	
    self.removeSeat = function(seat) { self.seats.remove(seat) }
}

ko.applyBindings(new ReservationsViewModel());