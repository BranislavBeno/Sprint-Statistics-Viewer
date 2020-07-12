package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.enums.FeatureScope;
import com.sprint.jdbc.TeamRefinementRowMapper;
import com.sprint.model.TeamRefinement;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import com.sprint.utils.Utils;

@SpringBootTest
@Testcontainers
class TeamRefinementDAOTest extends DatabaseBaseTest {

	@Autowired
	private TeamRefinementDAO teamRefinementDAO;

	@Autowired
	private TeamVelocityDAO teamVelocityDAO;

	/** The table name. */
	String tableName = "team_mango";

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		teamRefinementDAO.setDataSource(dataSource());
		teamVelocityDAO.setDataSource(dataSource());
	}

	@Test
	@DisplayName("Test whether getting instance of JDBC template from team refinement DAO is successfull")
	void testGettingJdbcTemplateFromRefinementDao() {
		JdbcTemplate jdbcTemplate = teamRefinementDAO.getJdbcTemplate();

		assertThat(jdbcTemplate).isNotNull();
	}

	@Test
	@DisplayName("Test whether getting instance of JDBC template from team velocity DAO is successfull")
	void testGettingJdbcTemplateFromVelocityDao() {
		JdbcTemplate jdbcTemplate = teamVelocityDAO.getJdbcTemplate();

		assertThat(jdbcTemplate).isNotNull();
	}

	@Test
	@DisplayName("Test whether getting and handling list of sprints is successfull")
	void testHandlingListOfSprints() {
		// Get list of team related records
		List<TeamRefinement> refinements = teamRefinementDAO.getCurrentSprint(tableName, new TeamRefinementRowMapper());

		// Get refinement related team data
		TeamRefinement refinement = refinements.stream().findFirst().orElse(new TeamRefinement());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(refinement.getUpdated());

		// Get map team specific sprint related refinements
		Map<String, Map<FeatureScope, Integer>> refinedSP = refinement.getRefinedStoryPoints();

		assertThat(updated).isEqualTo("2020-06-02 08:05");
		assertThat(refinement.getTeamName()).isEqualTo("Mango");
		assertThat(refinedSP.size()).isEqualTo(4);
	}
}
