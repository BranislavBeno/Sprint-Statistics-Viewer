package com.sprint.controllers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sprint.enums.FeatureScope;
import com.sprint.jdbc.TeamScopeFocusRowMapper;
import com.sprint.model.TeamScopeFocus;
import com.sprint.repository.impl.TeamScopeFocusDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamScopeFocusController.
 */
@Controller
public class TeamScopeFocusController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private TeamScopeFocusDAO team;

	/**
	 * Instantiates a new team scope focus controller.
	 *
	 * @param team the team
	 */
	@Autowired
	public TeamScopeFocusController(TeamScopeFocusDAO team) {
		this.team = team;
	}

	/**
	 * Collect one SP list.
	 *
	 * @param scope      the scope
	 * @return the list
	 */
	private List<Integer> collectOneSPList(final FeatureScope scope) {
		// Initialize list refinement related sprint data
		List<Integer> list = new ArrayList<>();

		return list;
	}

	/**
	 * Collect SP lists.
	 *
	 * @return the map
	 */
	private Map<FeatureScope, List<Integer>> collectSPLists() {
		// Initialize collection of story points lists
		Map<FeatureScope, List<Integer>> collectedSP = new EnumMap<>(FeatureScope.class);

		// Add basic story points list
		collectedSP.put(FeatureScope.BASIC, collectOneSPList(FeatureScope.BASIC));

		// Add advanced story points list
		collectedSP.put(FeatureScope.ADVANCED, collectOneSPList(FeatureScope.ADVANCED));

		// Add commercial story points list
		collectedSP.put(FeatureScope.COMMERCIAL, collectOneSPList(FeatureScope.COMMERCIAL));

		// Add future story points list
		collectedSP.put(FeatureScope.FUTURE, collectOneSPList(FeatureScope.FUTURE));

		return collectedSP;
	}

	/**
	 * Scope focus.
	 *
	 * @param teamId the team id
	 * @param model  the model
	 * @return the string
	 */
	@GetMapping("/{team}/scopefocus")
	public String scopeFocus(@PathVariable("team") String teamId, Model model) {
		// Set database name
		String tableName = TEAM_TABLE_PREFIX + teamId;

		// Get list of team related records
		List<TeamScopeFocus> sprints = team.getSprintList(tableName, new TeamScopeFocusRowMapper());

		// Get sprint related team data
		TeamScopeFocus scopeFocus = sprints.stream().findFirst().orElse(new TeamScopeFocus());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(scopeFocus.getUpdated());

		// Get team name
		String teamName = scopeFocus.getTeamName();

		// Get finished story points
		Map<FeatureScope, Integer> finishedSP = scopeFocus.getFinishedStoryPoints();

		// Convert refinements to map
		Map<FeatureScope, List<Integer>> collectedSP = collectSPLists();

		// Add updated time stamp
		model.addAttribute("pageTitle", "Team " + teamName + " scope focus");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", finishedSP.keySet());

		// Add basic story points list
		model.addAttribute("mBasicSP", collectedSP.get(FeatureScope.BASIC));

		// Add advanced story points list
		model.addAttribute("mAdvancedSP", collectedSP.get(FeatureScope.ADVANCED));

		// Add commercial story points list
		model.addAttribute("mCommercialSP", collectedSP.get(FeatureScope.COMMERCIAL));

		// Add future story points list
		model.addAttribute("mFutureSP", collectedSP.get(FeatureScope.FUTURE));

		return "scopefocus";
	}
}
