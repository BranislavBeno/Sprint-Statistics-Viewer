package com.sprint.controllers;

import com.codeborne.selenide.Selenide;
import com.sprint.SprintStatsViewerApplication;
import com.sprint.repository.TeamDatabaseTest;
import com.sprint.repository.impl.SprintKpiDAO;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = SprintStatsViewerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class SprintKpiControllerTest extends TeamDatabaseTest implements WithAssertions {

  private static final Logger LOGGER = LoggerFactory.getLogger(SprintKpiControllerTest.class);

  @Autowired
  private SprintKpiDAO kpis;

  @LocalServerPort
  private Integer port;

  @BeforeEach
  void setUp() {
    String url = WebBrowserInitializer.URL + port + "/kpi?sprint=";
    WebBrowserInitializer.DRIVER.get(url);
  }

  @Test
  @DisplayName("Test whether page title is 'Sprint KPI's Sprint 2'")
  void testPageTitle() {
    assertThat(Selenide.title()).isEqualTo("Sprint KPI's Sprint 2");

    String screenshotPath = Selenide.screenshot("kpi");
    LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
  }

  @Test
  @DisplayName("Test whether third table column has title 'Team Mango'")
  void testHeaderElementsFromTable() {
    List<WebElement> elementList = Selenide.$(By.cssSelector("thead tr")).findElements(By.cssSelector("th"));
    assertThat(elementList.get(2).getText()).isEqualTo("Team Mango");
  }
}
