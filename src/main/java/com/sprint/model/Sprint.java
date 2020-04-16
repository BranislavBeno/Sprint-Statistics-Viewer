/**
 * 
 */
package com.sprint.model;

import java.util.StringJoiner;

/**
 * The Class Sprint.
 *
 * @author benito
 */
public class Sprint {

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

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[", "]");

		sj.add("'" + sprintLabel + "'");
		sj.add("'" + teamName + "'");
		sj.add(String.valueOf(toDoStoryPointsSum));
		sj.add(String.valueOf(inProgressStoryPointsSum));
		sj.add(String.valueOf(finishedStoryPointsSum));

		return sj.toString();
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

}
