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
var ctx = document.getElementById("refinementChart").getContext('2d');
// Equip chart with data
var mixedChart = new Chart(ctx, {
    type: 'bar',
    data: {
        datasets: [{
            label: 'Bar Dataset',
            data: [10, 20, 30, 40]
        }, {
            label: 'Line Dataset',
            data: [50, 50, 50, 50],

            // Changes this dataset to become a line
            type: 'line'
        }],
        labels: ['January', 'February', 'March', 'April']
    },
	options : barOptions_stacked,
});