package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sprint.model.SprintProgress;
import com.sprint.repository.impl.SprintProgressDAO;

@ExtendWith(MockitoExtension.class)
@Testcontainers
class SprintProgressDAOTest {

	@Container
	private MySQLContainer<?> database = new MySQLContainer<>();

	private SprintProgressDAO sprintProgressDAO;

	String tableName = "team";

	@Mock
	JdbcTemplate jdbcTemplate;

	@NotNull
	private DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(database.getJdbcUrl());
		dataSource.setUser(database.getUsername());
		dataSource.setPassword(database.getPassword());
		return dataSource;
	}

	private SprintProgressDAO prepareDAO() {
		SprintProgressDAO sprintProgressDAO = new SprintProgressDAO();
		ReflectionTestUtils.setField(sprintProgressDAO, "jdbcTemplate", jdbcTemplate);
		return sprintProgressDAO;
	}

	@Test
	void testPositiveDefaultMetodGetRowCountFromSprintDaoInterface() {
		SprintProgressDAO sprintProgressDAO = prepareDAO();
		Mockito.when(jdbcTemplate.queryForObject("select count(*) from " + tableName, Integer.class)).thenReturn(4);

		assertThat(sprintProgressDAO.getRowCount(tableName)).isEqualTo(4);
	}

	@Test
	void testInteractionWithDatabase() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(database, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		sprintProgressDAO = new SprintProgressDAO();
		sprintProgressDAO.setDataSource(dataSource());

		SprintProgress sprintProgress = sprintProgressDAO.getSprintByLabel(tableName, "Sprint 1");

		assertThat(sprintProgress).isNotNull();
	}
}
