package com.sprint.repository;

import com.sprint.model.SprintKpi;
import com.sprint.repository.impl.SprintKpiDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(DAOTestConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SprintKpiDAOTest extends DatabaseBaseTest {

  @Autowired
  private SprintKpiDAO sprintKpiDAO;

  @BeforeEach
  void setDataSource4Dao() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
  }

  @Test
  @DisplayName("Test whether getting instance of JDBC template is successful")
  void testGettingJdbcTemplate() {
    JdbcTemplate jdbcTemplate = sprintKpiDAO.getJdbcTemplate();

    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  @DisplayName("Test whether getting database row record by id is successful")
  void testGettingSprintRecordById() {
    SprintKpi sprintKpis = sprintKpiDAO.getSprintById("team_apple", 2);

    assertThat(sprintKpis.getSprintLabel()).isEqualTo("Sprint 2");
    assertThat(sprintKpis.getUpdated())
        .isEqualToIgnoringHours(LocalDateTime.of(2020, 6, 2, 0, 0, 0));
    assertThat(sprintKpis.getClosedHighPriorStoriesSuccessRate()).isEqualTo(-1);
    assertThat(sprintKpis.getDeltaStoryPoints()).isEqualTo(0.3373);
  }

  @Test
  @DisplayName("Test whether getting database row record by sprint label is successful")
  void testGettingSprintRecordByLabel() {
    SprintKpi sprintKpis = sprintKpiDAO.getSprintByLabel("team_mango", "Sprint 1");

    assertThat(sprintKpis.getTeamName()).isEqualTo("Mango");
    assertThat(sprintKpis.getNotClosedHighPriorStoriesCount()).isZero();
    assertThat(sprintKpis.getPlannedStoryPointsClosed()).isEqualTo(1.2182);
  }
}
