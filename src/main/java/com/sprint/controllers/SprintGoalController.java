/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sprint.model.SprintGoal;
import com.sprint.repository.SprintGoalDAO;

/**
 * @author benito
 *
 */
@Controller
public class SprintGoalController {

	private SprintGoalDAO sprintGoals;

	public SprintGoalController(SprintGoalDAO dao) {
		this.sprintGoals = dao;
	}

	@GetMapping("/goals")
	public String goals(Model model) throws SQLException {
		// Get list of database tables
		List<SprintGoal> teams = sprintGoals.getListOfTables().stream().filter(t -> t.startsWith("team_"))
				.map(tn -> sprintGoals.getSprintById(tn, sprintGoals.getRowCount(tn))).collect(Collectors.toList());
		// Sort list of database tables
		Collections.sort(teams, (a, b) -> a.getTeamName().compareTo(b.getTeamName()));

		SprintGoal teamOne = Optional.ofNullable(teams.get(0)).orElse(new SprintGoal());

		model.addAttribute("sprintLabel", teamOne.getSprintLabel());
		model.addAttribute("teamOneName", teamOne.getTeamName());
		// model.addAttribute("teamOneGoals", teamOne.getSprintGoals());

		return "goals";
	}
}
