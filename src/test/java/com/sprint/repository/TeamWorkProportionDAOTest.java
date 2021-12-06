package com.sprint.repository;

import com.sprint.config.RepositoryConfiguration;
import com.sprint.jdbc.TeamWorkProportionRowMapper;
import com.sprint.model.TeamWorkProportion;
import com.sprint.repository.impl.TeamWorkProportionDAO;
import com.sprint.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = RepositoryConfiguration.class)
class TeamWorkProportionDAOTest extends TeamDatabaseTest {

  @Autowired
  private TeamWorkProportionDAO teamWorkProportionDAO;

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
