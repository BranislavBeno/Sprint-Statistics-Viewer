package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sprint.model.SprintGoal;

public class SprintGoalRowMapper implements RowMapper<SprintGoal> {

	@Override
	public SprintGoal mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final SprintGoal sprintGoal = new SprintGoal();

		sprintGoal.setSprintLabel(rs.getString("sprint"));
		sprintGoal.setTeamName(rs.getString("team_name"));
		sprintGoal.setSprintGoals(rs.getString("sprint_goals"));

		return sprintGoal;
	}

}
