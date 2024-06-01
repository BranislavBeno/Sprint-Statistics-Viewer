/**
 *
 */
package com.sprint.repository.impl;

import com.sprint.jdbc.SprintProgressRowMapper;
import com.sprint.model.SprintProgress;
import com.sprint.repository.SprintDAO;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@DependsOnDatabaseInitialization
@Repository
public class SprintProgressDAO implements SprintDAO {

	private final JdbcTemplate jdbcTemplate;

	public SprintProgressDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Gets the JDBC template.
	 *
	 * @return the JDBC template
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
	public SprintProgress getSprintById(final String tableName, final int id) {
		final String query = "select * from " + tableName + " where id = ?";
		return jdbcTemplate.queryForObject(query, new SprintProgressRowMapper(), id);
	}

	/**
	 * Gets the sprint by label.
	 *
	 * @param tableName the table name
	 * @param label     the label
	 * @return the sprint by label
	 */
	public SprintProgress getSprintByLabel(final String tableName, final String label) {
		final String query = "select * from " + tableName + " where sprint = ?";
		return jdbcTemplate.queryForObject(query, new SprintProgressRowMapper(), label);
	}
}
