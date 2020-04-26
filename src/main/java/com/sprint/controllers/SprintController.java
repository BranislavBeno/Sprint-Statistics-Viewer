/**
 * 
 */
package com.sprint.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.enums.ProgressState;
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

	private static final String TEAM_TABLE_PREFIX = "team_";

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
			sprintLabel = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
					.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList()).get(0)
					.getSprintLabel();
		} catch (Exception e) {
			log.warn("No sprint label found.");
		}

		return sprintLabel;
	}

	/**
	 * Find sprint by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	private List<Sprint> findSprintByLabel(final String label) {
		// Initialize list of trams
		List<Sprint> teams = null;
		try {
			teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
					.map(tn -> sprints.getSprintByLabel(tn, label)).collect(Collectors.toList());
		} catch (Exception e) {
			try {
				teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
						.map(tn -> sprints.getSprintById(tn, sprints.getRowCount(tn))).collect(Collectors.toList());
			} catch (SQLException e1) {
				log.warn("No sprint data found.");
			}
		}

		return teams;
	}

	/**
	 * Collect team names.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectTeamNames(final List<Sprint> sprints) {
		// Initialize list of team names
		List<String> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of scrum team names
			list = sprints.stream().map(Sprint::getTeamName).collect(Collectors.toList());
		}

		list.addAll(List.of("Total", "Time elapsed"));

		return list;
	}

	/**
	 * Collect to do story points list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectTodoSPList(final List<Sprint> sprints) {
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
			// Add remaining time
			list.add(count);
		}

		return list;
	}

	/**
	 * Collect in progress story points list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectInProgressSPList(final List<Sprint> sprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of in progress story points pro scrum team
			list = sprints.stream().map(Sprint::getInProgressStoryPointsSum).collect(Collectors.toList());

			// Extend list of in progress story points with summary of all story points
			list.add(list.stream().collect(Collectors.summingInt(Integer::intValue)));

			// Add zero for in progress category
			list.add(0);
		}

		return list;
	}

	/**
	 * Collect done story points list.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<Integer> collectDoneSPList(final List<Sprint> sprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of done story points pro scrum team
			list = sprints.stream().map(Sprint::getFinishedStoryPointsSum).collect(Collectors.toList());

			// Extend list of done story points with summary of all story points
			list.add(list.stream().collect(Collectors.summingInt(Integer::intValue)));

			// Get sprint starting date
			LocalDate start = sprints.stream().map(Sprint::getSprintStart).findFirst().orElseThrow();
			// Extend list with day count from start of sprint
			int count = start.until(LocalDate.now()).getDays();

			// Get sprint ending date
			LocalDate end = sprints.stream().map(Sprint::getSprintEnd).findFirst().orElseThrow();
			// Get sprint length
			int length = start.until(end).getDays();

			// If current date exceeds sprint length return sprint length
			if (count > length)
				count = length;

			// If sprint start exceeds current date return sprint 0
			if (count < 0)
				count = 0;

			// Add spent time
			list.add(count);
		}

		return list;
	}

	/**
	 * Collect story points lists.
	 *
	 * @param sprintList the sprint list
	 * @return the map
	 */
	private Map<ProgressState, List<Integer>> collectSPLists(final List<Sprint> sprintList) {
		// Initialize collection of story points lists
		Map<ProgressState, List<Integer>> collectedSP = new EnumMap<>(ProgressState.class);

		// Add to do story points list
		collectedSP.put(ProgressState.TO_DO, collectTodoSPList(sprintList));

		// Add in progress story points list
		collectedSP.put(ProgressState.IN_PROGRESS, collectInProgressSPList(sprintList));

		// Add done story points list
		collectedSP.put(ProgressState.DONE, collectDoneSPList(sprintList));

		return collectedSP;
	}

	/**
	 * Collect percentage lists.
	 *
	 * @param spLists the sp lists
	 * @return the map
	 */
	private Map<ProgressState, List<Integer>> collectPercentageLists(final Map<ProgressState, List<Integer>> spLists) {
		// Initialize collection of percentage lists
		Map<ProgressState, List<Integer>> collectedPercentage = new EnumMap<>(ProgressState.class);

		// Initialize particular percentage lists
		List<Integer> toDoList = new ArrayList<>();
		List<Integer> inProgressList = new ArrayList<>();
		List<Integer> doneList = new ArrayList<>();

		// Count length of first list
		int count = spLists.get(ProgressState.TO_DO).size();

		// Fill particular percentage lists
		for (int i = 0; i < count; i++) {
			// Get to do value
			int toDo = spLists.get(ProgressState.TO_DO).get(i);
			// Get in progress value
			int inProgress = spLists.get(ProgressState.IN_PROGRESS).get(i);
			// Get done value
			int done = spLists.get(ProgressState.DONE).get(i);
			// Sum of particular states
			int sum = toDo + inProgress + done;

			// Compute to do percentage
			Long toDoPercentage = Math.round((double) toDo / sum * 100);
			// Compute in progress percentage
			Long inProgressPercentage = Math.round((double) inProgress / sum * 100);
			// Compute done percentage
			int donePercentage = 100 - toDoPercentage.intValue() - inProgressPercentage.intValue();

			// Fill particular percentage lists
			toDoList.add(toDoPercentage.intValue());
			inProgressList.add(inProgressPercentage.intValue());
			doneList.add(donePercentage);
		}

		// Add percentage lists to resulting map
		collectedPercentage.put(ProgressState.TO_DO, toDoList);
		collectedPercentage.put(ProgressState.IN_PROGRESS, inProgressList);
		collectedPercentage.put(ProgressState.DONE, doneList);

		return collectedPercentage;
	}

	private Set<String> collectSprints() throws SQLException {
		// Initialize empty set of sprints
		Set<String> sprintSet = new TreeSet<>();

		List<String> teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX)).collect(Collectors.toList());

		String name = teams.get(0);
		ResultSet set = sprints.getSprints(name);
		
		return sprintSet;
	}

	/**
	 * Compute data for sprint progress.
	 *
	 * @param model       the model
	 * @param sprintLabel the sprint label
	 * @throws SQLException
	 */
	private void computeDataForSprintProgress(Model model, String sprintLabel) throws SQLException {
		// Get list of sprint related team data
		List<Sprint> teamList = findSprintByLabel(sprintLabel);

		//Set<String> sprints = collectSprints();

		// Refresh sprint label in case, that desired sprint wasn't found in database
		if (teamList != null)
			sprintLabel = teamList.stream().findFirst().orElseThrow().getSprintLabel();

		// Collect story points lists
		Map<ProgressState, List<Integer>> spLists = collectSPLists(teamList);

		// Collect percentage lists
		Map<ProgressState, List<Integer>> percentageLists = collectPercentageLists(spLists);

		// Create model attributes for Thymeleaf template
		// Add sprint label
		model.addAttribute("mSprintLabel", sprintLabel);

		// Add graph labels
		model.addAttribute("mLabels", collectTeamNames(teamList));

		// Add to do story points list
		model.addAttribute("mToDoSP", spLists.get(ProgressState.TO_DO));
		// Add to do percentage list
		model.addAttribute("mToDoPercentage", percentageLists.get(ProgressState.TO_DO));

		// Add in progress story points list
		model.addAttribute("mInProgressSP", spLists.get(ProgressState.IN_PROGRESS));
		// Add in progress percentage list
		model.addAttribute("mInProgressPercentage", percentageLists.get(ProgressState.IN_PROGRESS));

		// Add done story points list
		model.addAttribute("mDoneSP", spLists.get(ProgressState.DONE));
		model.addAttribute("mDonePercentage", percentageLists.get(ProgressState.DONE));
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
		// Get string label
		String sprintLabel = currentSprintLabel();

		computeDataForSprintProgress(model, sprintLabel);

		return "sprintprogress";
	}

	/**
	 * Sprints progress for.
	 *
	 * @param label the label
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/sprintprogressfor")
	public String sprintsProgressFor(@RequestParam("sprint") String label, Model model) throws SQLException {
		// Get string label
		String sprintLabel = label;

		computeDataForSprintProgress(model, sprintLabel);

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
