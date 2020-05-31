package com.sprint.model;

import java.time.LocalDateTime;

/**
 * The Class TeamVelocity.
 */
public class TeamVelocity {

	/** The team name. */
	private String teamName;

	/** The team member count. */
	private int teamMemberCount = 0;

	/** The on begin planned story points sum. */
	private int onBeginPlannedStoryPointsSum = 0;

	/** The on end planned story points sum. */
	private int onEndPlannedStoryPointsSum = 0;

	/** The finished story points sum. */
	private int finishedStoryPointsSum = 0;

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
	 * Gets the team member count.
	 *
	 * @return the team member count
	 */
	public int getTeamMemberCount() {
		return teamMemberCount;
	}

	/**
	 * Sets the team member count.
	 *
	 * @param teamMemberCount the new team member count
	 */
	public void setTeamMemberCount(int teamMemberCount) {
		this.teamMemberCount = teamMemberCount;
	}

	/**
	 * Gets the on begin planned story points sum.
	 *
	 * @return the on begin planned story points sum
	 */
	public int getOnBeginPlannedStoryPointsSum() {
		return onBeginPlannedStoryPointsSum;
	}

	/**
	 * Sets the on begin planned story points sum.
	 *
	 * @param onBeginPlannedStoryPointsSum the new on begin planned story points sum
	 */
	public void setOnBeginPlannedStoryPointsSum(int onBeginPlannedStoryPointsSum) {
		this.onBeginPlannedStoryPointsSum = onBeginPlannedStoryPointsSum;
	}

	/**
	 * Gets the on end planned story points sum.
	 *
	 * @return the on end planned story points sum
	 */
	public int getOnEndPlannedStoryPointsSum() {
		return onEndPlannedStoryPointsSum;
	}

	/**
	 * Sets the on end planned story points sum.
	 *
	 * @param onEndPlannedStoryPointsSum the new on end planned story points sum
	 */
	public void setOnEndPlannedStoryPointsSum(int onEndPlannedStoryPointsSum) {
		this.onEndPlannedStoryPointsSum = onEndPlannedStoryPointsSum;
	}

	/**
	 * Gets the finished story points sum.
	 *
	 * @return the finished story points sum
	 */
	public int getFinishedStoryPointsSum() {
		return finishedStoryPointsSum;
	}

	/**
	 * Sets the finished story points sum.
	 *
	 * @param finishedStoryPointsSum the new finished story points sum
	 */
	public void setFinishedStoryPointsSum(int finishedStoryPointsSum) {
		this.finishedStoryPointsSum = finishedStoryPointsSum;
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
