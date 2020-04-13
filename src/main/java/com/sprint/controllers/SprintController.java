/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.StringJoiner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping("/sprintscount")
	public String sprintsCount() throws SQLException {
		StringBuilder sb = new StringBuilder("Sprint count for ");
		sprints.getListOfTables()
				.forEach(table -> sb.append(table).append(" = ").append(sprints.getRowCount(table)).append(", "));

		return sb.toString();
	}

	@GetMapping("/sprintprogress")
	public String sprintsProgress() throws SQLException {
		StringBuilder sb = new StringBuilder("Sprintprogress = ");

		sprints.getListOfTables().stream().filter(t -> t.startsWith("team_")).forEach(
				table -> sb.append(sprints.getSprintById(table, sprints.getRowCount(table)).toString()).append("\n"));

		return sb.toString();
	}

	@GetMapping("/tables")
	public String tables() throws SQLException {
		StringJoiner sj = new StringJoiner(", ", "[", "]");

		sprints.getListOfTables().forEach(sj::add);

		return new StringBuilder("Tables = ").append(sj.toString()).toString();
	}
}
