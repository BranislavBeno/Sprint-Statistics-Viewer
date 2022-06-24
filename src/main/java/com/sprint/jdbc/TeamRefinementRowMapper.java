package com.sprint.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.model.FeatureScope;
import com.sprint.model.TeamRefinement;
import com.sprint.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class TeamRefinementRowMapper.
 */
public class TeamRefinementRowMapper implements RowMapper<TeamRefinement> {

    /**
     * The log.
     */
    private static final Log LOG = LogFactory.getLog(TeamRefinementRowMapper.class);

    /**
     * Gather refined story points.
     *
     * @param sprintRefinementJson the sprint refinement JSON
     * @return the map
     */
    private Map<FeatureScope, Integer> gatherRefinedStoryPoints(String sprintRefinementJson) {
        // Initialize map of refinements
        Map<FeatureScope, Integer> refinedStoryPoints = new EnumMap<>(FeatureScope.class);

        try {
            refinedStoryPoints = new ObjectMapper().readValue(sprintRefinementJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            LOG.error("Parsing of refined story points failed.");
        }
        return refinedStoryPoints;
    }

    /**
     * Gather refined story points.
     *
     * @param refinementJson the refinement JSON
     * @return the map
     */
    private Map<String, Map<FeatureScope, Integer>> gatherRefinedStoryPointsMap(String refinementJson) {
        // Initialize collection with refined story points
        Map<String, Map<FeatureScope, Integer>> refinedStoryPointsMap = new LinkedHashMap<>();

        try {
            // Get map of sprints with related refinement data in JSON format
            Map<String, String> map = new ObjectMapper().readValue(refinementJson, new TypeReference<>() {
            });

            // Iterate over map entries of sprint
            for (Entry<String, String> entry : map.entrySet()) {
                // Get sprint label
                String sprintLabel = entry.getKey();
                // Get map with refinements entries in JSON format
                String sprintRefinementJson = entry.getValue();

                // Fill collection with refined story points
                refinedStoryPointsMap.put(sprintLabel, gatherRefinedStoryPoints(sprintRefinementJson));
            }

        } catch (JsonProcessingException e) {
            LOG.error("Parsing sprint list of refined story points failed.");
        }
        return refinedStoryPointsMap;
    }

    /**
     * Map row.
     *
     * @param rs     the rs
     * @param rowNum the row num
     * @return the team refinement
     * @throws SQLException the SQL exception
     */
    @Override
    public TeamRefinement mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        // Get refinement values in JSON format
        String refinementJson = rs.getString("refined_story_points");

        // Create new sprint refinement entity
        final TeamRefinement sprint = new TeamRefinement();

        // Fill fields
        sprint.setTeamName(rs.getString("team_name"));
        sprint.setRefinedStoryPoints(gatherRefinedStoryPointsMap(refinementJson));
        sprint.setUpdated(Utils.convertTimeStampToLocalDateTime(rs.getTimestamp("updated")));

        return sprint;
    }
}
