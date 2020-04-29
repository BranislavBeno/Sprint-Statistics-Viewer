package com.sprint.model;

/**
 * The Class SprintKpi.
 */
public class SprintKpi {

	/** The sprint label. */
	private String sprintLabel = "";

	/** The team name. */
	private String teamName = "";

	/** The delta story points. */
	private double deltaStoryPoints = 0;

	/** The planned story points closed. */
	private double plannedStoryPointsClosed = 0;

	/** The not closed high prior stories count. */
	private int notClosedHighPriorStoriesCount = 0;

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
	 * Gets the delta story points.
	 *
	 * @return the delta story points
	 */
	public double getDeltaStoryPoints() {
		return deltaStoryPoints;
	}

	/**
	 * Sets the delta story points.
	 *
	 * @param deltaStoryPoints the new delta story points
	 */
	public void setDeltaStoryPoints(double deltaStoryPoints) {
		this.deltaStoryPoints = deltaStoryPoints;
	}

	/**
	 * Gets the planned story points closed.
	 *
	 * @return the planned story points closed
	 */
	public double getPlannedStoryPointsClosed() {
		return plannedStoryPointsClosed;
	}

	/**
	 * Sets the planned story points closed.
	 *
	 * @param plannedStoryPointsClosed the new planned story points closed
	 */
	public void setPlannedStoryPointsClosed(double plannedStoryPointsClosed) {
		this.plannedStoryPointsClosed = plannedStoryPointsClosed;
	}

	/**
	 * Gets the not closed high prior stories count.
	 *
	 * @return the not closed high prior stories count
	 */
	public int getNotClosedHighPriorStoriesCount() {
		return notClosedHighPriorStoriesCount;
	}

	/**
	 * Sets the not closed high prior stories count.
	 *
	 * @param notClosedHighPriorStoriesCount the new not closed high prior stories
	 *                                       count
	 */
	public void setNotClosedHighPriorStoriesCount(int notClosedHighPriorStoriesCount) {
		this.notClosedHighPriorStoriesCount = notClosedHighPriorStoriesCount;
	}
}
