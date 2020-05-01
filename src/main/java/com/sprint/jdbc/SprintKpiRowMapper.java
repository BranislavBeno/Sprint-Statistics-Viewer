package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sprint.model.SprintKpi;

/**
 * The Class SprintKpiRowMapper.
 */
public class SprintKpiRowMapper implements RowMapper<SprintKpi> {

	/**
	 * Map row.
	 *
	 * @param rs     the rs
	 * @param rowNum the row num
	 * @return the sprint kpi
	 * @throws SQLException the SQL exception
	 */
	@Override
	public SprintKpi mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final SprintKpi sprintKpi = new SprintKpi();

		sprintKpi.setSprintLabel(rs.getString("sprint"));
		sprintKpi.setTeamName(rs.getString("team_name"));
		sprintKpi.setDeltaStoryPoints(rs.getDouble("delta_sp"));
		sprintKpi.setPlannedStoryPointsClosed(rs.getDouble("planned_sp_closed"));
		sprintKpi.setNotClosedHighPriorStoriesCount(rs.getInt("not_closed_high_prior_stories"));
		sprintKpi.setUpdated(rs.getTimestamp("updated").toLocalDateTime());

		return sprintKpi;
	}
}
