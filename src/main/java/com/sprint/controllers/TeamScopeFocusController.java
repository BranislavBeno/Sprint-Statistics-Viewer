package com.sprint.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sprint.enums.FeatureScope;
import com.sprint.jdbc.TeamRefinementRowMapper;
import com.sprint.model.TeamRefinement;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamScopeFocusController.
 */
@Controller
public class TeamScopeFocusController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private TeamRefinementDAO team;

	/**
	 * Instantiates a new team scope focus controller.
	 *
	 * @param team the team
	 * @param velocities the velocities
	 */
	@Autowired
	public TeamScopeFocusController(TeamRefinementDAO team, TeamVelocityDAO velocities) {
		this.team = team;
	}

	/**
	 * Collect one SP list.
	 *
	 * @param scope the scope
	 * @param theSprints the the sprints
	 * @return the list
	 */
	private List<Integer> collectOneSPList(final FeatureScope scope,
			final Collection<Map<FeatureScope, Integer>> theSprints) {
		// Initialize list refinement related sprint data
		List<Integer> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of basic story points
			list = theSprints.stream().map(l -> l.get(scope)).collect(Collectors.toList());
		}

		return list;
	}

	/**
	 * Collect SP lists.
	 *
	 * @param refinedSP the refined SP
	 * @return the map
	 */
	private Map<FeatureScope, List<Integer>> collectSPLists(final Collection<Map<FeatureScope, Integer>> refinedSP) {
		// Initialize collection of story points lists
		Map<FeatureScope, List<Integer>> collectedSP = new EnumMap<>(FeatureScope.class);

		// Add basic story points list
		collectedSP.put(FeatureScope.BASIC, collectOneSPList(FeatureScope.BASIC, refinedSP));

		// Add advanced story points list
		collectedSP.put(FeatureScope.ADVANCED, collectOneSPList(FeatureScope.ADVANCED, refinedSP));

		// Add commercial story points list
		collectedSP.put(FeatureScope.COMMERCIAL, collectOneSPList(FeatureScope.COMMERCIAL, refinedSP));

		// Add future story points list
		collectedSP.put(FeatureScope.FUTURE, collectOneSPList(FeatureScope.FUTURE, refinedSP));

		return collectedSP;
	}

	/**
	 * Scope focus.
	 *
	 * @param teamId the team id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/{team}/scopefocus")
	public String scopeFocus(@PathVariable("team") String teamId, Model model) {
		// Set database name
		String tableName = TEAM_TABLE_PREFIX + teamId;
		// Get list of team related records
		List<TeamRefinement> refinements = team.getCurrentSprint(tableName, new TeamRefinementRowMapper());

		// Get refinement related team data
		TeamRefinement refinement = refinements.stream().findFirst().orElse(new TeamRefinement());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(refinement.getUpdated());

		// Get team name
		String teamName = refinement.getTeamName();

		// Get map team specific sprint related refinements
		Map<String, Map<FeatureScope, Integer>> refinedSP = refinement.getRefinedStoryPoints();

		// Convert refinements to map
		Map<FeatureScope, List<Integer>> collectedSP = collectSPLists(refinedSP.values());

		// Add updated time stamp
		model.addAttribute("pageTitle", "Team " + teamName + " scope focus");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", refinedSP.keySet());

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
