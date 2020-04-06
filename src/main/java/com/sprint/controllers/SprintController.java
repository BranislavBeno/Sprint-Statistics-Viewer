/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.util.StringJoiner;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.repository.SprintDAO;

/**
 * @author benito
 *
 */
@RestController
public class SprintController {

	private SprintDAO sprints;

	public SprintController(SprintDAO dao) {
		this.sprints = dao;
	}

	@RequestMapping("/sprintscount")
	public String sprintsCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sprint count = ").append(sprints.getCountOfEmployees());

		return sb.toString();
	}

	@RequestMapping("/sprintprogress")
	public String sprintsProgress() {
		StringBuilder sb = new StringBuilder();

		sb.append("Sprint = ").append(sprints.getSprintById(sprints.getCountOfEmployees()).toString());

		return sb.toString();
	}

	@RequestMapping("/tables")
	public String tables() throws SQLException {
		StringJoiner sj = new StringJoiner(", ", "[", "]");

		sprints.getListOfTables().forEach(sj::add);

		return new StringBuilder("Tables = ").append(sj.toString()).toString();
	}
}
