package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sprint.model.Sprint;

public class SprintRowMapper implements RowMapper<Sprint> {

	@Override
	public Sprint mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Sprint sprint = new Sprint();

		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setTeamName(rs.getString("team_name"));
		sprint.setToDoStoryPointsSum(rs.getInt("to_do_sp_sum"));
		sprint.setInProgressStoryPointsSum(rs.getInt("in_progress_sp_sum"));
		sprint.setFinishedStoryPointsSum(rs.getInt("finished_sp_sum"));

		return sprint;
	}

}
