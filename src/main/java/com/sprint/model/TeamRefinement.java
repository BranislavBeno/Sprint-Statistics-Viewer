package com.sprint.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The Class TeamRefinement.
 */
public class TeamRefinement {

	/** The team name. */
	private String teamName = "";

	/** The refined story points. */
	private Map<String, Map<FeatureScope, Integer>> refinedStoryPoints;

	/** The updated. */
	private LocalDateTime updated;

	/**
	 * Gets the team name.
	 *
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Sets the team name.
	 *
	 * @param teamName the new team name
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Gets the refined story points.
	 *
	 * @return the refined story points
	 */
	public Map<String, Map<FeatureScope, Integer>> getRefinedStoryPoints() {
		return refinedStoryPoints;
	}

	/**
	 * Sets the refined story points.
	 *
	 * @param refinedStoryPoints the refined story points
	 */
	public void setRefinedStoryPoints(Map<String, Map<FeatureScope, Integer>> refinedStoryPoints) {
		this.refinedStoryPoints = refinedStoryPoints;
	}

	/**
	 * Gets the updated.
	 *
	 * @return the updated
	 */
	public LocalDateTime getUpdated() {
		return updated;
	}

	/**
	 * Sets the updated.
	 *
	 * @param updated the new updated
	 */
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
}
