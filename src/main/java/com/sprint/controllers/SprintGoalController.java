/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	/** The sprint goals. */
	private SprintGoalDAO sprintGoals;

	/**
	 * Instantiates a new sprint goal controller.
	 *
	 * @param dao the dao
	 */
	public SprintGoalController(SprintGoalDAO dao) {
		this.sprintGoals = dao;
	}

	/**
	 * Goals.
	 *
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/goals")
	public String goals(Model model) throws SQLException {
		// Get list of database tables
		List<SprintGoal> teams = sprintGoals.getListOfTables().stream().filter(t -> t.startsWith("team_"))
				.map(tn -> sprintGoals.getSprintById(tn, sprintGoals.getRowCount(tn))).collect(Collectors.toList());
		// Sort list of database tables
		Collections.sort(teams, (a, b) -> a.getTeamName().compareTo(b.getTeamName()));

		// Prepare data for all teams
		prepareTeamData(model, teams);

		return "goals";
	}

	/**
	 * Prepare team data.
	 *
	 * @param model the model
	 * @param teams the teams
	 */
	private void prepareTeamData(Model model, List<SprintGoal> teams) {
		for (int i = 0; i < 4; i++) {
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
			model.addAttribute("sprintLabel", team.getSprintLabel());
			model.addAttribute(teamName, team.getTeamName());
			model.addAttribute(teamGoals, goals);
		}
	}
}
