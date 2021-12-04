package com.sprint.repository;

import com.sprint.jdbc.TeamRefinementRowMapper;
import com.sprint.model.FeatureScope;
import com.sprint.model.TeamRefinement;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class TeamRefinementDAOTest extends DatabaseBaseTest {

  @Autowired
  private TeamRefinementDAO teamRefinementDAO;

  @Autowired
  private TeamVelocityDAO teamVelocityDAO;

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
    teamRefinementDAO.setDataSource(dataSource());
    teamVelocityDAO.setDataSource(dataSource());
  }

  @Test
  @DisplayName("Test whether getting instance of JDBC template from team refinement DAO is successful")
  void testGettingJdbcTemplateFromRefinementDao() {
    JdbcTemplate jdbcTemplate = teamRefinementDAO.getJdbcTemplate();

    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  @DisplayName("Test whether getting instance of JDBC template from team velocity DAO is successful")
  void testGettingJdbcTemplateFromVelocityDao() {
    JdbcTemplate jdbcTemplate = teamVelocityDAO.getJdbcTemplate();

    assertThat(jdbcTemplate).isNotNull();
  }

  @Test
  @DisplayName("Test whether putting non existing team name results into getting empty string instead of database table name")
  void testGettingDatabaseTableNameReturnsEmptyString() throws SQLException {
    String tableName = teamRefinementDAO.getTableName("banana");

    assertThat(tableName).isEmpty();
  }

  @Test
  @DisplayName("Test whether putting existing team name results into successful getting database table name")
  void testGettingDatabaseTableNameSucceeds() throws SQLException {
    String tableName = teamRefinementDAO.getTableName("mango");

    assertThat(tableName).isEqualTo("team_mango");
  }

  @Test
  @DisplayName("Test whether getting and handling list of sprints is successful")
  void testHandlingListOfSprints() {
    // Get list of team related records
    List<TeamRefinement> refinements = teamRefinementDAO.getCurrentSprint("team_mango",
        new TeamRefinementRowMapper());

    // Get refinement related team data
    TeamRefinement refinement = refinements.stream().findFirst().orElse(new TeamRefinement());

    // Get time stamp of database item last update
    String updated = Utils.convertTimeStampToString(refinement.getUpdated());

    // Get map team specific sprint related refinements
    Map<String, Map<FeatureScope, Integer>> refinedSP = refinement.getRefinedStoryPoints();

    assertThat(updated).isEqualTo("2020-06-02 08:05");
    assertThat(refinement.getTeamName()).isEqualTo("Mango");
    assertThat(refinedSP).hasSize(4);
  }
}
