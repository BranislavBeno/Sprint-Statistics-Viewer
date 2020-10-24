package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
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

import com.sprint.model.FeatureScope;
import com.sprint.jdbc.TeamScopeFocusRowMapper;
import com.sprint.model.TeamScopeFocus;
import com.sprint.repository.impl.TeamScopeFocusDAO;
import com.sprint.utils.Utils;

/**
 * The Class TeamScopeFocusDAOTest.
 */
@SpringBootTest
@Testcontainers
class TeamScopeFocusDAOTest extends DatabaseBaseTest {

	/** The team scope focus DAO. */
	@Autowired
	private TeamScopeFocusDAO teamScopeFocusDAO;

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		teamScopeFocusDAO.setDataSource(dataSource());
	}

	/**
	 * Test getting jdbc template.
	 */
	@Test
	@DisplayName("Test whether getting instance of JDBC template is successfull")
	void testGettingJdbcTemplate() {
		JdbcTemplate jdbcTemplate = teamScopeFocusDAO.getJdbcTemplate();

		assertThat(jdbcTemplate).isNotNull();
	}

	/**
	 * Test getting list of tables.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether getting list of team related database tables is successfull")
	void testGettingListOfTables() throws SQLException {
		List<String> list = teamScopeFocusDAO.getListOfTables();

		assertThat(list.size()).isEqualTo(2);
	}

	/**
	 * Test handling list of sprints.
	 */
	@Test
	@DisplayName("Test whether getting and handling list of sprints is successfull")
	void testHandlingListOfSprints() {
		// Get list of team related records
		List<TeamScopeFocus> sprints = teamScopeFocusDAO.getSprintList("team_apple", new TeamScopeFocusRowMapper());

		// Get last record from sprint related team data
		TeamScopeFocus scopeFocus = sprints.stream().reduce((first, second) -> second).orElse(new TeamScopeFocus());

		// Get time stamp of database item last update
		String updated = Utils.convertTimeStampToString(scopeFocus.getUpdated());

		// Get finished story points map
		Map<FeatureScope, Integer> finishedSP = scopeFocus.getFinishedStoryPoints();

		assertThat(updated).isEqualTo("2020-06-02 08:02");
		assertThat(scopeFocus.getTeamName()).isEqualTo("Apple");
		assertThat(scopeFocus.getSprintLabel()).isEqualTo("Sprint 1");
		assertThat(finishedSP.get(FeatureScope.ADVANCED)).isEqualTo(0);
		assertThat(finishedSP.get(FeatureScope.BASIC)).isEqualTo(54);
		assertThat(finishedSP.get(FeatureScope.COMMERCIAL)).isEqualTo(0);
		assertThat(finishedSP.get(FeatureScope.FUTURE)).isEqualTo(0);
	}
}
