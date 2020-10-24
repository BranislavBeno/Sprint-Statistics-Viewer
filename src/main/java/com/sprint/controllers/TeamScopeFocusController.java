package com.sprint.controllers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sprint.model.FeatureScope;
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
	 * Collect sprint labels.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectSprintLabels(List<TeamScopeFocus> sprints) {
		// Initialize list of scope focus related sprint data
		List<String> list = new ArrayList<>();

		// Get list of scope related values
		if (sprints != null)
			list = sprints.stream().map(TeamScopeFocus::getSprintLabel).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect one SP list.
	 *
	 * @param scope   the scope
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectOneSPList(final FeatureScope scope, List<TeamScopeFocus> sprints) {
		// Initialize list of scope focus related sprint data
		List<Integer> list = new ArrayList<>();

		// Get list of scope related values
		if (sprints != null)
			list = sprints.stream().map(s -> s.getFinishedStoryPoints().get(scope)).collect(Collectors.toList());

		return list;
	}

	/**
	 * Collect SP lists.
	 *
	 * @param sprints the sprints
	 * @return the map
	 */
	private Map<FeatureScope, List<Integer>> collectSPLists(List<TeamScopeFocus> sprints) {
		// Initialize collection of story points lists
		Map<FeatureScope, List<Integer>> collectedSP = new EnumMap<>(FeatureScope.class);

		// Add basic story points list
		collectedSP.put(FeatureScope.BASIC, collectOneSPList(FeatureScope.BASIC, sprints));

		// Add advanced story points list
		collectedSP.put(FeatureScope.ADVANCED, collectOneSPList(FeatureScope.ADVANCED, sprints));

		// Add commercial story points list
		collectedSP.put(FeatureScope.COMMERCIAL, collectOneSPList(FeatureScope.COMMERCIAL, sprints));

		// Add future story points list
		collectedSP.put(FeatureScope.FUTURE, collectOneSPList(FeatureScope.FUTURE, sprints));

		return collectedSP;
	}

	/**
	 * Compute percentage.
	 *
	 * @param future the future
	 * @param sum the sum
	 * @return the long
	 */
	private long computePercentage(int future, int sum) {
		return Math.round((double) future / sum * 100);
	}

	/**
	 * Collect percentage lists.
	 *
	 * @param spLists the sp lists
	 * @return the map
	 */
	private Map<FeatureScope, List<Integer>> collectPercentageLists(final Map<FeatureScope, List<Integer>> spLists) {
		// Initialize collection of percentage lists
		Map<FeatureScope, List<Integer>> collectedPercentage = new EnumMap<>(FeatureScope.class);

		// Initialize particular percentage lists
		List<Integer> futureList = new ArrayList<>();
		List<Integer> commercialList = new ArrayList<>();
		List<Integer> advancedList = new ArrayList<>();
		List<Integer> basicList = new ArrayList<>();

		// Count length of first list
		int count = spLists.get(FeatureScope.BASIC).size();

		// Fill particular percentage lists
		for (int i = 0; i < count; i++) {
			// Get future value
			int future = spLists.get(FeatureScope.FUTURE).get(i);
			// Get commercial value
			int commercial = spLists.get(FeatureScope.COMMERCIAL).get(i);
			// Get advanced value
			int advanced = spLists.get(FeatureScope.ADVANCED).get(i);
			// Get basic value
			int basic = spLists.get(FeatureScope.BASIC).get(i);
			// Sum of particular scopes
			int sum = future + commercial + advanced + basic;

			// Compute future percentage
			Long futurePercentage = computePercentage(future, sum);
			// Compute commercial percentage
			Long commercialPercentage = computePercentage(commercial, sum);
			// Compute advanced percentage
			Long advancedPercentage = computePercentage(advanced, sum);
			// Compute basic percentage
			int basicPercentage = 100 - futurePercentage.intValue() - commercialPercentage.intValue()
					- advancedPercentage.intValue();

			// Fill particular percentage lists
			futureList.add(futurePercentage.intValue());
			commercialList.add(commercialPercentage.intValue());
			advancedList.add(advancedPercentage.intValue());
			basicList.add(basicPercentage);
		}

		// Add percentage lists to resulting map
		collectedPercentage.put(FeatureScope.BASIC, basicList);
		collectedPercentage.put(FeatureScope.ADVANCED, advancedList);
		collectedPercentage.put(FeatureScope.COMMERCIAL, commercialList);
		collectedPercentage.put(FeatureScope.FUTURE, futureList);

		return collectedPercentage;
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

		// Get last record from sprint related team data
		TeamScopeFocus scopeFocus = sprints.stream().reduce((first, second) -> second).orElse(new TeamScopeFocus());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(scopeFocus.getUpdated());

		// Get team name
		String teamName = scopeFocus.getTeamName();

		// Convert scope focuses to map
		Map<FeatureScope, List<Integer>> collectedSP = collectPercentageLists(collectSPLists(sprints));

		// Add updated time stamp
		model.addAttribute("pageTitle", "Team " + teamName + " scope focus");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", collectSprintLabels(sprints));

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
