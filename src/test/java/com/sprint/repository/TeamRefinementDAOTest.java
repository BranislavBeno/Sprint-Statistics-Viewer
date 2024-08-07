package com.sprint.repository;

import com.sprint.config.RepositoryConfiguration;
import com.sprint.jdbc.TeamRefinementRowMapper;
import com.sprint.model.FeatureScope;
import com.sprint.model.TeamRefinement;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

@Testcontainers(disabledWithoutDocker = true)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = RepositoryConfiguration.class)
class TeamRefinementDAOTest extends TeamDatabaseTest implements WithAssertions {

    @Autowired
    private TeamRefinementDAO teamRefinementDAO;

    @Autowired
    private TeamVelocityDAO teamVelocityDAO;

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
    void testGettingDatabaseTableNameReturnsEmptyString() {
        String tableName = teamRefinementDAO.getTableName("banana");

        assertThat(tableName).isEmpty();
    }

    @Test
    @DisplayName("Test whether putting existing team name results into successful getting database table name")
    void testGettingDatabaseTableNameSucceeds() {
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
