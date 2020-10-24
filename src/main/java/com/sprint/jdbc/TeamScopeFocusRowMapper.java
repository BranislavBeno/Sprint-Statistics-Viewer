package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.model.FeatureScope;
import com.sprint.model.TeamScopeFocus;
import com.sprint.utils.Utils;

/**
 * The Class TeamScopeFocusRowMapper.
 */
public class TeamScopeFocusRowMapper implements RowMapper<TeamScopeFocus> {

	/** The log. */
	private static Log log = LogFactory.getLog(TeamScopeFocusRowMapper.class);

	/**
	 * Gather refined story points.
	 *
	 * @param refinementJson the refinement json
	 * @return the map
	 */
	private Map<FeatureScope, Integer> gatherRefinedStoryPoints(String refinementJson) {
		Map<FeatureScope, Integer> refinedStoryPoints = new EnumMap<>(FeatureScope.class);

		try {
			refinedStoryPoints = new ObjectMapper().readValue(refinementJson,
					new TypeReference<Map<FeatureScope, Integer>>() {
					});
		} catch (JsonProcessingException e) {
			log.error("Parsing of refined story points failed.");
		}
		return refinedStoryPoints;
	}

	/**
	 * Map row.
	 *
	 * @param rs     the rs
	 * @param rowNum the row num
	 * @return the team scope focus
	 * @throws SQLException the SQL exception
	 */
	@Override
	public TeamScopeFocus mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		// Get refinement values in JSON
		Map<FeatureScope, Integer> finishedStoryPoints = gatherRefinedStoryPoints(rs.getString("finished_sp"));

		// Create new team scope focus entity
		final TeamScopeFocus team = new TeamScopeFocus();

		// Fill fields
		team.setTeamName(rs.getString("team_name"));
		team.setSprintLabel(rs.getString("sprint"));
		team.setFinishedStoryPoints(finishedStoryPoints);
		team.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

		return team;
	}
}
