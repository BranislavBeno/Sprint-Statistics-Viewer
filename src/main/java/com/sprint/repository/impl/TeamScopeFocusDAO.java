package com.sprint.repository.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sprint.model.TeamScopeFocus;
import com.sprint.repository.TeamListDAO;

/**
 * The Class TeamScopeFocusDAO.
 */
@Repository
public class TeamScopeFocusDAO implements TeamListDAO<TeamScopeFocus> {

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
	 * Gets the JDBC template.
	 *
	 * @return the JDBC template
	 */
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
