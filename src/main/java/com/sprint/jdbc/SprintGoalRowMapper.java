package com.sprint.jdbc;

import com.sprint.model.SprintGoal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class SprintGoalRowMapper.
 */
public class SprintGoalRowMapper implements RowMapper<SprintGoal> {

	/**
	 * Map row.
	 *
	 * @param rs     the rs
	 * @param rowNum the row num
	 * @return the sprint goal
	 * @throws SQLException the SQL exception
	 */
	@Override
	public SprintGoal mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		// Create new sprint goal entity
		final SprintGoal sprintGoal = new SprintGoal();

		sprintGoal.setSprintLabel(rs.getString("sprint"));
		sprintGoal.setTeamName(rs.getString("team_name"));
		sprintGoal.setSprintGoals(rs.getString("sprint_goals"));

		return sprintGoal;
	}
}
