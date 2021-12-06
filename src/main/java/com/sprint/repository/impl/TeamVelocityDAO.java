package com.sprint.repository.impl;

import com.sprint.jdbc.TeamVelocityRowMapper;
import com.sprint.model.TeamVelocity;
import com.sprint.repository.TeamListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TeamVelocityDAO implements TeamListDAO<TeamVelocity> {

	private final JdbcTemplate jdbcTemplate;

	public TeamVelocityDAO(@Autowired DataSource dataSource) {
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
	 * Gets the full sprint list.
	 *
	 * @param tableName the table name
	 * @return the full sprint list
	 */
	public List<TeamVelocity> getFullSprintList(final String tableName) {
		// Get data from last 13 sprints
		List<TeamVelocity> list = getJdbcTemplate().query("SELECT * FROM " + tableName + ";",
				new TeamVelocityRowMapper());

		// Remove last sprint - it is current, hence not finished sprint - its
		// statistics are not final
		list.remove(list.size() - 1);

		// Return list of sprints
		return list;
	}
}
