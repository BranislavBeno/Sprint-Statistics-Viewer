package com.sprint.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sprint.enums.FeatureScope;
import com.sprint.jdbc.TeamVelocityRowMapper;
import com.sprint.model.TeamVelocity;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamVelocityController.
 */
@Controller
public class TeamVelocityController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private TeamVelocityDAO team;

	/**
	 * Instantiates a new team velocity controller.
	 *
	 * @param team the team
	 */
	@Autowired
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
			list = sprints.stream().map(s -> s.getOnBeginPlannedStoryPointsSum()).collect(Collectors.toList());

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
			list = sprints.stream().map(s -> s.getOnEndPlannedStoryPointsSum()).collect(Collectors.toList());

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
			list = sprints.stream().map(s -> s.getFinishedStoryPointsSum()).collect(Collectors.toList());

		return list;
	}

	private int countOneTeamsVelocity(String tableName) {
		// Get list of sprints for particular team
		List<TeamVelocity> sprints = team.getSprintList(tableName, new TeamVelocityRowMapper());

		// Remove last sprint - it is current not finished sprint - its count of
		// finished story points is not final
		sprints.remove(sprints.size() - 1);

		// Compute velocity
		Double velocity = sprints.stream().mapToInt(TeamVelocity::getFinishedStoryPointsSum).average().orElse(0);

		return velocity.intValue();
	}

	private List<Integer> collectVelocityList(String tableName) {
		return Collections.nCopies(FeatureScope.values().length, countOneTeamsVelocity(tableName));
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
		model.addAttribute("mVelocitySP", collectVelocityList(tableName));

		return "velocity";
	}
}
