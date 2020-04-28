package com.sprint.model;

/**
 * The Class TeamGoal.
 */
public class TeamGoal {

	/** The team name. */
	private String teamName = "";

	/** The goals. */
	private String[] goals = new String[] {};

	/**
	 * Instantiates a new team goal.
	 *
	 * @param teamName the team name
	 * @param goals the goals
	 */
	public TeamGoal(String teamName, String[] goals) {
		this.teamName = teamName;
		this.goals = goals;
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
	 * Gets the goals.
	 *
	 * @return the goals
	 */
	public String[] getGoals() {
		return goals;
	}
}
