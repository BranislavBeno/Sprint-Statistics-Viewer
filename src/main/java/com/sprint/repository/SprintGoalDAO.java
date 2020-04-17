package com.sprint.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sprint.jdbc.SprintGoalRowMapper;
import com.sprint.model.SprintGoal;

@Repository
public class SprintGoalDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

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

	public int getRowCount(final String tableName) {
		return jdbcTemplate.queryForObject("select count(*) from " + tableName, Integer.class);
	}

	public SprintGoal getSprintById(final String tableName, final int id) {
		final String query = "select * from " + tableName + " where id = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { id }, new SprintGoalRowMapper());
	}

	public SprintGoal getSprintByLabel(final String tableName, final String label) {
		final String query = "select * from " + tableName + " where sprint = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { label }, new SprintGoalRowMapper());
	}
}
