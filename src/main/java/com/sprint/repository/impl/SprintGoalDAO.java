package com.sprint.repository.impl;

import com.sprint.jdbc.SprintGoalRowMapper;
import com.sprint.model.SprintGoal;
import com.sprint.repository.SprintDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * The Class SprintGoalDAO.
 */
@Repository
public class SprintGoalDAO implements SprintDAO {

	private final JdbcTemplate jdbcTemplate;

	public SprintGoalDAO(@Autowired DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * Gets the sprint by id.
	 *
	 * @param tableName the table name
	 * @param id        the id
	 * @return the sprint by id
	 */
	public SprintGoal getSprintById(final String tableName, final int id) {
		final String query = "select * from " + tableName + " where id = ?";
		return jdbcTemplate.queryForObject(query, new SprintGoalRowMapper(), id);
	}

	/**
	 * Gets the sprint by label.
	 *
	 * @param tableName the table name
	 * @param label     the label
	 * @return the sprint by label
	 */
	public SprintGoal getSprintByLabel(final String tableName, final String label) {
		final String query = "select * from " + tableName + " where sprint = ?";
		return jdbcTemplate.queryForObject(query, new SprintGoalRowMapper(), label);
	}
}
