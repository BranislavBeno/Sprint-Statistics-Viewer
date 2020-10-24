package com.sprint.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The Class SprintGoal.
 */
public class SprintRefinement {

	/** The sprint label. */
	private String sprintLabel = "";

	/** The refined story points. */
	private Map<FeatureScope, Integer> refinedStoryPoints;

	/** The updated. */
	private LocalDateTime updated;

	/**
	 * Gets the sprint label.
	 *
	 * @return the sprint label
	 */
	public String getSprintLabel() {
		return sprintLabel;
	}

	/**
	 * Sets the sprint label.
	 *
	 * @param sprintLabel the new sprint label
	 */
	public void setSprintLabel(String sprintLabel) {
		this.sprintLabel = sprintLabel;
	}

	/**
	 * Gets the refined story points.
	 *
	 * @return the refined story points
	 */
	public Map<FeatureScope, Integer> getRefinedStoryPoints() {
		return refinedStoryPoints;
	}

	/**
	 * Sets the refined story points.
	 *
	 * @param refinedStoryPoints the refined story points
	 */
	public void setRefinedStoryPoints(Map<FeatureScope, Integer> refinedStoryPoints) {
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
