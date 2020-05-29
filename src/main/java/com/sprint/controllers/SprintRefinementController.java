package com.sprint.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sprint.enums.FeatureScope;
import com.sprint.model.SprintRefinement;
import com.sprint.repository.SprintRefinementDAO;

/**
 * The Class SprintRefinementController.
 */
@Controller
public class SprintRefinementController {

	/** The sprints. */
	private SprintRefinementDAO sprints;

	/**
	 * Instantiates a new sprint refinement controller.
	 *
	 * @param dao the dao
	 */
	@Autowired
	public SprintRefinementController(SprintRefinementDAO dao) {
		this.sprints = dao;
	}

	/**
	 * Gather last update time stamp.
	 *
	 * @param refinements the refinements
	 * @return the string
	 */
	private String gatherLastUpdateTimeStamp(List<SprintRefinement> refinements) {
		String updated = "unknown";
		if (refinements != null) {
			// Set time stamp about last data update
			updated = refinements.stream().findFirst().orElseThrow().getUpdated()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		}
		return updated;
	}

	/**
	 * Collect label list.
	 *
	 * @param theSprints the the sprints
	 * @return the list
	 */
	private List<String> collectLabelList(final List<SprintRefinement> theSprints) {
		// Initialize list refinement related sprint data
		List<String> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of basic story points
			list = theSprints.stream().map(SprintRefinement::getSprintLabel).collect(Collectors.toList());
		}

		return list;
	}

	/**
	 * Collect one SP list.
	 *
	 * @param scope      the scope
	 * @param theSprints the the sprints
	 * @return the list
	 */
	private List<Integer> collectOneSPList(final FeatureScope scope, final List<SprintRefinement> theSprints) {
		// Initialize list refinement related sprint data
		List<Integer> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of basic story points
			list = theSprints.stream().map(l -> l.getRefinedStoryPoints().get(scope)).collect(Collectors.toList());
		}

		return list;
	}

	/**
	 * Collect SP lists.
	 *
	 * @param sprintList the sprint list
	 * @return the map
	 */
	private Map<FeatureScope, List<Integer>> collectSPLists(final List<SprintRefinement> sprintList) {
		// Initialize collection of story points lists
		Map<FeatureScope, List<Integer>> collectedSP = new EnumMap<>(FeatureScope.class);

		// Add basic story points list
		collectedSP.put(FeatureScope.BASIC, collectOneSPList(FeatureScope.BASIC, sprintList));

		// Add advanced story points list
		collectedSP.put(FeatureScope.ADVANCED, collectOneSPList(FeatureScope.ADVANCED, sprintList));

		// Add commercial story points list
		collectedSP.put(FeatureScope.COMMERCIAL, collectOneSPList(FeatureScope.COMMERCIAL, sprintList));

		// Add future story points list
		collectedSP.put(FeatureScope.FUTURE, collectOneSPList(FeatureScope.FUTURE, sprintList));

		return collectedSP;
	}

	/**
	 * Refinement.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/refinement")
	public String refinement(Model model) {
		List<SprintRefinement> refinements = sprints.getRefinements();

		Map<FeatureScope, List<Integer>> refinedSP = collectSPLists(refinements);

		// Set time stamp of database item last update
		String updated = gatherLastUpdateTimeStamp(refinements);

		// Add updated time stamp
		model.addAttribute("pageTitle", "Summarized refinement");

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add labels list
		model.addAttribute("mLabels", collectLabelList(refinements));

		// Add basic story points list
		model.addAttribute("mBasicSP", refinedSP.get(FeatureScope.BASIC));

		// Add advanced story points list
		model.addAttribute("mAdvancedSP", refinedSP.get(FeatureScope.ADVANCED));

		// Add commercial story points list
		model.addAttribute("mCommercialSP", refinedSP.get(FeatureScope.COMMERCIAL));

		// Add future story points list
		model.addAttribute("mFutureSP", refinedSP.get(FeatureScope.FUTURE));

		return "refinement";
	}
}
