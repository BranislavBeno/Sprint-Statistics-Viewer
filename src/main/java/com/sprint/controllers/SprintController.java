/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sprint.model.Sprint;
import com.sprint.repository.SprintDAO;

/**
 * @author benito
 *
 */
@Controller
public class SprintController {

	private SprintDAO sprints;

	public SprintController(SprintDAO dao) {
		this.sprints = dao;
	}

	@GetMapping("/goals")
	public String goals() {
		return "goals";
	}

	@GetMapping("/sprintprogress")
	public String sprintsProgress(Model model) throws SQLException {
		// Get list of database tables
		List<Sprint> teams = sprints.getListOfTables().stream().filter(t -> t.startsWith("team_"))
				.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList());

		model.addAttribute("teamList", teams);
		return "sprintprogress";
	}

	@GetMapping("/tables")
	public String tables(Model model) throws SQLException {
		// Get list of database tables
		model.addAttribute("tableList", sprints.getListOfTables());

		return "tables";
	}
}
