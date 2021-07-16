package com.sprint.repository;

import com.sprint.model.FeatureScope;
import com.sprint.model.SprintRefinement;
import com.sprint.repository.impl.SprintRefinementDAO;
import com.sprint.utils.Utils;
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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(DAOTestConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SprintRefinementDAOTest extends DatabaseBaseTest {

  @Autowired
  private SprintRefinementDAO sprintRefinementDAO;

  @BeforeEach
  void setDataSource4Dao() {
    // Initialize database
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_SPRINT_TABLE.sql");
  }

  @Test
  @DisplayName("Test whether getting list of sprints is successful")
  void testGettingListOfSprints() {
    // Get list of refinements
    List<SprintRefinement> list = sprintRefinementDAO.getRefinements();

    // Assert list size
    assertThat(list).hasSize(4);

    // Get one refinement
    SprintRefinement refinement = list.get(3);

    // Get update time stamp
    String updated = Utils.convertTimeStampToString(refinement.getUpdated());

    // Assert update time stamp
    assertThat(updated).isEqualTo("2020-05-11 16:31");
    // Assert sprint label
    assertThat(refinement.getSprintLabel()).isEqualTo("Sprint 5");
    // Assert refined story points related to project phase
    assertThat(refinement.getRefinedStoryPoints()).isEqualTo(
        Map.of(
            FeatureScope.BASIC, 286,
            FeatureScope.ADVANCED, 0,
            FeatureScope.COMMERCIAL, 0,
            FeatureScope.FUTURE, 0
        )
    );
  }
}
