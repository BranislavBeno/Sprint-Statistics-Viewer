// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

// Configure chart data
let chartData = {
	labels : labels,
	datasets : [ {
		data : toDoPercentage,
		data_c : toDoSP,
		label : 'To do',
		backgroundColor : 'rgba(231, 76, 60, 1)',
		hoverbackgroundColor : 'rgba(235, 0, 0, 1)'
	}, {
		data : inProgressPercentage,
		data_c : inProgressSP,
		label : 'In progress',
		backgroundColor : 'rgba(52, 152, 219, 1)',
		hoverBackgroundColor : 'rgba(0, 0, 255, 1)'
	}, {
		data : donePercentage,
		data_c : doneSP,
		label : 'Done',
		backgroundColor : 'rgba(92, 184, 92 , 1)',
		hoverBackgroundColor : 'rgba(50, 205, 50, 1)'
	} ]
}

// Configure chart design
let chartOptions = {
	responsive : true,
	tooltips : {
		mode : 'index',
		intersect : true
	},
	scales : {
		xAxes : [ {
			stacked : true,
			ticks : {
				beginAtZero : true,
				fontSize : 15
			},
			scaleLabel : {
				display : false,
			}
		} ],
		yAxes : [ {
			stacked : true,
			ticks : {
				fontSize : 15,
			}
		} ]
	},
	legend : {
		display : true,
	},
	animation : {
		onComplete : function() {
			let chartInstance = this.chart;
			ctx.fillStyle = "#fff";

			Chart.helpers.each(this.data.datasets.forEach(function(dataset, i) {
				let meta = chartInstance.controller.getDatasetMeta(i);
				Chart.helpers.each(meta.data.forEach(function(bar, index) {
					let data = dataset.data_c[index];
					if (data != 0) {
						let shift = 24;
						if (data < 100)
							shift = 18;
						if (data < 10)
							shift = 12;
						ctx.fillText(data, bar._model.x - shift, bar._model.y);
					}
				}), this)
			}), this);
		}
	},
};

// Get element representing chart
let ctx = document.getElementById('progressChart').getContext('2d');
// Equip chart with data
let myChart = new Chart(ctx, {
	type : 'horizontalBar',
	data : chartData,
	options : chartOptions
});