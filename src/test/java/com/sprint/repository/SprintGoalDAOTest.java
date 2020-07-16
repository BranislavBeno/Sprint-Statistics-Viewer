package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.model.SprintGoal;
import com.sprint.repository.impl.SprintGoalDAO;

/**
 * The Class SprintGoalDAOTest.
 */
@SpringBootTest
@Testcontainers
class SprintGoalDAOTest extends DatabaseBaseTest {

	/** The sprint goal DAO. */
	@Autowired
	private SprintGoalDAO sprintGoalDAO;

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		sprintGoalDAO.setDataSource(dataSource());
	}

	/**
	 * Test getting jdbc template.
	 */
	@Test
	@DisplayName("Test whether getting instance of JDBC template is successfull")
	void testGettingJdbcTemplate() {
		JdbcTemplate jdbcTemplate = sprintGoalDAO.getJdbcTemplate();

		assertThat(jdbcTemplate).isNotNull();
	}

	/**
	 * Test getting sprint record by label.
	 */
	@Test
	@DisplayName("Test whether getting database row record by sprint label is successfull")
	void testGettingSprintRecordByLabel() {
		SprintGoal sprintGoal = sprintGoalDAO.getSprintByLabel("team_mango", "Sprint 2");

		assertThat(sprintGoal.getTeamName()).isEqualTo("Mango");
		assertThat(sprintGoal.getSprintGoals()).isEqualTo(
				"[\"Mango goal 8\", \"Mango goal 9\", \"Mango goal 10\", \"Mango goal 11\", \"Mango goal 12\"]");
	}

	/**
	 * Test getting sprint record by id.
	 */
	@Test
	@DisplayName("Test whether getting database row record by id is successfull")
	void testGettingSprintRecordById() {
		SprintGoal sprintGoal = sprintGoalDAO.getSprintById("team_apple", 1);

		assertThat(sprintGoal.getSprintLabel()).isEqualTo("Sprint 1");
	}
}
