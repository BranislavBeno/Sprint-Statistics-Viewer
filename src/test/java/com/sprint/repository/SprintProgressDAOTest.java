package com.sprint.repository;

import com.sprint.config.RepositoryConfiguration;
import com.sprint.model.SprintProgress;
import com.sprint.repository.impl.SprintProgressDAO;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Testcontainers(disabledWithoutDocker = true)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = RepositoryConfiguration.class)
class SprintProgressDAOTest extends TeamDatabaseTest implements WithAssertions {

    private static final String TABLE_NAME = "team_mango";

    @Autowired
    private SprintProgressDAO sprintProgressDAO;

    @Test
    @DisplayName("Test whether getting database row record by id is successful")
    void testGettingSprintRecordById() {
        SprintProgress sprintProgress = sprintProgressDAO.getSprintById("team_apple", 2);

        assertThat(sprintProgress.getTeamName()).isEqualTo("Apple");
        assertThat(sprintProgress.getUpdated())
                .isEqualToIgnoringHours(LocalDateTime.of(2020, 6, 2, 0, 0, 0));
        assertThat(sprintProgress.getSprintStart()).isEqualTo(LocalDate.of(2019, 4, 4));
        assertThat(sprintProgress.getSprintEnd()).isEqualTo(LocalDate.of(2019, 4, 24));
    }

    @Test
    @DisplayName("Test whether getting database row record by sprint label is successful")
    void testGettingSprintRecordByLabel() {
        SprintProgress sprintProgress = sprintProgressDAO.getSprintByLabel(TABLE_NAME, "Sprint 1");

        assertThat(sprintProgress.getSprintLabel()).isEqualTo("Sprint 1");
        assertThat(sprintProgress.getFinishedStoryPointsSum()).isEqualTo(67);
        assertThat(sprintProgress.getInProgressStoryPointsSum()).isZero();
        assertThat(sprintProgress.getToDoStoryPointsSum()).isZero();
    }

    @Test
    @DisplayName("Test whether getting database list of tables is successful")
    void testGettingDatabaseListOfTables() {
        List<String> list = sprintProgressDAO.getListOfTables();

        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("Test whether getting database table row count is successful")
    void testGettingDatabaseTableRowCount() {
        int count = sprintProgressDAO.getRowCount(TABLE_NAME).orElse(0);

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Test whether getting list of sprints from particular database table is successful")
    void testGettingListOfSprints() {
        List<String> list = sprintProgressDAO.getSprintList(TABLE_NAME);

        assertThat(list).hasSize(2);
    }
}
