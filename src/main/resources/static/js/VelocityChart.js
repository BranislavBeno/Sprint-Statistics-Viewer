// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

// Configure chart data
let chartData = {
	labels : labels,
	datasets : [ {
		label : 'Velocity',
		data : velocitySP,
		borderColor : 'rgb(255, 0, 0)',
		backgroundColor : 'rgba(255, 0, 0)',
		pointStyle : 'rect',
		type : 'line',
		fill : false,
		lineTension : 0
	}, {
		label : 'Velocity/#Dev',
		data : devVelocitySP,
		borderColor : 'rgb(244, 236, 44)',
		backgroundColor : 'rgb(244, 236, 44)',
		pointStyle : 'rect',
		type : 'line',
		fill : false,
		lineTension : 0
	}, {
		label : 'On sprint begin planned',
		data : onBeginSP,
		backgroundColor : 'rgba(92, 184, 92, 1)',
		hoverBackgroundColor : 'rgba(0 ,128, 0, 1)'
	}, {
		label : 'On sprint end planned',
		data : onEndSP,
		backgroundColor : 'rgba(240,173,78,1)',
		hoverBackgroundColor : 'rgba(255, 165, 0, 1)'
	}, {
		label : 'Finished',
		data : finishedSP,
		backgroundColor : 'rgba(2, 117, 216, 1)',
		hoverBackgroundColor : 'rgba(0, 0, 255, 1)'
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
			color : 'rgba(110, 42, 110, 1)',
			position : "bottom",
			scaleLabel : {
				display : true,
				labelString : 'Sprint'
			},
		} ],
		yAxes : [ {
			display : true,
			position : "left",
			scaleLabel : {
				display : true,
				labelString : 'Story points'
			},
			ticks : {
				beginAtZero : true
			}
		} ],
	}
}

// Get element representing chart
let ctx = document.getElementById('velocityChart').getContext('2d');
// Equip chart with data
let myChart = new Chart(ctx, {
	type : 'bar',
	data : chartData,
	options : chartOptions
});