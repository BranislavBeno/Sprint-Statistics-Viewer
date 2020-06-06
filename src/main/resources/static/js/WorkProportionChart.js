// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

// Configure chart data
let chartData = {
	labels : labels,
	datasets : [ {
		label : 'Linear trend',
		data : trendSP,
		borderColor : 'rgba(255, 0, 0, 1)',
		backgroundColor : 'rgba(255, 0, 0, 1)',
		pointStyle : 'rect',
		type : 'line',
		fill : false
	}, {
		label : 'Stories',
		data : storiesSP,
		backgroundColor : 'rgba(52, 152, 219, 1)',
		hoverBackgroundColor : 'rgba(0, 0, 255, 1)'
	}, {
		label : 'Bugfixes',
		data : bugfixesSP,
		backgroundColor : 'rgba(231, 76, 60, 1)',
		hoverbackgroundColor : 'rgba(235, 0, 0, 1)'
	} ]
};

// Configure chart design
let chartOptions = {
	responsive : true,
	tooltips : {
		mode : 'index',
		intersect : true
	},
	scales : {
		xAxes : [ {
			display : true,
			stacked : true,
			color : 'rgba(110, 42, 110, 1)',
			position : "bottom",
			scaleLabel : {
				display : true,
				labelString : 'Sprint'
			},
		} ],
		yAxes : [ {
			display : true,
			stacked : true,
			position : "left",
			scaleLabel : {
				display : true,
				labelString : 'Story points'
			},
			ticks : {
				beginAtZero : true
			},
		} ],
	}
}

// Get element representing chart
let ctx = document.getElementById('workProportionChart').getContext('2d');
// Equip chart with data
let myChart = new Chart(ctx, {
	type : 'bar',
	data : chartData,
	options : chartOptions
});