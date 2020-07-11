package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.model.SprintProgress;
import com.sprint.repository.impl.SprintProgressDAO;

/**
 * The Class SprintProgressDAOTest.
 */
@SpringBootTest
@Testcontainers
class SprintProgressDAOTest extends DatabaseBaseTest {

	/** The sprint progress DAO. */
	@Autowired
	private SprintProgressDAO sprintProgressDAO;

	/** The table name. */
	String tableName = "team_mango";

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		sprintProgressDAO.setDataSource(dataSource());
	}

	/**
	 * Test getting sprint record by id.
	 */
	@Test
	@DisplayName("Test whether getting database row record by id is successfull")
	void testGettingSprintRecordById() {
		SprintProgress sprintProgress = sprintProgressDAO.getSprintById("team_apple", 2);

		assertThat(sprintProgress.getTeamName()).isEqualTo("Apple");
	}

	/**
	 * Test getting sprint record by label.
	 */
	@Test
	@DisplayName("Test whether getting database row record by sprint label is successfull")
	void testGettingSprintRecordByLabel() {
		SprintProgress sprintProgress = sprintProgressDAO.getSprintByLabel(tableName, "Sprint 1");

		assertThat(sprintProgress.getFinishedStoryPointsSum()).isEqualTo(67);
	}

	/**
	 * Test getting database list of tables.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether getting database list of tables is successfull")
	void testGettingDatabaseListOfTables() throws SQLException {
		List<String> list = sprintProgressDAO.getListOfTables();

		assertThat(list.size()).isEqualTo(2);
	}

	/**
	 * Test getting database table row count.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether getting database table row count is successfull")
	void testGettingDatabaseTableRowCount() throws SQLException {
		int count = sprintProgressDAO.getRowCount(tableName);

		assertThat(count).isEqualTo(2);
	}

	@Test
	@DisplayName("Test whether getting list of sprints from particular database table is successfull")
	void testGettingListOfSprints() throws SQLException {
		List<String> list = sprintProgressDAO.getSprintList(tableName);

		assertThat(list.size()).isEqualTo(2);
	}
}
