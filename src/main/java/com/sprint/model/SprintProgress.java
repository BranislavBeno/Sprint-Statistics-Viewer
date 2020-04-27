/**
 * 
 */
package com.sprint.model;

import java.time.LocalDate;

public class SprintProgress {

	/** The sprint label. */
	private String sprintLabel;

	/** The team name. */
	private String teamName;

	/** The to do story points sum. */
	private int toDoStoryPointsSum = 0;

	/** The in progress story points sum. */
	private int inProgressStoryPointsSum = 0;

	/** The finished story points summary. */
	private int finishedStoryPointsSum = 0;

	/** The sprint start. */
	private LocalDate sprintStart = LocalDate.of(1970, 1, 1);

	/** The sprint end. */
	private LocalDate sprintEnd = LocalDate.of(1970, 1, 1);

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
	 * Gets the to do story points sum.
	 *
	 * @return the to do story points sum
	 */
	public Integer getToDoStoryPointsSum() {
		return toDoStoryPointsSum;
	}

	/**
	 * Sets the to do story points sum.
	 *
	 * @param toDoStoryPointsSum the new to do story points sum
	 */
	public void setToDoStoryPointsSum(int toDoStoryPointsSum) {
		this.toDoStoryPointsSum = toDoStoryPointsSum;
	}

	/**
	 * Gets the in progress story points sum.
	 *
	 * @return the in progress story points sum
	 */
	public Integer getInProgressStoryPointsSum() {
		return inProgressStoryPointsSum;
	}

	/**
	 * Sets the in progress story points sum.
	 *
	 * @param inProgressStoryPointsSum the new in progress story points sum
	 */
	public void setInProgressStoryPointsSum(int inProgressStoryPointsSum) {
		this.inProgressStoryPointsSum = inProgressStoryPointsSum;
	}

	/**
	 * Gets the finished story points sum.
	 *
	 * @return the finished story points sum
	 */
	public Integer getFinishedStoryPointsSum() {
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
	 * Gets the sprint start.
	 *
	 * @return the sprint start
	 */
	public LocalDate getSprintStart() {
		return sprintStart;
	}

	/**
	 * Sets the sprint start.
	 *
	 * @param sprintStart the new sprint start
	 */
	public void setSprintStart(LocalDate sprintStart) {
		this.sprintStart = sprintStart;
	}

	/**
	 * Gets the sprint end.
	 *
	 * @return the sprint end
	 */
	public LocalDate getSprintEnd() {
		return sprintEnd;
	}

	/**
	 * Sets the sprint end.
	 *
	 * @param sprintEnd the new sprint end
	 */
	public void setSprintEnd(LocalDate sprintEnd) {
		this.sprintEnd = sprintEnd;
	}
}
