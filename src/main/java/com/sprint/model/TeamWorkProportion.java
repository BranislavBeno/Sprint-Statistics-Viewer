package com.sprint.model;

import java.time.LocalDateTime;

/**
 * The Class TeamWorkProportion.
 */
public class TeamWorkProportion {

	/** The team name. */
	private String teamName;

	/** The sprint label. */
	private String sprintLabel;

	/** The finished stories SP. */
	private int finishedStoriesSP = 0;

	/** The finished bugs SP. */
	private int finishedBugsSP = 0;

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
	 * Gets the finished stories SP.
	 *
	 * @return the finished stories SP
	 */
	public int getFinishedStoriesSP() {
		return finishedStoriesSP;
	}

	/**
	 * Sets the finished stories SP.
	 *
	 * @param finishedStoriesSP the new finished stories SP
	 */
	public void setFinishedStoriesSP(int finishedStoriesSP) {
		this.finishedStoriesSP = finishedStoriesSP;
	}

	/**
	 * Gets the finished bugs SP.
	 *
	 * @return the finished bugs SP
	 */
	public int getFinishedBugsSP() {
		return finishedBugsSP;
	}

	/**
	 * Sets the finished bugs SP.
	 *
	 * @param finishedBugsSP the new finished bugs SP
	 */
	public void setFinishedBugsSP(int finishedBugsSP) {
		this.finishedBugsSP = finishedBugsSP;
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
