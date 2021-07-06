package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ScreenCaptureOnFailure.class})
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamRefinementControllerTest extends DatabaseBaseTest {

  @Autowired
  private TeamRefinementDAO refinements;

  @Autowired
  private TeamVelocityDAO velocity;

  @Container
  public static final BrowserWebDriverContainer<?> webDriverContainer =
      new BrowserWebDriverContainer<>()
          .withCapabilities(new ChromeOptions()
              .addArguments("--no-sandbox")
              .addArguments("--disable-dev-shm-usage"));

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
      new ScreenShooterExtension().to("build/selenide");

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    // Load data from data source
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");

    // Read refinement specific data
    refinements.setDataSource(dataSource());
    // Read velocity specific data
    velocity.setDataSource(dataSource());

    Configuration.timeout = 2000;
    Configuration.baseUrl = "http://172.17.0.1:" + port;

    RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/apple/refinement");
  }

  @Test
  @DisplayName("Test whether page title is 'Team Apple refinement'")
  void testPageTitle() {
    String caption = $(By.tagName("title")).getOwnText();
    assertThat(caption).isEqualTo("Team Apple refinement");
  }
}
