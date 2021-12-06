package com.sprint.repository.impl;

import com.sprint.model.TeamScopeFocus;
import com.sprint.repository.TeamListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TeamScopeFocusDAO implements TeamListDAO<TeamScopeFocus> {

	private final JdbcTemplate jdbcTemplate;

	public TeamScopeFocusDAO(@Autowired DataSource dataSource) {
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
}
