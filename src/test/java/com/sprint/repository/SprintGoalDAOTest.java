package com.sprint.repository;

import com.sprint.config.RepositoryConfiguration;
import com.sprint.model.SprintGoal;
import com.sprint.repository.impl.SprintGoalDAO;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = RepositoryConfiguration.class)
class SprintGoalDAOTest extends TeamDatabaseTest implements WithAssertions {

  @Autowired
  private SprintGoalDAO sprintGoalDAO;

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
