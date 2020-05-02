'use strict';

// This function creates table header row inclusive with filling first column
function createTableHeader() {
	// Create table header
	let head = document.createElement('thead');

	// Create header row
	let headRow = document.createElement('tr');

	// Create first header row column
	let headRowCol1 = document.createElement('th');
	headRowCol1.innerHTML = "#";
	// Add first header row column element to parent element
	headRow.appendChild(headRowCol1);

	// Iterate through team related data
	for (let i = 0; i < teamKpis.length; i++) {
		// Create header row column
		let headRowCol = document.createElement('th');
		headRowCol.innerHTML = "Team " + teamKpis[i].teamName;
		// Add first header row column element to parent element
		headRow.appendChild(headRowCol);
	}

	// Add header row element to parent element
	head.appendChild(headRow);

	// Return header element
	return head;
}

// This function creates table body row inclusive with filling first column
function createTableBodyRow(content) {
	// Create body row element
	let bodyRow = document.createElement('tr');

	// Create column for body row element
	let bodyRowCol = document.createElement('td');
	bodyRowCol.innerHTML = content;
	// Add body row column element to parent element
	bodyRow.appendChild(bodyRowCol);

	// Return row element with first column
	return bodyRow;
}

// Get kpi's table element
let kpiTable = document.getElementById("kpiTable");

// Add table header element to parent element
let tHead = createTableHeader();

// Create body rows
const bodyRows = [ "Delta number of Story Points start vs. close <10%",
		"At least 80% of planned story points closed",
		"All critical and high priority stories closed out" ];

// Create table body
let tBody = document.createElement('tbody');

// Create first body row
const tBodyRow1 = createTableBodyRow(bodyRows[0]);
// Create second body row
const tBodyRow2 = createTableBodyRow(bodyRows[1]);
// Create third body row
const tBodyRow3 = createTableBodyRow(bodyRows[2]);

// Add kpi's table items
for (let i = 0; i < teamKpis.length; i++) {
	// Create column for first body row
	let deltaStoryPoints = teamKpis[i].deltaStoryPoints * 100;
	let tBodyRowCol1 = document.createElement('td');
	tBodyRowCol1.innerHTML = deltaStoryPoints.toFixed(2) + "%";
	tBodyRowCol1.className = "bg-danger";
	if (deltaStoryPoints < 10) {
		tBodyRowCol1.className = "bg-success";
	}
	// Add body row column element to parent element
	tBodyRow1.appendChild(tBodyRowCol1);

	// Create column for second body row
	let plannedStoryPointsClosed = teamKpis[i].plannedStoryPointsClosed * 100;
	let tBodyRowCol2 = document.createElement('td');
	tBodyRowCol2.innerHTML = plannedStoryPointsClosed.toFixed(2) + "%";
	tBodyRowCol2.className = "bg-danger";
	if (plannedStoryPointsClosed >= 80) {
		tBodyRowCol2.className = "bg-success";
	}
	// Add body row column element to parent element
	tBodyRow2.appendChild(tBodyRowCol2);

	// Create column for third body row
	let notClosedHighPriorStoriesCount = teamKpis[i].notClosedHighPriorStoriesCount;
	let tBodyRowCol3 = document.createElement('td');
	tBodyRowCol3.innerHTML = notClosedHighPriorStoriesCount;
	tBodyRowCol3.className = "bg-danger";
	if (notClosedHighPriorStoriesCount == 0) {
		tBodyRowCol3.className = "bg-success";
	}
	// Add body row column element to parent element
	tBodyRow3.appendChild(tBodyRowCol3);
}

// Add first body row element to parent element
tBody.appendChild(tBodyRow1);
// Add second body row element to parent element
tBody.appendChild(tBodyRow2);
// Add third body row element to parent element
tBody.appendChild(tBodyRow3);

// Add table head element to parent element
kpiTable.appendChild(tHead);
// Add table body element to parent element
kpiTable.appendChild(tBody);
