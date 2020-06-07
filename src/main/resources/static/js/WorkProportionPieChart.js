// Configure chart data
let pieChartData = {
	labels : [ "Stories", "Bugfixes" ],
	datasets : [ {
		backgroundColor : [ "#3e95cd", "#c45850" ],
		data : [ 2478, 5267 ]
	} ]
};

// Configure chart design
let pieChartOptions = {
	title : {
		display : true,
	}
}

// Get element representing chart
let pieCtx = document.getElementById('workProportionPieChart').getContext('2d');

// Equip chart with data
let myPieChart = new Chart(pieCtx, {
	type : 'pie',
	data : pieChartData,
	options : pieChartOptions
});