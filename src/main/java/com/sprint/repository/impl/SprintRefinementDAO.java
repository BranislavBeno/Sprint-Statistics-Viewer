/**
 * 
 */
package com.sprint.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sprint.jdbc.SprintRefinementRowMapper;
import com.sprint.model.SprintRefinement;

/**
 * The Class SprintProgressDAO.
 */
@Repository
public class SprintRefinementDAO {

	/** The Constant REFINEMENT_TABLE_NAME. */
	private static final String REFINEMENT_TABLE_NAME = "sprint";

	/** The JDBC template. */
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
	 * Gets the refinements.
	 *
	 * @return the refinements
	 */
	public List<SprintRefinement> getRefinements() {
		return jdbcTemplate.query("SELECT * FROM (SELECT * FROM " + REFINEMENT_TABLE_NAME
				+ " ORDER BY id DESC LIMIT 4) var1 ORDER BY id ASC;", new SprintRefinementRowMapper());
	}
}
