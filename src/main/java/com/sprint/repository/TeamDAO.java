package com.sprint.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * The Interface TeamDAO.
 *
 * @param <T> the generic type
 */
public interface TeamDAO<T> {

	/**
	 * Gets the JDBC template.
	 *
	 * @return the JDBC template
	 */
	abstract JdbcTemplate getJdbcTemplate();

	/**
	 * Gets the table name.
	 *
	 * @param teamName the team name
	 * @return the table name
	 * @throws SQLException the SQL exception
	 */
	default String getTableName(String teamName) throws SQLException {
		// Get list of all team related tables
		List<String> tables = getJdbcTemplate().query("show tables", new SingleColumnRowMapper<>(String.class));

		return tables.stream().filter(t -> t.endsWith(teamName)).findFirst().orElse("");
	}

	/**
	 * Gets the sprint list.
	 *
	 * @param tableName the table name
	 * @param rowMapper the row mapper
	 * @return the sprint list
	 */
	default List<T> getCurrentSprint(final String tableName, final RowMapper<T> rowMapper) {
		return getJdbcTemplate().query(
				"SELECT * FROM (SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 1) var1 ORDER BY id ASC;",
				rowMapper);
	}
}
