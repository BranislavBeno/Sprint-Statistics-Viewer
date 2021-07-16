package com.sprint.repository;

import com.sprint.model.SprintProgress;
import com.sprint.repository.impl.SprintProgressDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(DAOTestConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SprintProgressDAOTest extends DatabaseBaseTest {

  @Autowired
  private SprintProgressDAO sprintProgressDAO;

  private final String tableName = "team_mango";

  @BeforeEach
  void setDataSource4Dao() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
  }

  @Test
  @DisplayName("Test whether getting database row record by id is successful")
  void testGettingSprintRecordById() {
    SprintProgress sprintProgress = sprintProgressDAO.getSprintById("team_apple", 2);

    assertThat(sprintProgress.getTeamName()).isEqualTo("Apple");
    assertThat(sprintProgress.getUpdated())
        .isEqualToIgnoringHours(LocalDateTime.of(2020, 6, 2, 0, 0, 0));
    assertThat(sprintProgress.getSprintStart()).isEqualTo(LocalDate.of(2019, 4, 4));
    assertThat(sprintProgress.getSprintEnd()).isEqualTo(LocalDate.of(2019, 4, 24));
  }

  @Test
  @DisplayName("Test whether getting database row record by sprint label is successful")
  void testGettingSprintRecordByLabel() {
    SprintProgress sprintProgress = sprintProgressDAO.getSprintByLabel(tableName, "Sprint 1");

    assertThat(sprintProgress.getSprintLabel()).isEqualTo("Sprint 1");
    assertThat(sprintProgress.getFinishedStoryPointsSum()).isEqualTo(67);
    assertThat(sprintProgress.getInProgressStoryPointsSum()).isZero();
    assertThat(sprintProgress.getToDoStoryPointsSum()).isZero();
  }

  @Test
  @DisplayName("Test whether getting database list of tables is successful")
  void testGettingDatabaseListOfTables() throws SQLException {
    List<String> list = sprintProgressDAO.getListOfTables();

    assertThat(list.size()).isEqualTo(2);
  }

  @Test
  @DisplayName("Test whether getting database table row count is successful")
  void testGettingDatabaseTableRowCount() {
    int count = sprintProgressDAO.getRowCount(tableName);

    assertThat(count).isEqualTo(2);
  }

  @Test
  @DisplayName("Test whether getting list of sprints from particular database table is successful")
  void testGettingListOfSprints() throws SQLException {
    List<String> list = sprintProgressDAO.getSprintList(tableName);

    assertThat(list.size()).isEqualTo(2);
  }
}
