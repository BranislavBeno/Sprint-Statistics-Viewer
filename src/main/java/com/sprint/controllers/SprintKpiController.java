package com.sprint.controllers;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.model.SprintKpi;
import com.sprint.repository.impl.SprintKpiDAO;

/**
 * The Class SprintKpiController.
 */
@Controller
public class SprintKpiController {

	/** The log. */
	private static Log log = LogFactory.getLog(SprintKpiController.class);

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The KPI's. */
	private SprintKpiDAO kpis;

	/**
	 * Instantiates a new sprint kpi controller.
	 *
	 * @param kpis the kpis
	 */
	@Autowired
	public SprintKpiController(SprintKpiDAO kpis) {
		this.kpis = kpis;
	}

	/**
	 * Find sprint by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	private List<SprintKpi> findSprintByLabel(final String label) {
		// Initialize list of teams
		List<SprintKpi> teams = null;
		try {
			teams = kpis.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
					.map(tn -> kpis.getSprintByLabel(tn, label)).collect(Collectors.toList());
		} catch (Exception e) {
			try {
				teams = kpis.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
						.map(tn -> kpis.getSprintById(tn, kpis.getRowCount(tn))).collect(Collectors.toList());
			} catch (SQLException e1) {
				log.warn("No sprint kpi data found.");
			}
		}

		return teams;
	}

	/**
	 * Collect sprints.
	 *
	 * @return the sets the
	 * @throws SQLException the SQL exception
	 */
	private Set<String> collectSprints() throws SQLException {
		// Initialize empty set of sprints
		Set<String> sprintSet = new TreeSet<>();

		// Get list of database tables related with team related sprint data
		List<String> teams = kpis.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
				.collect(Collectors.toList());

		// Get set of sprint labels
		if (!teams.isEmpty()) {
			// Initialize counter
			int i = 0;
			// Get first set
			sprintSet = new TreeSet<>(kpis.getSprintList(teams.get(i)));

			// Create intersection of sprint labels over all team related sprint data
			for (i = 1; i < teams.size(); i++) {
				Set<String> newSet = new TreeSet<>(kpis.getSprintList(teams.get(i)));
				sprintSet.retainAll(newSet);
			}
		}

		return sprintSet;
	}

	/**
	 * Kpi.
	 *
	 * @param label the label
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/kpi")
	public String kpi(@RequestParam("sprint") String label, Model model) throws SQLException {
		// Get list of sprint related team data
		List<SprintKpi> teams = findSprintByLabel(label);

		// In case that method parameter 'sprintLabel' is not compliant with data in
		// database, last sprint related data record in database is chosen
		// In that case is sprint label updated
		// Subsequently time stamp about last data update is set
		String updated = "unknown";
		if (teams != null) {
			// Set sprint label
			label = teams.stream().findFirst().orElseThrow().getSprintLabel();
			// Set time stamp about last data update
			updated = teams.stream().findFirst().orElseThrow().getUpdated()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		}

		// Sort list of database tables
		Collections.sort(teams, (a, b) -> a.getTeamName().compareTo(b.getTeamName()));

		// Find sprint labels for all available team related sprint data
		Set<String> sprintSet = collectSprints();

		// Add sprint label
		model.addAttribute("mSprintLabel", label);

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add list of sprints
		model.addAttribute("mSprintList", sprintSet);

		// Add list of team related sprint kpi's
		model.addAttribute("mTeamKpis", teams);

		return "kpi";
	}
}
