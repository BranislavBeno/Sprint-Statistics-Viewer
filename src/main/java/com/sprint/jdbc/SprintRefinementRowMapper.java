package com.sprint.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.enums.FeatureScope;
import com.sprint.model.SprintRefinement;
import com.sprint.utils.Utils;

/**
 * The Class SprintRefinementRowMapper.
 */
public class SprintRefinementRowMapper implements RowMapper<SprintRefinement> {

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
		String refinementJson = rs.getString("refined_SP");
		Map<FeatureScope, Integer> refinedStoryPoints = new EnumMap<>(FeatureScope.class);

		try {
			refinedStoryPoints = new ObjectMapper().readValue(refinementJson,
					new TypeReference<Map<FeatureScope, Integer>>() {
					});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// Create new sprint refinement entity
		final SprintRefinement sprint = new SprintRefinement();

		// Fill fields
		sprint.setSprintLabel(rs.getString("sprint"));
		sprint.setRefinedStoryPoints(refinedStoryPoints);
		sprint.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

		return sprint;
	}
}
