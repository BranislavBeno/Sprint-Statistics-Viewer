// Set header coloring for goal cards
$(document).ready(function() {
	// Set card header background color to blue when team name is 'Blue'
	$("div.card-header>p:contains(Blue)").parent().addClass("bg-info");
	// Set card header background color to green when team name is 'Green'
	$("div.card-header>p:contains(Green)").parent().addClass("bg-success");
	// Set card header background color to orange when team name is 'Orange'
	$("div.card-header>p:contains(Orange)").parent().addClass("bg-warning");
	// Set card header background color to red when team name is 'Red'
	$("div.card-header>p:contains(Red)").parent().addClass("bg-danger");
});

// Set header coloring for goal cards on AWS
$(document).ready(function() {
	// Set card header background color to blue when team name is 'Plum'
	$("div.card-header>p:contains(Plum)").parent().addClass("bg-info");
	// Set card header background color to green when team name is 'Apple'
	$("div.card-header>p:contains(Apple)").parent().addClass("bg-success");
	// Set card header background color to orange when team name is 'Apricot'
	$("div.card-header>p:contains(Apricot)").parent().addClass("bg-warning");
	// Set card header background color to red when team name is 'Strawberry'
	$("div.card-header>p:contains(Strawberry)").parent().addClass("bg-danger");
});
