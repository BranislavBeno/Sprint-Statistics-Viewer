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

@Controller
public class TeamWorkProportionController {

	private static final String TEAM_TABLE_PREFIX = "team_";

	private TeamWorkProportionDAO team;

	@Autowired
	public TeamWorkProportionController(TeamWorkProportionDAO team) {
		this.team = team;
	}

	private List<String> collectSprintLabels(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<String> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getSprintLabel).collect(Collectors.toList());

		return list;
	}

	private List<Integer> collectStoriesSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedStoriesSP).collect(Collectors.toList());

		return list;
	}

	private List<Integer> collectBugfixesSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedBugsSP).collect(Collectors.toList());

		return list;
	}

	private List<Integer> collectTrendSPList(List<TeamWorkProportion> sprints) {
		// Initialize list of work proportion related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of work proportion related values
		if (sprints != null)
			list = sprints.stream().map(TeamWorkProportion::getFinishedBugsSP).collect(Collectors.toList());

		return list;
	}

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

		return "workproportion";
	}
}
