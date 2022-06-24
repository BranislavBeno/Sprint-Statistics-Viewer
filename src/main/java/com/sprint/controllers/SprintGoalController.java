/**
 *
 */
package com.sprint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.model.SprintGoal;
import com.sprint.model.TeamGoal;
import com.sprint.repository.impl.SprintGoalDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.*;

/**
 * The Class SprintGoalController.
 *
 * @author benito
 */
@Controller
public class SprintGoalController {

    /**
     * The log.
     */
    private static final Log LOG = LogFactory.getLog(SprintGoalController.class);

    /**
     * The Constant TEAM_TABLE_PREFIX.
     */
    private static final String TEAM_TABLE_PREFIX = "team_";

    /**
     * The sprint goals.
     */
    private final SprintGoalDAO sprints;

    /**
     * Instantiates a new sprint goal controller.
     *
     * @param dao the dao
     */
    @Autowired
    public SprintGoalController(SprintGoalDAO dao) {
        this.sprints = dao;
    }

    /**
     * Find sprint by label.
     *
     * @param label the label
     * @return the list
     */
    private List<SprintGoal> findSprintByLabel(final String label) {
        // Initialize list of teams
        List<SprintGoal> teams = null;
        try {
            teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
                    .map(tn -> sprints.getSprintByLabel(tn, label)).toList();
        } catch (Exception e) {
            try {
                teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
                        .map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).toList();
            } catch (SQLException e1) {
                LOG.warn("No sprint goal data found.");
            }
        }

        return teams;
    }

    /**
     * Prepare team data.
     *
     * @param teams the teams
     * @return the list
     */
    private List<TeamGoal> collectTeamGoals(List<SprintGoal> teams) {
        // Initialize list with output parameters
        List<TeamGoal> teamGoals = new ArrayList<>();

        for (SprintGoal team : teams) {
            // Initialize array for goals
            String[] goals = new String[]{};

            // Initialize jackson mapper for json string
            ObjectMapper mapper = new ObjectMapper();
            // Fill goals array
            try {
                goals = mapper.readValue(team.getSprintGoals(), String[].class);
            } catch (JsonProcessingException e) {
                LOG.warn("Conversion from json to list of goals for team " + team.getTeamName() + " failed.");
            }

            // Add new entry with team name and team goals into list
            teamGoals.add(new TeamGoal(team.getTeamName(), goals));
        }

        return teamGoals;
    }

    /**
     * Collect sprints.
     *
     * @return the sets the
     * @throws SQLException the SQL exception
     */
    private Set<String> collectSprints() throws SQLException {
        // Initialize empty set of sprints
        Set<String> sprintSet = new TreeSet<>();

        // Get list of database tables related with team related sprint data
        List<String> teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX)).toList();

        // Get set of sprint labels
        if (!teams.isEmpty()) {
            // Initialize counter
            int i = 0;
            // Get first set
            sprintSet = new TreeSet<>(sprints.getSprintList(teams.get(i)));

            // Create intersection of sprint labels over all team related sprint data
            for (i = 1; i < teams.size(); i++) {
                Set<String> newSet = new TreeSet<>(sprints.getSprintList(teams.get(i)));
                sprintSet.retainAll(newSet);
            }
        }

        return sprintSet;
    }

    /**
     * Goals.
     *
     * @param label the label
     * @param model the model
     * @return the string
     * @throws SQLException the SQL exception
     */
    @GetMapping("/goals")
    public String goals(@RequestParam("sprint") String label, Model model) throws SQLException {
        // Get list of sprint related team data
        List<SprintGoal> teams = findSprintByLabel(label);

        // In case that method parameter 'sprintLabel' is not compliant with data in
        // database,
        // last sprint related data record in database is chosen
        // In that case is sprint label updated
        List<TeamGoal> teamGoals = Collections.emptyList();
        if (teams != null) {
            label = teams.stream().findFirst().orElseThrow().getSprintLabel();
            // Sort list of database tables
            teams.sort(Comparator.comparing(SprintGoal::getTeamName));
            // Collect list of team related sprint goals
            teamGoals = collectTeamGoals(teams);
        }

        // Collect sprint labels for all available team related sprint data
        Set<String> sprintSet = collectSprints();

        // Add sprint label
        model.addAttribute("mSprintLabel", label);

        // Add list of sprints
        model.addAttribute("mSprintList", sprintSet);

        // Add list of team related sprint goals
        model.addAttribute("mTeamGoals", teamGoals);

        return "goals";
    }
}
