package com.sprint.controllers;

import com.codeborne.selenide.Selenide;
import com.sprint.SprintStatsViewerApplication;
import com.sprint.repository.TeamDatabaseTest;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = SprintStatsViewerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class TeamRefinementControllerTest extends TeamDatabaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRefinementControllerTest.class);

    @Autowired
    private TeamRefinementDAO refinements;
    @Autowired
    private TeamVelocityDAO velocity;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        String url = WebBrowserInitializer.URL + port + "/apple/refinement";
        WebBrowserInitializer.DRIVER.get(url);
    }

    @Test
    @DisplayName("Test whether page title is 'Team Apple refinement'")
    void testPageTitle() {
        assertThat(Selenide.title()).isEqualTo("Team Apple refinement");

        String screenshotPath = screenshot("refinement");
        LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
    }
}
