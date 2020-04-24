function showName(name) {
	alert("Here's the name: " + name);
}

var barOptions_stacked = {
	tooltips : {
		enabled : false,
	},
	scales : {
		xAxes : [ {
			ticks : {
				beginAtZero : true,
				fontSize : 11
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
			ctx.font = "15px";
			ctx.fillStyle = "#fff";

			Chart.helpers.each(this.data.datasets.forEach(function(dataset, i) {
				var meta = chartInstance.controller.getDatasetMeta(i);
				Chart.helpers.each(meta.data.forEach(function(bar, index) {
					data = dataset.data_c[index];
					if (data != 0) {
						ctx.fillText(data, bar._model.x - 30, bar._model.y);
					}
				}), this)
			}), this);
		}
	},
};

Chart.defaults.global.defaultFontColor = '#bebebe';

var ctx = document.getElementById("myChart").getContext('2d');
var myChart = new Chart(ctx, {
	type : 'horizontalBar',
	data : {
		labels : [ "Red", "Green", "Black", "Blue", "Total", "Time elapsed" ],

		datasets : [ {
			data : [ 88, 62, 93, 92, 79, 92 ],
			data_c : [ 35, 38, 68, 35, 176, 11 ],
			label : "To do",
			backgroundColor : "rgba(235,0,0,1)",
			hoverBackgroundColor : "rgba(255,0,0,1)"
		}, {
			data : [ 12, 38, 7, 8, 21 ],
			data_c : [ 5, 23, 5, 3, 36 ],
			label : "In progress",
			backgroundColor : "rgba(80,145,205,1)",
			hoverBackgroundColor : "rgba(100,185,255,1)"
		}, {
			data : [ 0, 0, 0, 0, 0, 8 ],
			data_c : [ 0, 0, 0, 0, 0, 1 ],
			label : "Done",
			backgroundColor : "rgba(50,205,50,1)",
			hoverBackgroundColor : "rgba(0,255,0,1)"
		} ]
	},
	options : barOptions_stacked,
});