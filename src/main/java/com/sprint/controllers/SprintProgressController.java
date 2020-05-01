/**
 * 
 */
package com.sprint.controllers;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.enums.ProgressState;
import com.sprint.model.SprintProgress;
import com.sprint.repository.SprintProgressDAO;

/**
 * The Class SprintProgressController.
 */
@Controller
public class SprintProgressController {

	/** The log. */
	private static Log log = LogFactory.getLog(SprintProgressController.class);

	/** The Constant TEAM_TABLE_PREFIX. */
	private static final String TEAM_TABLE_PREFIX = "team_";

	/** The Constant TIME_ELAPSED_COLUMN. */
	private static final String TIME_ELAPSED_COLUMN = "Time elapsed";

	/** The Constant TOTAL_COLUMN. */
	private static final String TOTAL_COLUMN = "Total";

	/** The sprints. */
	private SprintProgressDAO sprints;

	/**
	 * Instantiates a new sprint controller.
	 *
	 * @param dao the dao
	 */
	@Autowired
	public SprintProgressController(SprintProgressDAO dao) {
		this.sprints = dao;
	}

	/**
	 * Find sprint by label.
	 *
	 * @param label the label
	 * @return the list
	 */
	private List<SprintProgress> findSprintByLabel(final String label) {
		// Initialize list of teams
		List<SprintProgress> teams = null;
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
	 * Count remaining time.
	 *
	 * @param theSprints the the sprints
	 * @return the int
	 */
	private int countRemainingTime(final List<SprintProgress> theSprints) {
		// Get sprint ending date
		LocalDate end = theSprints.stream().map(SprintProgress::getSprintEnd).findFirst().orElseThrow();

		// Extend list with day count till end of sprint
		int count = LocalDate.now().until(end).getDays();

		// Handle period length
		if (count > 0) {
			// Exclude weekend days
			Long days = LocalDate.now().datesUntil(end.plusDays(1))
					.filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY)
					.count();
			count = days.intValue();
		} else {
			// If current date exceeds end of the sprint return 0
			count = 0;
		}

		return count;
	}

	/**
	 * Count spent time.
	 *
	 * @param sprints the sprints
	 * @return the int
	 */
	private int countSpentTime(final List<SprintProgress> sprints) {
		// Get sprint starting date
		LocalDate start = sprints.stream().map(SprintProgress::getSprintStart).findFirst().orElseThrow();

		// Extend list with day count from start of sprint without weekend days
		Long days = start.datesUntil(LocalDate.now())
				.filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY).count();
		int count = days.intValue();

		// Get sprint ending date
		LocalDate end = sprints.stream().map(SprintProgress::getSprintEnd).findFirst().orElseThrow();
		// Get sprint length without weekend days
		days = start.datesUntil(end.plusDays(1))
				.filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY && d.getDayOfWeek() != DayOfWeek.SUNDAY).count();
		int length = days.intValue();

		// If current date exceeds sprint length return sprint length
		if (count > length)
			count = length;

		// If sprint start exceeds current date return sprint 0
		if (count < 0)
			count = 0;
		return count;
	}

	/**
	 * Collect team names.
	 *
	 * @param sprints the sprints
	 * @return the list
	 */
	private List<String> collectTeamNames(final List<SprintProgress> sprints) {
		// Initialize list of team names
		List<String> list = new ArrayList<>();

		if (sprints != null) {
			// Get list of scrum team names
			list = sprints.stream().map(SprintProgress::getTeamName).collect(Collectors.toList());
		}

		list.addAll(List.of(TOTAL_COLUMN, TIME_ELAPSED_COLUMN));

		return list;
	}

	/**
	 * Collect to do story points list.
	 *
	 * @param theSprints the sprints
	 * @return the list
	 */
	private List<Integer> collectTodoSPList(final List<SprintProgress> theSprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of to do story points pro scrum team
			list = theSprints.stream().map(SprintProgress::getToDoStoryPointsSum).collect(Collectors.toList());

			// Extend list of to do story points with summary of all story points
			list.add(list.stream().collect(Collectors.summingInt(Integer::intValue)));

			// Add remaining time
			list.add(countRemainingTime(theSprints));
		}

		return list;
	}

	/**
	 * Collect in progress story points list.
	 *
	 * @param theSprints the sprints
	 * @return the list
	 */
	private List<Integer> collectInProgressSPList(final List<SprintProgress> theSprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of in progress story points pro scrum team
			list = theSprints.stream().map(SprintProgress::getInProgressStoryPointsSum).collect(Collectors.toList());

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
	 * @param theSprints the sprints
	 * @return the list
	 */
	private List<Integer> collectDoneSPList(final List<SprintProgress> theSprints) {
		// Initialize list of team related sprint data
		List<Integer> list = new ArrayList<>();

		if (theSprints != null) {
			// Get list of done story points pro scrum team
			list = theSprints.stream().map(SprintProgress::getFinishedStoryPointsSum).collect(Collectors.toList());

			// Extend list of done story points with summary of all story points
			list.add(list.stream().collect(Collectors.summingInt(Integer::intValue)));

			// Add spent time
			list.add(countSpentTime(theSprints));
		}

		return list;
	}

	/**
	 * Collect story points lists.
	 *
	 * @param sprintList the sprint list
	 * @return the map
	 */
	private Map<ProgressState, List<Integer>> collectSPLists(final List<SprintProgress> sprintList) {
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
		List<String> teams = sprints.getListOfTables().stream().filter(t -> t.startsWith(TEAM_TABLE_PREFIX))
				.collect(Collectors.toList());

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
	 * Compute data for sprint progress.
	 *
	 * @param model       the model
	 * @param sprintLabel the sprint label
	 * @throws SQLException the SQL exception
	 */
	private void computeDataForSprintProgress(Model model, String sprintLabel) throws SQLException {
		// Get list of sprint related team data
		List<SprintProgress> teamList = findSprintByLabel(sprintLabel);

		// In case that method parameter 'sprintLabel' is not compliant with data in
		// database,
		// last sprint related data record in database is chosen
		// In that case is sprint label updated
		// Subsequently time stamp about last data update is set
		String updated = "unknown";
		if (teamList != null) {
			sprintLabel = teamList.stream().findFirst().orElseThrow().getSprintLabel();
			// Set time stamp about last data update
			updated = teamList.stream().findFirst().orElseThrow().getUpdated()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		}

		// Collect story points lists
		Map<ProgressState, List<Integer>> spLists = collectSPLists(teamList);

		// Collect percentage lists
		Map<ProgressState, List<Integer>> percentageLists = collectPercentageLists(spLists);

		// Find sprint labels for all available team related sprint data
		Set<String> sprintSet = collectSprints();

		// Create model attributes for Thymeleaf template
		// Add sprint label
		model.addAttribute("mSprintLabel", sprintLabel);

		// Add updated time stamp
		model.addAttribute("mUpdated", updated);

		// Add list of sprints
		model.addAttribute("mSprintList", sprintSet);

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
	 * @param label the label
	 * @param model the model
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	@GetMapping("/sprintprogress")
	public String sprintsProgress(@RequestParam("sprint") String label, Model model) throws SQLException {
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
