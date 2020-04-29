// Get kpi's table element
let kpiTable = document.getElementById("kpiTable");

// Create table header
let tHead = document.createElement('thead');

// Create header row
let tHeadRow = document.createElement('tr');

// Create first header row column
let tHeadRowCol = document.createElement('th');

// Add first header row column element to parent element
tHeadRow.appendChild(tHeadRowCol);

//Create body rows
let bodyRows = [ "Delta number of Story Points start vs. close <10%",
		"At least 80% of planned story points closed",
		"All critical and high priority stories closed out" ];

// Create table body
let tBody = document.createElement('tbody');

// Create first body row
let tBodyRow1 = document.createElement('tr');

//Create column for first body row
let tBodyRowCol1 = document.createElement('td');
tBodyRowCol1.innerHTML = bodyRows[0];

//Add first body row column element to parent element
tBodyRow1.appendChild(tBodyRowCol1);

// Create second body row
let tBodyRow2 = document.createElement('tr');

//Create column for second body row
let tBodyRowCol2 = document.createElement('td');
tBodyRowCol2.innerHTML = bodyRows[1];

//Add second body row column element to parent element
tBodyRow2.appendChild(tBodyRowCol2);

// Create third body row
let tBodyRow3 = document.createElement('tr');

// Create column for third body row
let tBodyRowCol3 = document.createElement('td');
tBodyRowCol3.innerHTML = bodyRows[2];

// Add third body row column element to parent element
tBodyRow3.appendChild(tBodyRowCol3);

// Add kpi's table items
for (let i = 0; i < teamKpis.length; i++) {
	// Get team name
	let teamName = teamKpis[i].teamName;

	// Create header row column
	let tHeadRowCol = document.createElement('th');
	tHeadRowCol.innerHTML = "Team " + teamName;

	// Add first header row column element to parent element
	tHeadRow.appendChild(tHeadRowCol);

	// Create column for first body row
	let tBodyRowCol1 = document.createElement('td');
	tBodyRowCol1.innerHTML = teamKpis[i].deltaStoryPoints;

	// Add body row column element to parent element
	tBodyRow1.appendChild(tBodyRowCol1);

	// Create column for second body row
	let tBodyRowCol2 = document.createElement('td');
	tBodyRowCol2.innerHTML = teamKpis[i].plannedStoryPointsClosed;

	// Add body row column element to parent element
	tBodyRow2.appendChild(tBodyRowCol2);

	// Create column for third body row
	let tBodyRowCol3 = document.createElement('td');
	tBodyRowCol3.innerHTML = teamKpis[i].notClosedHighPriorStoriesCount;

	// Add body row column element to parent element
	tBodyRow3.appendChild(tBodyRowCol3);
}

// Add header row element to parent element
tHead.appendChild(tHeadRow);

// Add table header element to parent element
kpiTable.appendChild(tHead);

// Add first body row element to parent element
tBody.appendChild(tBodyRow1);

// Add second body row element to parent element
tBody.appendChild(tBodyRow2);

// Add third body row element to parent element
tBody.appendChild(tBodyRow3);

// Add table body element to parent element
kpiTable.appendChild(tBody);
