// Configure chart data
let pieChartData = {
	labels : [ "Stories", "Bugfixes" ],
	datasets : [ {
		borderColor : [ "#3e95cd", "#c45850" ],
		backgroundColor : [ "#3e95cd", "#c45850" ],
		hoverBorderColor : [ '#0000ff', '#eb0000' ],
		hoverBackgroundColor : [ '#0000ff', '#eb0000' ],
		data : percentageSP
	} ]
};

// Configure chart design
let pieChartOptions = {
	responsive : true,
	maintainAspectRatio : false,
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