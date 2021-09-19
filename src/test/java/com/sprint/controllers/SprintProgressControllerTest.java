package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.SprintProgressDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ScreenCaptureOnFailure.class})
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintProgressControllerTest extends DatabaseBaseTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(SprintProgressControllerTest.class);

  @Autowired
  private SprintProgressDAO sprintProgressDAO;

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
      new ScreenShooterExtension().to("build/selenide");

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
    sprintProgressDAO.setDataSource(dataSource());

    Configuration.timeout = WebBrowserInitializer.TIMEOUT;
    Configuration.baseUrl = WebBrowserInitializer.URL + port;
    Configuration.browser = WebBrowserInitializer.BROWSER;

    RemoteWebDriver remoteWebDriver = WebBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/sprintprogress?sprint=");
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
