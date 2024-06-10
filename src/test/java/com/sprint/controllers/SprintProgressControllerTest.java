package com.sprint.controllers;

import com.codeborne.selenide.Selenide;
import com.sprint.SprintStatsViewerApplication;
import com.sprint.repository.TeamDatabaseTest;
import com.sprint.repository.impl.SprintProgressDAO;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = SprintStatsViewerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class SprintProgressControllerTest extends TeamDatabaseTest implements WithAssertions {

  private static final Logger LOGGER = LoggerFactory.getLogger(SprintProgressControllerTest.class);

  @Autowired
  private SprintProgressDAO sprintProgressDAO;

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    String url = WebBrowserInitializer.URL + port + "/sprintprogress?sprint=";
    WebBrowserInitializer.DRIVER.get(url);
  }

  @Test
  @DisplayName("Test whether model attributes are shown on web page")
  void testSprintsProgress() {
    String text = Selenide.$(By.id("footerText")).getOwnText();
    assertThat(text).isEqualTo("Last update: 2020-06-02 08:05");

    String screenshotPath = Selenide.screenshot("progress");
    LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
  }
}
