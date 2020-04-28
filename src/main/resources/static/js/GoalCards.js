// Get drop down menu element
let goalCards = document.getElementById("goalCards");

// Add new drop down menu items
for (let i = 0; i < teamGoals.length; i++) {
	let teamName = teamGoals[i].teamName;

	// Create div element for column
	let divCol = document.createElement('div');
	divCol.className = "col mb-4";

	// Create div element for card
	let divCard = document.createElement('div');
	divCard.className = "card border-secondary h-100 w-100 d-inline-block";

	// Create div element for card header
	let divCardHead = document.createElement('div');
	divCardHead.className = "card-header text-white bg-secondary";

	// Set proper header color
	if (teamName.toLowerCase() == "red")
		divCardHead.className = "card-header text-white bg-danger";
	else if (teamName.toLowerCase() == "green")
		divCardHead.className = "card-header text-white bg-success";
	else if (teamName.toLowerCase() == "blue")
		divCardHead.className = "card-header text-white bg-info";
	else if (teamName.toLowerCase() == "orange")
		divCardHead.className = "card-header text-white bg-warning";

	// Create p element for card caption
	let p = document.createElement('p');
	p.className = "h3";
	p.innerHTML = "Team " + teamName;

	// Create div element for card body
	let divCardBody = document.createElement('div');
	divCardBody.className = "h5 card-body";

	// Create ul element for card body
	let ul = document.createElement('ul');

	// Assign goals list
	let goals = teamGoals[i].goals;

	// Run through goals
	for (let j = 0; j < goals.length; j++) {
		// Create li element for unordered list
		let li = document.createElement('li');
		li.innerHTML = goals[j];

		// Add li element for list item to parent element
		ul.appendChild(li);
	}

	// Add p element for card caption to parent element
	divCardHead.appendChild(p);

	// Add div element for card header to parent element
	divCard.appendChild(divCardHead);

	// Add ul element for card body to parent element
	divCardBody.appendChild(ul);

	// Add div element for card body to parent element
	divCard.appendChild(divCardBody);

	// Add div element for card to parent element
	divCol.appendChild(divCard);

	// Add div element for column to parent element
	goalCards.appendChild(divCol);
}
