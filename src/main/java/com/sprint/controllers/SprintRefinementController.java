package com.sprint.controllers;

import com.sprint.jdbc.TeamVelocityRowMapper;
import com.sprint.model.FeatureScope;
import com.sprint.model.SprintRefinement;
import com.sprint.model.TeamVelocity;
import com.sprint.repository.impl.SprintRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The Class SprintRefinementController.
 */
@Controller
public class SprintRefinementController {

    /**
     * The Constant TEAM_TABLE_PREFIX.
     */
    private static final String TEAM_TABLE_PREFIX = "team_";

    /**
     * The refinements.
     */
    private final SprintRefinementDAO refinements;

    /**
     * The velocities.
     */
    private final TeamVelocityDAO velocities;

    /**
     * Instantiates a new sprint refinement controller.
     *
     * @param dao        the dao
     * @param velocities the velocities
     */
    public SprintRefinementController(SprintRefinementDAO dao, TeamVelocityDAO velocities) {
        this.refinements = dao;
        this.velocities = velocities;
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
            list = theSprints.stream().map(SprintRefinement::getSprintLabel).toList();
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
            list = theSprints.stream().map(l -> l.getRefinedStoryPoints().get(scope)).toList();
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
     * Count one teams velocity.
     *
     * @param tableName the table name
     * @return the int
     */
    private int countOneTeamsVelocity(String tableName) {
        // Get list of sprints for particular team
        List<TeamVelocity> sprints = velocities.getSprintList(tableName, new TeamVelocityRowMapper());

        // Remove last sprint - it is current not finished sprint - its count of
        // finished story points is not final
        sprints.removeLast();

        // Compute velocity
        double velocity = sprints.stream().mapToInt(TeamVelocity::getFinishedStoryPointsSum).average().orElse(0);

        return (int) velocity;
    }

    /**
     * Count expected velocity.
     *
     * @return the int
     */
    private int countExpectedVelocity() {
        // Get list of team tables
        List<String> tables = velocities.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX)).toList();

        // Count list of velocities
        return tables.stream().map(this::countOneTeamsVelocity).mapToInt(v -> v).sum();
    }

    /**
     * Collect expected velocity list.
     *
     * @return the list
     */
    private List<Integer> collectExpectedVelocityList() {
        return Collections.nCopies(FeatureScope.values().length, countExpectedVelocity());
    }

    /**
     * Refinement.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/refinement")
    public String refinement(Model model) {
        // Get list of sprint related summarized refinements
        List<SprintRefinement> sprints = refinements.getRefinements();

        // Convert refinements to map
        Map<FeatureScope, List<Integer>> refinedSP = collectSPLists(sprints);

        // Set time stamp of database item last update
        String updated = gatherLastUpdateTimeStamp(sprints);

        // Add updated time stamp
        model.addAttribute("pageTitle", "Summarized refinement");

        // Add updated time stamp
        model.addAttribute("mUpdated", updated);

        // Add labels list
        model.addAttribute("mLabels", collectLabelList(sprints));

        // Add basic story points list
        model.addAttribute("mBasicSP", refinedSP.get(FeatureScope.BASIC));

        // Add advanced story points list
        model.addAttribute("mAdvancedSP", refinedSP.get(FeatureScope.ADVANCED));

        // Add commercial story points list
        model.addAttribute("mCommercialSP", refinedSP.get(FeatureScope.COMMERCIAL));

        // Add future story points list
        model.addAttribute("mFutureSP", refinedSP.get(FeatureScope.FUTURE));

        // Add expected velocity list
        model.addAttribute("mExpectedSP", collectExpectedVelocityList());

        return "refinement";
    }
}
