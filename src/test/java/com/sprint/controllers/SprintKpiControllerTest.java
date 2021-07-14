package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.SprintKpiDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ScreenCaptureOnFailure.class})
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintKpiControllerTest extends DatabaseBaseTest {

  @Autowired
  private SprintKpiDAO kpis;

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
      new ScreenShooterExtension().to("build/selenide");

  @LocalServerPort
  private Integer port;

  @BeforeEach
  void setUp() {
    ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
    kpis.setDataSource(dataSource());

    Configuration.timeout = WebBrowserInitializer.TIMEOUT;
    Configuration.baseUrl = WebBrowserInitializer.URL + port;
    Configuration.browser = WebBrowserInitializer.BROWSER;

    RemoteWebDriver remoteWebDriver = WebBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/kpi?sprint=");
  }

  @Test
  @DisplayName("Test whether page title is 'Sprint KPI's Sprint 2'")
  void testPageTitle() {
    String caption = $(By.tagName("title")).getOwnText();
    assertThat(caption).isEqualTo("Sprint KPI's Sprint 2");
  }

  @Test
  @DisplayName("Test whether third table column has title 'Team Mango'")
  void testHeaderElementsFromTable() {
    List<WebElement> elementList = $(By.cssSelector("thead tr")).findElements(By.cssSelector("th"));
    assertThat(elementList.get(2).getText()).isEqualTo("Team Mango");
  }
}
