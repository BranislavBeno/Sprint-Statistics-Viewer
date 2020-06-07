package com.sprint.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sprint.jdbc.TeamWorkProportionRowMapper;
import com.sprint.model.TeamWorkProportion;
import com.sprint.repository.impl.TeamWorkProportionDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamWorkProportionController.
 */
@Controller
public class TeamWorkProportionController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private TeamWorkProportionDAO team;

	/**
	 * Instantiates a new team work proportion controller.
	 *
	 * @param team the team
	 */
	@Autowired
	public TeamWorkProportionController(TeamWorkProportionDAO team) {
		this.team = team;
	}

	/**
	 * Collect sprint labels.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectSprintLabels(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<String> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getSprintLabel).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect stories SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectStoriesSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedStoriesSP).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect bugfixes SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectBugfixesSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedBugsSP).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect trend SP list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectTrendSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedBugsSP).collect(Collectors.toList());

		return Utils.computeLinearTrend(list);
	}

	/**
	 * Collect pie chart data.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectPieChartData(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null) {
			// Compute absolute sum finished stories
			int stories = sprints.stream().map(TeamWorkProportion::getFinishedStoriesSP)
					.collect(Collectors.summingInt(v -> v));

			// Compute absolute sum fixed bugs
			int bugs = sprints.stream().map(TeamWorkProportion::getFinishedBugsSP)
					.collect(Collectors.summingInt(v -> v));

			// Compute fixed bugs percentage
			Double res = 100 * (double) bugs / (bugs + stories);

			// Add data to output list
			list.add(100 - res.intValue());
			list.add(res.intValue());
		}

		return list;
	}

	/**
	 * Work proportion.
	 *
	 * @param teamId the team id
	 * @param model  the model
	 * @return the string
	 */
	@GetMapping("/{team}/workproportion")
	public String workProportion(@PathVariable("team") String teamId, Model model) {
		// Set database name
		String tableName = TEAM_TABLE_PREFIX + teamId;

		// Get list of team related records
		List<TeamWorkProportion> sprints = team.getSprintList(tableName, new TeamWorkProportionRowMapper());

		// Get last record from sprint related team data
		TeamWorkProportion velocity = sprints.stream().reduce((first, second) -> second)
				.orElse(new TeamWorkProportion());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(velocity.getUpdated());

		// Get team name
		String teamName = velocity.getTeamName();

		// Add updated time stamp
		model.addAttribute("pageTitle", "Team " + teamName + " work proportion");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", collectSprintLabels(sprints));

		// Add new implementation and rework story points list
		model.addAttribute("mStoriesSP", collectStoriesSPList(sprints));

		// Add on sprint end planned story points list
		model.addAttribute("mBugfixesSP", collectBugfixesSPList(sprints));

		// Add on sprint end planned story points list
		model.addAttribute("mTrendSP", collectTrendSPList(sprints));

		// Add on sprint end planned story points list
		model.addAttribute("mPercentageSP", collectPieChartData(sprints));

		return "workproportion";
	}
}
