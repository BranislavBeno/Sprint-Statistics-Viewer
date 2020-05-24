package com.sprint.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SprintProgressDAOTest {

	String tableName = "team";

	@Mock
	JdbcTemplate jdbcTemplate;

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

	/*
	 * @Test void testPositiveDefaultMetodGetSprintListFromSprintDaoInterface()
	 * throws SQLException { SprintProgressDAO sprintProgressDAO = prepareDAO();
	 * Mockito.when(jdbcTemplate.query("select sprint from " + tableName, new
	 * SingleColumnRowMapper<>(String.class))) .thenReturn(List.of("Sprint 1",
	 * "Sprint 2"));
	 * 
	 * assertThat(sprintProgressDAO.getSprintList(tableName)).isNotEmpty(); }
	 */}
