package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.sprint.model.Sprint;

/**
 * The Class SprintRowMapper.
 */
public class SprintRowMapper implements RowMapper<Sprint> {

	/**
	 * Map row.
	 *
	 * @param rs     the rs
	 * @param rowNum the row num
	 * @return the sprint
	 * @throws SQLException the SQL exception
	 */
	@Override
	public Sprint mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		// Get starting date of sprint
		Date theStart = rs.getDate("sprint_start");
		LocalDate start = Instant.ofEpochMilli(theStart.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

		// Get ending date of sprint
		Date theEnd = rs.getDate("sprint_end");
		LocalDate end = Instant.ofEpochMilli(theEnd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

		// Create new Sprint entity
		final Sprint sprint = new Sprint();

		// Fill fields
		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setTeamName(rs.getString("team_name"));
		sprint.setToDoStoryPointsSum(rs.getInt("to_do_sp_sum"));
		sprint.setInProgressStoryPointsSum(rs.getInt("in_progress_sp_sum"));
		sprint.setFinishedStoryPointsSum(rs.getInt("finished_sp_sum"));
		sprint.setSprintStart(start);
		sprint.setSprintEnd(end);

		return sprint;
	}

}
