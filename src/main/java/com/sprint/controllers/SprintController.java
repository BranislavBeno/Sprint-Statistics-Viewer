/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sprint.model.Sprint;
import com.sprint.repository.SprintDAO;

/**
 * The Class SprintController.
 *
 * @author benito
 */
@Controller
public class SprintController {

	/** The log. */
	private static Log log = LogFactory.getLog(SprintController.class);

	/** The sprints. */
	private SprintDAO sprints;

	/**
	 * Instantiates a new sprint controller.
	 *
	 * @param dao the dao
	 */
	public SprintController(SprintDAO dao) {
		this.sprints = dao;
	}

	/**
	 * Current sprint label.
	 *
	 * @return the string
	 */
	private String currentSprintLabel() {
		// Get sprint label
		String sprintLabel = "";

		try {
			sprintLabel = sprints.getListOfTables().stream().filter(t -> t.startsWith("team_"))
					.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList()).get(0)
					.getSprintLabel();
		} catch (Exception e) {
			log.warn("No sprint label found.");
		}

		return sprintLabel;
	}

	private List<String> collectTeamNames(List<Sprint> sprints) {
		// Initialize list of team names
		List<String> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of to do story points pro scrum team
			list = sprints.stream().map(Sprint::getTeamName).collect(Collectors.toList());
		}

		list.addAll(List.of("Total", "Time elapsed"));

		return list;
	}

	/**
	 * Find sprint by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	private List<Sprint> findSprintByLabel(String label) {
		// Initialize list of trams
		List<Sprint> teams = null;
		try {
			teams = sprints.getListOfTables().stream().filter(t -> t.startsWith("team_"))
					.map(tn -> sprints.getSprintByLabel(tn, label)).collect(Collectors.toList());
		} catch (Exception e) {
			try {
				teams = sprints.getListOfTables().stream().filter(t -> t.startsWith("team_"))
						.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList());
			} catch (SQLException e1) {
				log.warn("No sprint data found.");
			}
		}

		return teams;
	}

	private List<Integer> collectTodoList(List<Sprint> sprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of to do story points pro scrum team
			list = sprints.stream().map(Sprint::getToDoStoryPointsSum).collect(Collectors.toList());

			// Extend list of to do story points with summary of all story points
			list.add(list.stream().collect(Collectors.summingInt(Integer::intValue)));

			// Get sprint ending date
			LocalDate end = sprints.stream().map(Sprint::getSprintEnd).findFirst().orElseThrow();
			// Extend list with day count till end of sprint
			int count = LocalDate.now().until(end).getDays();
			// If current date exceeds end of the sprint return 0
			if (count < 0)
				count = 0;
			list.add(count);
		}

		return list;
	}

	/**
	 * Sprints progress.
	 *
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/sprintprogress")
	public String sprintsProgress(Model model) throws SQLException {
		// Add model attributes for Thymeleaf template
		model.addAttribute("mSprintLabel", currentSprintLabel());
		model.addAttribute("mLabels", collectTeamNames(findSprintByLabel("")));
		model.addAttribute("mToDoSP", collectTodoList(findSprintByLabel("")));

		return "sprintprogress";
	}

	/**
	 * Tables.
	 *
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/tables")
	public String tables(Model model) throws SQLException {
		// Get list of database tables
		model.addAttribute("tableList", sprints.getListOfTables());

		return "tables";
	}
}
