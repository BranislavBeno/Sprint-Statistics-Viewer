package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.SprintKpiDAO;

@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintKpiControllerTest extends DatabaseBaseTest {

	@Autowired
	private SprintKpiDAO kpis;

	/** The container. */
	@Container
	private BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>()
			.withCapabilities(new ChromeOptions()).withReuse(true);

	/** The port. */
	@LocalServerPort
	private int port;

	/**
	 * Gets the web driver url.
	 *
	 * @return the web driver url
	 */
	@BeforeEach
	private void getWebDriverUrl() {
		// Load data from data source
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		kpis.setDataSource(dataSource());

		// Get web driver's URL
		container.getWebDriver().get("http://172.17.0.1:" + port + "/kpi?sprint=");
	}

	@Test
	@DisplayName("Test whether page title is 'Sprint KPI's Sprint 2'")
	void testAboutPageTitle() {
		// Get page title
		WebElement pageTitle = container.getWebDriver().findElementByTagName("title");
		// Assert expected and actual content
		assertThat(pageTitle.getAttribute("text")).isEqualTo("Sprint KPI's Sprint 2");
	}

	@Test
	@DisplayName("Test whether third table column has title 'Team Mango'")
	void testHeaderElementsFromTable() {
		// Get list of web elements
		List<WebElement> webElementList = container.getWebDriver().findElementsByCssSelector("thead tr th");
		// Assert expected and actual content
		assertThat(webElementList.get(2).getText()).isEqualTo("Team Mango");
	}
}
