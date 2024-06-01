/**
 * 
 */
package com.sprint.repository.impl;

import com.sprint.jdbc.SprintRefinementRowMapper;
import com.sprint.model.SprintRefinement;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@DependsOnDatabaseInitialization
@Repository
public class SprintRefinementDAO {

	private static final String REFINEMENT_TABLE_NAME = "sprint";

	private final JdbcTemplate jdbcTemplate;

	public SprintRefinementDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Gets the refinements.
	 *
	 * @return the refinements
	 */
	public List<SprintRefinement> getRefinements() {
		return jdbcTemplate.query("SELECT * FROM (SELECT * FROM " + REFINEMENT_TABLE_NAME
				+ " ORDER BY id DESC LIMIT 4) var1 ORDER BY id ASC;", new SprintRefinementRowMapper());
	}
}
