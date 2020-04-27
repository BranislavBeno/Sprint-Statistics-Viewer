/**
 * 
 */
package com.sprint.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sprint.jdbc.SprintProgressRowMapper;
import com.sprint.model.SprintProgress;

@Repository
public class SprintProgressDAO {

	/** The jdbc template. */
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
	 * Gets the list of tables.
	 *
	 * @return the list of tables
	 * @throws SQLException the SQL exception
	 */
	public List<String> getListOfTables() throws SQLException {
		List<String> list = new ArrayList<>();

		try (Statement stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("show tables");) {
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		}
		return list;
	}

	/**
	 * Gets the row count.
	 *
	 * @param tableName the table name
	 * @return the row count
	 */
	public int getRowCount(final String tableName) {
		return jdbcTemplate.queryForObject("select count(*) from " + tableName, Integer.class);
	}

	public List<Map<String,Object>> getSprints(final String tableName) throws SQLException {
		List<Map<String,Object>> set = jdbcTemplate.queryForList("select 'sprint' from " + tableName);

		set.stream().forEach(s -> System.out.println(s.toString()));

		return set;
	}

	/**
	 * Gets the sprint by id.
	 *
	 * @param tableName the table name
	 * @param id        the id
	 * @return the sprint by id
	 */
	public SprintProgress getSprintById(final String tableName, final int id) {
		final String query = "select * from " + tableName + " where id = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { id }, new SprintProgressRowMapper());
	}

	/**
	 * Gets the sprint by label.
	 *
	 * @param tableName the table name
	 * @param label     the label
	 * @return the sprint by label
	 */
	public SprintProgress getSprintByLabel(final String tableName, final String label) {
		final String query = "select * from " + tableName + " where sprint = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { label }, new SprintProgressRowMapper());
	}
}
