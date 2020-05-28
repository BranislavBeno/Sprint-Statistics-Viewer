// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

let labels = [ 'Sprint 59', 'Sprint 60', 'Sprint 61', 'Sprint 62' ];
let expected = [ 220, 220, 220, 220 ];
let basic = [ 257, 161, 8, 3 ];
let advanced = [ 35, 18, 61, 8 ];
let commercial = [ 0, 0, 0, 0 ];
let future = [ 0, 0, 0, 0 ];

let barChartData = {
	labels : labels,
	datasets : [ {
		label : 'Expected',
		data : expected,
		borderColor : 'rgba(235, 0, 0, 1)',
		type : 'line',
		fill : false
	}, {
		label : 'Basic',
		data : basic,
		backgroundColor : 'rgba(52, 152, 219, 1)',
		hoverBackgroundColor : 'rgba(0, 0, 255, 1)'
	}, {
		label : 'Advanced',
		data : advanced,
		backgroundColor : 'rgba(255, 159, 64, 1)',
		hoverBackgroundColor : 'rgba(251 ,219, 72, 1)'
	}, {
		label : 'Commercial',
		data : commercial,
		backgroundColor : 'rgba(92, 184, 92 , 1)',
		hoverBackgroundColor : 'rgba(50, 205, 50, 1)'
	}, {
		label : 'Future',
		data : future,
		backgroundColor : 'rgba(128,0,128,1)',
		hoverBackgroundColor : 'rgba(50, 205, 50, 1)'
	} ]
};

window.onload = function() {
	var ctx = document.getElementById("refinementChart").getContext("2d");
	window.myBar = new Chart(ctx, {
		type : 'bar',
		data : barChartData,
		options : {
			responsive : true,
			tooltips : {
				mode : 'index',
				intersect : true
			},
			scales : {
				xAxes : [ {
					stacked : true
				} ],
				yAxes : [ {
					type : "linear", // only linear but allow scale type
					// registration. This allows extensions
					// to exist solely for log scale for
					// instance
					stacked : true,
					display : true,
					position : "left",
					ticks : {
						beginAtZero : true,
						suggestedMin : 0,
						suggestedMax : 10,
						min : 0
					}
				}, {
					type : "linear", // only linear but allow scale type
					// registration. This allows extensions
					// to exist solely for log scale for
					// instance
					display : false,
					ticks : {
						beginAtZero : true,
						suggestedMin : 0,
						suggestedMax : 10,
						min : 0
					}
				} ],
			}
		}
	});
};