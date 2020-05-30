package com.sprint.repository.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sprint.jdbc.SprintGoalRowMapper;
import com.sprint.model.SprintGoal;
import com.sprint.repository.SprintDAO;

/**
 * The Class SprintGoalDAO.
 */
@Repository
public class SprintGoalDAO implements SprintDAO {

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/**
	 * Sets the data source.
	 *
	 * @param dataSource the new data source
	 */
	@Autowired
	public void setDataSource(final DataSource dataSource) {
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
		return jdbcTemplate.queryForObject(query, new Object[] { id }, new SprintGoalRowMapper());
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
		return jdbcTemplate.queryForObject(query, new Object[] { label }, new SprintGoalRowMapper());
	}
}
