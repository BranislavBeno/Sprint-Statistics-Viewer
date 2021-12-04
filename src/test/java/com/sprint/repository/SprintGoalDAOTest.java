package com.sprint.repository;

import com.sprint.model.SprintGoal;
import com.sprint.repository.impl.SprintGoalDAO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class SprintGoalDAOTest extends DatabaseBaseTest {

  @Autowired
  private SprintGoalDAO sprintGoalDAO;

  @BeforeAll
  static void setUp() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
  }

  @AfterAll
  static void tearDown() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "DROP_TEAM_TABLE.sql");
  }

  @BeforeEach
  void setDataSource4Dao() {
    sprintGoalDAO.setDataSource(dataSource());
  }

  @Test
  @DisplayName("Test whether getting instance of JDBC template is successful")
  void testGettingJdbcTemplate() {
    JdbcTemplate jdbcTemplate = sprintGoalDAO.getJdbcTemplate();

    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  @DisplayName("Test whether getting database row record by sprint label is successful")
  void testGettingSprintRecordByLabel() {
    SprintGoal sprintGoal = sprintGoalDAO.getSprintByLabel("team_mango", "Sprint 2");

    assertThat(sprintGoal.getTeamName()).isEqualTo("Mango");
    assertThat(sprintGoal.getSprintGoals()).isEqualTo(
        "[\"Mango goal 8\", \"Mango goal 9\", \"Mango goal 10\", \"Mango goal 11\", \"Mango goal 12\"]");
  }

  @Test
  @DisplayName("Test whether getting database row record by id is successful")
  void testGettingSprintRecordById() {
    SprintGoal sprintGoal = sprintGoalDAO.getSprintById("team_apple", 1);

    assertThat(sprintGoal.getSprintLabel()).isEqualTo("Sprint 1");
  }
}
