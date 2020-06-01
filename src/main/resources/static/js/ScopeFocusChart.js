// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

// Configure chart data
let chartData = {
	labels : labels,
	datasets : [ {
		label : 'Basic',
		data : basicSP,
		backgroundColor : 'rgba(2, 117, 216, 1)',
		hoverBackgroundColor : 'rgba(0, 0, 255, 1)'
	}, {
		label : 'Advanced',
		data : advancedSP,
		backgroundColor : 'rgba(240,173,78,1)',
		hoverBackgroundColor : 'rgba(255, 165, 0, 1)'
	}, {
		label : 'Commercial',
		data : commercialSP,
		backgroundColor : 'rgba(92, 184, 92, 1)',
		hoverBackgroundColor : 'rgba(0 ,128, 0, 1)'
	}, {
		label : 'Future',
		data : futureSP,
		backgroundColor : 'rgba(164, 63, 164 , 1)',
		hoverBackgroundColor : 'rgba(110, 42, 110, 1)'
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
		/*
		 * gridLines : { zeroLineColor : "#bebebe" }
		 */
		} ],
		yAxes : [ {
			display : true,
			stacked : true,
			position : "left",
			scaleLabel : {
				display : true,
				labelString : 'Story points'
			},
		/*
		 * gridLines : { zeroLineColor : "#bebebe" }
		 */
		} ],
	}
}

// Get element representing chart
let ctx = document.getElementById('scopeFocusChart').getContext('2d');
// Equip chart with data
let myChart = new Chart(ctx, {
	type : 'bar',
	data : chartData,
	options : chartOptions
});