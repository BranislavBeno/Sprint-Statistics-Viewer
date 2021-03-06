package com.sprint.repository;

import com.sprint.model.TeamVelocity;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class TeamVelocityDAOTest extends DatabaseBaseTest {

  @Autowired
  private TeamVelocityDAO teamVelocityDAO;

  @BeforeEach
  private void setDataSource4Dao() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
    teamVelocityDAO.setDataSource(dataSource());
  }

  @Test
  @DisplayName("Test whether getting list of sprints from particular database table is successful")
  void testGettingListOfSprints() {
    List<TeamVelocity> list = teamVelocityDAO.getFullSprintList(("team_mango"));

    TeamVelocity teamVelocity = list.get(0);

    String updated = Utils.convertTimeStampToString(teamVelocity.getUpdated());

    assertThat(updated).isEqualTo("2020-06-02 08:02");
    assertThat(teamVelocity.getSprintLabel()).isEqualTo("Sprint 1");
    assertThat(teamVelocity.getTeamName()).isEqualTo("Mango");
    assertThat(teamVelocity.getTeamMemberCount()).isEqualTo(8);
    assertThat(teamVelocity.getFinishedStoryPointsSum()).isEqualTo(67);
    assertThat(teamVelocity.getOnBeginPlannedStoryPointsSum()).isEqualTo(55);
    assertThat(teamVelocity.getOnEndPlannedStoryPointsSum()).isEqualTo(67);
  }
}
