package com.sprint.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * The Interface TeamListDAO.
 *
 * @param <T> the generic type
 */
public interface TeamListDAO<T> {

	/**
	 * Gets the JDBC template. F
	 * 
	 * @return the JDBC template
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
	 * Gets the sprint list.
	 *
	 * @param tableName the table name
	 * @param rowMapper the row mapper
	 * @return the sprint list
	 */
	default List<T> getSprintList(final String tableName, final RowMapper<T> rowMapper) {
		// Get data from last 13 sprints
		List<T> list = getJdbcTemplate().query(
				"SELECT * FROM (SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 13) var1 ORDER BY id ASC;",
				rowMapper);

		// Remove last sprint - it is current, hence not finished sprint - its
		// statistics are not final
		list.remove(list.size() - 1);

		return list;
	}
}
