/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.model.SprintGoal;
import com.sprint.repository.SprintGoalDAO;

/**
 * The Class SprintGoalController.
 *
 * @author benito
 */
@Controller
public class SprintGoalController {

	/** The log. */
	private static Log log = LogFactory.getLog(SprintGoalController.class);

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The sprint goals. */
	private SprintGoalDAO sprints;

	/**
	 * Instantiates a new sprint goal controller.
	 *
	 * @param dao the dao
	 */
	public SprintGoalController(SprintGoalDAO dao) {
		this.sprints = dao;
	}

	/**
	 * Find sprint by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	private List<SprintGoal> findSprintByLabel(final String label) {
		// Initialize list of teams
		List<SprintGoal> teams = null;
		try {
			teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
					.map(tn -> sprints.getSprintByLabel(tn, label)).collect(Collectors.toList());
		} catch (Exception e) {
			try {
				teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
						.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList());
			} catch (SQLException e1) {
				log.warn("No sprint data found.");
			}
		}

		return teams;
	}

	/**
	 * Prepare team data.
	 *
	 * @param model the model
	 * @param teams the teams
	 */
	private void prepareTeamData(Model model, List<SprintGoal> teams) {
		for (int i = 0; i < teams.size(); i++) {
			// Extract team
			SprintGoal team = Optional.ofNullable(teams.get(i)).orElse(new SprintGoal());

			// Initialize array for goals
			String[] goals = new String[] {};

			// Initialize jackson mapper for json string
			ObjectMapper mapper = new ObjectMapper();
			// Fill array goals
			try {
				goals = mapper.readValue(team.getSprintGoals(), String[].class);
			} catch (JsonProcessingException e) {
				log.warn("Conversion from json to list of goals for team " + team.getTeamName() + " failed.");
			}

			// Prepare attribute names
			String teamName = "teamName" + i;
			String teamGoals = "teamGoals" + i;

			// Add model attributes for thymeleaf template
			model.addAttribute(teamName, team.getTeamName());
			model.addAttribute(teamGoals, goals);
		}
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
		List<String> teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
				.collect(Collectors.toList());

		// Get set of sprint labels
		if (!teams.isEmpty()) {
			// Initialize counter
			int i = 0;
			// Get first set
			sprintSet = new TreeSet<>(sprints.getSprintList(teams.get(i)));

			// Create intersection of sprint labels over all team related sprint data
			for (i = 1; i < teams.size(); i++) {
				Set<String> newSet = new TreeSet<>(sprints.getSprintList(teams.get(i)));
				sprintSet.retainAll(newSet);
			}
		}

		return sprintSet;
	}

	/**
	 * Goals.
	 *
	 * @param label the label
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/goals")
	public String goals(@RequestParam("sprint") String label, Model model) throws SQLException {
		// Get list of sprint related team data
		List<SprintGoal> teams = findSprintByLabel(label);

		// In case that method parameter 'sprintLabel' is not compliant with data in
		// database,
		// last sprint related data record in database is chosen
		// In that case is sprint label updated
		if (teams != null)
			label = teams.stream().findFirst().orElseThrow().getSprintLabel();

		// Sort list of database tables
		Collections.sort(teams, (a, b) -> a.getTeamName().compareTo(b.getTeamName()));

		// Prepare data for all teams
		prepareTeamData(model, teams);

		// Find sprint labels for all available team related sprint data
		Set<String> sprintSet = collectSprints();

		// Add sprint label
		model.addAttribute("mSprintLabel", label);

		// Add list of sprints
		model.addAttribute("mSprintList", sprintSet);

		return "goals";
	}
}
