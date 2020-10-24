package com.sprint.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The Class TeamScopeFocus.
 */
public class TeamScopeFocus {

	/** The team name. */
	private String teamName = "";

	/** The sprint label. */
	private String sprintLabel = "";

	/** The finished story points. */
	private Map<FeatureScope, Integer> finishedStoryPoints;

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
	 * Gets the finished story points.
	 *
	 * @return the finished story points
	 */
	public Map<FeatureScope, Integer> getFinishedStoryPoints() {
		return finishedStoryPoints;
	}

	/**
	 * Sets the finished story points.
	 *
	 * @param finishedStoryPoints the finished story points
	 */
	public void setFinishedStoryPoints(Map<FeatureScope, Integer> finishedStoryPoints) {
		this.finishedStoryPoints = finishedStoryPoints;
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
