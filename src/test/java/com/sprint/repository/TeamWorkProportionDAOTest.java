package com.sprint.repository;

import com.sprint.jdbc.TeamWorkProportionRowMapper;
import com.sprint.model.TeamWorkProportion;
import com.sprint.repository.impl.TeamWorkProportionDAO;
import com.sprint.utils.Utils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class TeamWorkProportionDAOTest extends DatabaseBaseTest {

  @Autowired
  private TeamWorkProportionDAO teamWorkProportionDAO;

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
    teamWorkProportionDAO.setDataSource(dataSource());
  }

  @Test
  @DisplayName("Test whether getting instance of JDBC template is successful")
  void testGettingJdbcTemplate() {
    JdbcTemplate jdbcTemplate = teamWorkProportionDAO.getJdbcTemplate();

    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  @DisplayName("Test whether getting and handling list of sprints is successful")
  void testHandlingListOfSprints() {
    // Get list of team related records
    List<TeamWorkProportion> sprints = teamWorkProportionDAO.getSprintList("team_mango",
        new TeamWorkProportionRowMapper());

    // Get last record from sprint related team data
    TeamWorkProportion velocity = sprints.stream().reduce((first, second) -> second)
        .orElse(new TeamWorkProportion());

    // Get time stamp of database item last update
    String updated = Utils.convertTimeStampToString(velocity.getUpdated());

    assertThat(updated).isEqualTo("2020-06-02 08:02");
    assertThat(velocity.getTeamName()).isEqualTo("Mango");
    assertThat(velocity.getSprintLabel()).isEqualTo("Sprint 1");
    assertThat(velocity.getFinishedStoriesSP()).isEqualTo(65);
    assertThat(velocity.getFinishedBugsSP()).isEqualTo(2);
  }
}
