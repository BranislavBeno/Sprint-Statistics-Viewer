package com.sprint.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * The Interface SprintDAO.
 */
public interface SprintDAO {

	/**
	 * Gets the  template.
	 *
	 * @return the  template
	 */
	abstract JdbcTemplate getJdbcTemplate();

	/**
	 * Gets the list of tables.
	 *
	 * @return the list of tables
	 * @throws SQLException the SQL exception
	 */
	default List<String> getListOfTables() throws SQLException {
		return getJdbcTemplate().query("show tables", new SingleColumnRowMapper<>(String.class));
	}

	/**
	 * Gets the row count.
	 *
	 * @param tableName the table name
	 * @return the row count
	 */
	default int getRowCount(final String tableName) {
		return getJdbcTemplate().queryForObject("select count(*) from " + tableName, Integer.class);
	}

	/**
	 * Gets the sprint list.
	 *
	 * @param tableName the table name
	 * @return the sprint list
	 * @throws SQLException the SQL exception
	 */
	default List<String> getSprintList(final String tableName) throws SQLException {
		return getJdbcTemplate().query("select sprint from " + tableName, new SingleColumnRowMapper<>(String.class));
	}
}
