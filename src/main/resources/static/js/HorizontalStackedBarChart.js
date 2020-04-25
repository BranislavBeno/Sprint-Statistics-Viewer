// Default font color
Chart.defaults.global.defaultFontColor = '#bebebe';

// Configure look and feel of chart
var barOptions_stacked = {
	tooltips : {
		enabled : false,
	},
	scales : {
		xAxes : [ {
			ticks : {
				beginAtZero : true,
				fontSize : 15
			},
			scaleLabel : {
				display : false,
			},
			gridLines : {},
			ticks : {
				fontSize : 15,
			},
			stacked : true
		} ],
		yAxes : [ {
			gridLines : {},
			ticks : {
				fontSize : 15,
			},
			stacked : true,
		} ]
	},
	legend : {
		display : true,
	},

	animation : {
		onComplete : function() {
			var chartInstance = this.chart;
			var ctx = chartInstance.ctx;
			ctx.fillStyle = "#fff";

			Chart.helpers.each(this.data.datasets.forEach(function(dataset, i) {
				var meta = chartInstance.controller.getDatasetMeta(i);
				Chart.helpers.each(meta.data.forEach(function(bar, index) {
					data = dataset.data_c[index];
					if (data != 0) {
						shift = 24;
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
var ctx = document.getElementById("myChart").getContext('2d');
// Equip chart with data
var myChart = new Chart(ctx, {
	type : 'horizontalBar',
	data : {
		labels : labels,

		datasets : [ {
			data : toDoPercentage,
			data_c : toDoSP,
			label : "To do",
			backgroundColor : "rgba(235,0,0,1)",
			hoverBackgroundColor : "rgba(255,0,0,1)"
		}, {
			data : inProgressPercentage,
			data_c : inProgressSP,
			label : "In progress",
			backgroundColor : "rgba(80,145,205,1)",
			hoverBackgroundColor : "rgba(100,185,255,1)"
		}, {
			data : donePercentage,
			data_c : doneSP,
			label : "Done",
			backgroundColor : "rgba(50,205,50,1)",
			hoverBackgroundColor : "rgba(0,255,0,1)"
		} ]
	},
	options : barOptions_stacked,
});