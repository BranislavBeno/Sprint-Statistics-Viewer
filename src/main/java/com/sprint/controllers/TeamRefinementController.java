package com.sprint.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.sprint.jdbc.TeamVelocityRowMapper;
import com.sprint.jdbc.TeamRefinementRowMapper;
import com.sprint.model.TeamVelocity;
import com.sprint.model.TeamRefinement;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamRefinementController.
 */
@Controller
public class TeamRefinementController {

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The team. */
	private TeamRefinementDAO team;

	/** The velocities. */
	private TeamVelocityDAO velocities;

	/**
	 * Instantiates a new team refinement controller.
	 *
	 * @param team       the team
	 * @param velocities the velocities
	 */
	@Autowired
	public TeamRefinementController(TeamRefinementDAO team, TeamVelocityDAO velocities) {
		this.team = team;
		this.velocities = velocities;
	}

	/**
	 * Collect one SP list.
	 *
	 * @param scope      the scope
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
	 * Count one teams velocity.
	 *
	 * @param tableName the table name
	 * @return the int
	 */
	private int countOneTeamsVelocity(String tableName) {
		// Get list of sprints for particular team
		List<TeamVelocity> sprints = velocities.getSprintList(tableName, new TeamVelocityRowMapper());

		// Compute velocity
		Double velocity = sprints.stream().mapToInt(TeamVelocity::getFinishedStoryPointsSum).average().orElse(0);
		// Round the double value
		Long lNum = Math.round(velocity);

		return lNum.intValue();
	}

	/**
	 * Collect expected velocity list.
	 *
	 * @param tableName the table name
	 * @return the list
	 */
	private List<Integer> collectExpectedVelocityList(String tableName) {
		return Collections.nCopies(FeatureScope.values().length, countOneTeamsVelocity(tableName));
	}

	/**
	 * Refinement.
	 *
	 * @param teamId the team id
	 * @param model  the model
	 * @return the string
	 */
	@GetMapping("/{team}/refinement")
	public String refinement(@PathVariable("team") String teamId, Model model) {
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
		model.addAttribute("pageTitle", "Team " + teamName + " refinement");

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

		// Add expected velocity list
		model.addAttribute("mExpectedSP", collectExpectedVelocityList(tableName));

		return "refinement";
	}
}
