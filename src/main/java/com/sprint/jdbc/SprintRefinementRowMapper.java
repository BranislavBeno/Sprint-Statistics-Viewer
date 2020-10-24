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
import com.sprint.model.SprintRefinement;
import com.sprint.utils.Utils;

/**
 * The Class SprintRefinementRowMapper.
 */
public class SprintRefinementRowMapper implements RowMapper<SprintRefinement> {

	/** The log. */
	private static Log log = LogFactory.getLog(SprintRefinementRowMapper.class);

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
	 * @return the sprint refinement
	 * @throws SQLException the SQL exception
	 */
	@Override
	public SprintRefinement mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		// Get refinement values in JSON
		Map<FeatureScope, Integer> refinedStoryPoints = gatherRefinedStoryPoints(rs.getString("refined_SP"));

		// Create new sprint refinement entity
		final SprintRefinement sprint = new SprintRefinement();

		// Fill fields
		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setRefinedStoryPoints(refinedStoryPoints);
		sprint.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

		return sprint;
	}
}
