package com.sprint.jdbc;

import com.sprint.model.TeamWorkProportion;
import com.sprint.utils.Utils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Class TeamWorkProportionRowMapper.
 */
public class TeamWorkProportionRowMapper implements RowMapper<TeamWorkProportion> {

	/**
	 * Map row.
	 *
	 * @param rs the rs
	 * @param rowNum the row num
	 * @return the team work proportion
	 * @throws SQLException the SQL exception
	 */
	@Override
	public TeamWorkProportion mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Create new sprint team proportion entity
		final TeamWorkProportion sprint = new TeamWorkProportion();

		sprint.setTeamName(rs.getString("team_name"));
		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setFinishedStoriesSP(rs.getInt("finished_stories_sp_sum"));
		sprint.setFinishedBugsSP(rs.getInt("finished_bugs_sp_sum"));
		sprint.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

		return sprint;
	}
}
