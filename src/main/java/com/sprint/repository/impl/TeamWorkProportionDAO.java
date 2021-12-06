package com.sprint.repository.impl;

import com.sprint.model.TeamWorkProportion;
import com.sprint.repository.TeamListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TeamWorkProportionDAO implements TeamListDAO<TeamWorkProportion> {

	private final JdbcTemplate jdbcTemplate;

	public TeamWorkProportionDAO(@Autowired DataSource dataSource) {
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
}
