package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sprint.model.TeamVelocity;
import com.sprint.utils.Utils;

/**
 * The Class TeamVelocityRowMapper.
 */
public class TeamVelocityRowMapper implements RowMapper<TeamVelocity> {

	/**
	 * Map row.
	 *
	 * @param rs the rs
	 * @param rowNum the row num
	 * @return the sprint velocity
	 * @throws SQLException the SQL exception
	 */
	@Override
	public TeamVelocity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		// Create new sprint velocity entity
		final TeamVelocity sprint = new TeamVelocity();

		// Fill fields
		sprint.setTeamName(rs.getString("team_name"));
		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setTeamMemberCount(rs.getInt("team_member_count"));
		sprint.setOnBeginPlannedStoryPointsSum(rs.getInt("on_begin_planned_sp_sum"));
		sprint.setOnEndPlannedStoryPointsSum(rs.getInt("on_end_planned_sp_sum"));
		sprint.setFinishedStoryPointsSum(rs.getInt("finished_sp_sum"));
		sprint.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

		return sprint;
	}
}
