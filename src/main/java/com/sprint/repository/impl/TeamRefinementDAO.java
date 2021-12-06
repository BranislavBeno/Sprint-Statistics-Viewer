/**
 * 
 */
package com.sprint.repository.impl;

import com.sprint.model.TeamRefinement;
import com.sprint.repository.TeamDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TeamRefinementDAO implements TeamDAO<TeamRefinement> {

	private final JdbcTemplate jdbcTemplate;

	public TeamRefinementDAO(@Autowired DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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
