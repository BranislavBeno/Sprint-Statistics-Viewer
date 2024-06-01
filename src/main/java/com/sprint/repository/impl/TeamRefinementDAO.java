/**
 * 
 */
package com.sprint.repository.impl;

import com.sprint.model.TeamRefinement;
import com.sprint.repository.TeamDAO;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@DependsOnDatabaseInitialization
@Repository
public class TeamRefinementDAO implements TeamDAO<TeamRefinement> {

	private final JdbcTemplate jdbcTemplate;

	public TeamRefinementDAO(DataSource dataSource) {
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
