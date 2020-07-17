package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.enums.FeatureScope;
import com.sprint.model.SprintRefinement;
import com.sprint.repository.impl.SprintRefinementDAO;
import com.sprint.utils.Utils;

/**
 * The Class SprintRefinementDAOTest.
 */
@SpringBootTest
@Testcontainers
class SprintRefinementDAOTest extends DatabaseBaseTest {

	/** The sprint refinement DAO. */
	@Autowired
	private SprintRefinementDAO sprintRefinementDAO;

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		// Initialize database
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_SPRINT_TABLE.sql");

		// Set data source
		sprintRefinementDAO.setDataSource(dataSource());
	}

	/**
	 * Test getting list of sprints.
	 */
	@Test
	@DisplayName("Test whether getting list of sprints is successfull")
	void testGettingListOfSprints() {
		// Get list of refinements
		List<SprintRefinement> list = sprintRefinementDAO.getRefinements();

		// Assert list size
		assertThat(list.size()).isEqualTo(4);

		// Get one refinement
		SprintRefinement refinement = list.get(3);

		// Get update time stamp
		String updated = Utils.convertTimeStampToString(refinement.getUpdated());

		// Assert update time stamp
		assertThat(updated).isEqualTo("2020-05-11 16:31");
		// Assert sprint label
		assertThat(refinement.getSprintLabel()).isEqualTo("Sprint 5");
		// Assert refined story points related to project phase
		assertThat(refinement.getRefinedStoryPoints().get(FeatureScope.BASIC)).isEqualTo(286);
		assertThat(refinement.getRefinedStoryPoints().get(FeatureScope.ADVANCED)).isEqualTo(0);
		assertThat(refinement.getRefinedStoryPoints().get(FeatureScope.COMMERCIAL)).isEqualTo(0);
		assertThat(refinement.getRefinedStoryPoints().get(FeatureScope.FUTURE)).isEqualTo(0);
	}
}
