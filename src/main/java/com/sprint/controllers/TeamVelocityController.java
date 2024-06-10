package com.sprint.controllers;

import com.sprint.jdbc.TeamVelocityRowMapper;
import com.sprint.model.TeamVelocity;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class TeamVelocityController.
 */
@Controller
public class TeamVelocityController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private final TeamVelocityDAO team;

	/**
	 * Instantiates a new team velocity controller.
	 *
	 * @param team the team
	 */
	public TeamVelocityController(TeamVelocityDAO team) {
		this.team = team;
	}

	/**
	 * Collect sprint labels.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectSprintLabels(List<TeamVelocity> sprints) {
		// Initialize list of velocity related sprint data
		List<String> list = new ArrayList<>();

		// Get list of velocity related values
		if (sprints != null)
			list = sprints.stream().map(TeamVelocity::getSprintLabel).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect on begin SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectOnBeginSPList(List<TeamVelocity> sprints) {
		// Initialize list of velocity related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of velocity related values
		if (sprints != null)
			list = sprints.stream().map(TeamVelocity::getOnBeginPlannedStoryPointsSum).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect on end SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectOnEndSPList(List<TeamVelocity> sprints) {
		// Initialize list of velocity related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of velocity related values
		if (sprints != null)
			list = sprints.stream().map(TeamVelocity::getOnEndPlannedStoryPointsSum).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect finished SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectFinishedSPList(List<TeamVelocity> sprints) {
		// Initialize list of velocity related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of velocity related values
		if (sprints != null)
			list = sprints.stream().map(TeamVelocity::getFinishedStoryPointsSum).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect velocity list.
	 *
	 * @param tableName the table name
	 * @return the list
	 */
	private List<List<Integer>> collectVelocityList(String tableName) {
		// Get list of team related records
		int sprintListSize = team.getSprintList(tableName, new TeamVelocityRowMapper()).size();

		// Get full list of sprints for particular team
		List<TeamVelocity> allSprints = team.getFullSprintList(tableName);

		// Initialize list of velocity related sprint data
		Deque<Integer> velocityList = new LinkedList<>();

		// Initialize list of velocity pro developer related sprint data
		Deque<Integer> devVelocityList = new LinkedList<>();

		// Initialize counter
		int counter = sprintListSize;

		// Compute velocity
		while (counter > 0) {
			// Compute velocity as double
			double velocity = allSprints.stream().collect(Utils.lastN(sprintListSize)).stream()
					.mapToInt(TeamVelocity::getFinishedStoryPointsSum).average().orElse(0);

			// Round the double value
			long lNum = StrictMath.round(velocity);

			// Put team's velocity to the head of list
			velocityList.addFirst((int) lNum);

			// Reduce input list
			TeamVelocity last = allSprints.removeLast();

			// Get team member count for related sprint
			int memCount = last.getTeamMemberCount();

			// Put velocity per developer to the head of list
			int lDev = StrictMath.round(lNum / (float) memCount);

			devVelocityList.addFirst(lDev);

			// Decrement counter
			counter--;
		}

		return List.of(new ArrayList<>(velocityList), new ArrayList<>(devVelocityList));
	}

	/**
	 * Velocity.
	 *
	 * @param teamId the team id
	 * @param model  the model
	 * @return the string
	 */
	@GetMapping("/{team}/velocity")
	public String velocity(@PathVariable("team") String teamId, Model model) {
		// Set database name
		String tableName = TEAM_TABLE_PREFIX + teamId;

		// Get list of team related records
		List<TeamVelocity> sprints = team.getSprintList(tableName, new TeamVelocityRowMapper());

		// Get last record from sprint related team data
		TeamVelocity velocity = sprints.stream().reduce((first, second) -> second).orElse(new TeamVelocity());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(velocity.getUpdated());

		// Get team name
		String teamName = velocity.getTeamName();

		// Get velocities
		List<List<Integer>> velocityList = collectVelocityList(tableName);

		// Add updated time stamp
		model.addAttribute("pageTitle", "Team " + teamName + " velocity");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", collectSprintLabels(sprints));

		// Add on sprint begin planned story points list
		model.addAttribute("mOnBeginSP", collectOnBeginSPList(sprints));

		// Add on sprint end planned story points list
		model.addAttribute("mOnEndSP", collectOnEndSPList(sprints));

		// Add finished story points list
		model.addAttribute("mFinishedSP", collectFinishedSPList(sprints));

		// Add team's velocity list
		model.addAttribute("mVelocitySP", velocityList.getFirst());

		// Add team's velocity per developer list
		model.addAttribute("mDevVelocitySP", velocityList.get(1));

		return "velocity";
	}
}
