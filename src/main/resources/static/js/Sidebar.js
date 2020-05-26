$(document).ready(function() {
	$('#sidebarCollapse').on('click', function() {
		// Switch active state
		$('#sidebar').toggleClass('active');
		// If side bar in active state, change element inner html
		if ($("#sidebar").hasClass("active")) {
			// Side bar collapse button
			$("#sidebarCollapse").html("<i class=\"fas fa-angle-right\"></i>");
			// Menu item - Home
			$("#homeMenuItem").html("<i class=\"fas fa-home\"></i>");
			// Menu item - Progress
			$("#progressMenuItem").html("<i class=\"fas fa-chart-line\"></i>");
			// Menu item - KPI's
			$("#kpiMenuItem").html("<i class=\"fas fa-th\"></i>");
			// Menu item - Refinement
			$("#refinementMenuItem").html("<i class=\"far fa-chart-bar\"></i>");
			// Menu item - Goals
			$("#goalsMenuItem").html("<i class=\"fas fa-list-ul\"></i>");
		} else {
			// Side bar collapse button
			$("#sidebarCollapse").html("<i class=\"fas fa-angle-left\"></i>");
			// Menu item - Home
			$("#homeMenuItem").html("<i class=\"fas fa-home\"></i> Home");
			// Menu item - Progress
			$("#progressMenuItem").html("<i class=\"fas fa-chart-line\"></i> Progress");
			// Menu item - KPI's
			$("#kpiMenuItem").html("<i class=\"fas fa-th\"></i> KPI's");
			// Menu item - Refinement
			$("#refinementMenuItem").html("<i class=\"far fa-chart-bar\"></i> Refinement");
			// Menu item - Goals
			$("#goalsMenuItem").html("<i class=\"fas fa-list-ul\"></i> Goals");
		}
	});
});
