package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.model.SprintKpi;
import com.sprint.repository.impl.SprintKpiDAO;

/**
 * The type Sprint kpi dao test.
 */
@SpringBootTest
@Testcontainers
class SprintKpiDAOTest extends DatabaseBaseTest {

	/** The sprint kpi DAO. */
	@Autowired
	private SprintKpiDAO sprintKpiDAO;

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		sprintKpiDAO.setDataSource(dataSource());
	}

	/**
	 * Test getting jdbc template.
	 */
	@Test
	@DisplayName("Test whether getting instance of JDBC template is successfull")
	void testGettingJdbcTemplate() {
		JdbcTemplate jdbcTemplate = sprintKpiDAO.getJdbcTemplate();

		assertThat(jdbcTemplate).isNotNull();
	}

	@Test
	@DisplayName("Test whether getting database row record by id is successfull")
	void testGettingSprintRecordById() {
		SprintKpi sprintProgress = sprintKpiDAO.getSprintById("team_apple", 2);

		assertThat(sprintProgress.getSprintLabel()).isEqualTo("Sprint 2");
		assertThat(sprintProgress.getUpdated()).isEqualTo(LocalDateTime.of(2020, 6, 2, 8, 5, 25));
		assertThat(sprintProgress.getClosedHighPriorStoriesSuccessRate()).isEqualTo(-1);
		assertThat(sprintProgress.getDeltaStoryPoints()).isEqualTo(0.3373);
	}

	@Test
	@DisplayName("Test whether getting database row record by sprint label is successfull")
	void testGettingSprintRecordByLabel() {
		SprintKpi sprintProgress = sprintKpiDAO.getSprintByLabel("team_mango", "Sprint 1");

		assertThat(sprintProgress.getTeamName()).isEqualTo("Mango");
		assertThat(sprintProgress.getNotClosedHighPriorStoriesCount()).isEqualTo(0);
		assertThat(sprintProgress.getPlannedStoryPointsClosed()).isEqualTo(1.2182);
	}
}
