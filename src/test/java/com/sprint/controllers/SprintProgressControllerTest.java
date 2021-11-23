package com.sprint.controllers;

import com.sprint.SprintStatsViewerApplication;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.SprintProgressDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = SprintStatsViewerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class SprintProgressControllerTest extends DatabaseBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintProgressControllerTest.class);

    @Autowired
    private SprintProgressDAO sprintProgressDAO;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
        sprintProgressDAO.setDataSource(dataSource());

        String url = WebBrowserInitializer.URL + port + "/sprintprogress?sprint=";
        WebBrowserInitializer.DRIVER.get(url);
    }

    @Test
    @DisplayName("Test whether model attributes are shown on web page")
    void testSprintsProgress() {
        String text = $(By.id("footerText")).getOwnText();
        assertThat(text).isEqualTo("Last update: 2020-06-02 08:05");

        String screenshotPath = screenshot("progress");
        LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
    }
}
