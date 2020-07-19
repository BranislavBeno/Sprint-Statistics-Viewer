package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.SprintKpiDAO;

/**
 * The Class SprintKpiControllerTest.
 */
@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintKpiControllerTest extends DatabaseBaseTest {

	/** The kpis. */
	@Autowired
	private SprintKpiDAO kpis;

	/** The port. */
	@LocalServerPort
	private int port;

	/**
	 * Prepare resources.
	 */
	@BeforeEach
	private void prepareResources() {
		// Load data from data source
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		kpis.setDataSource(dataSource());

		// Get web driver's URL
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver().get(ChromeBrowserInitializer.URL + port + "/kpi?sprint=");
	}

	/**
	 * Test about page title.
	 */
	@Test
	@DisplayName("Test whether page title is 'Sprint KPI's Sprint 2'")
	void testAboutPageTitle() {
		// Get page title
		WebElement pageTitle = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementByTagName("title");
		// Assert expected and actual content
		assertThat(pageTitle.getAttribute("text")).isEqualTo("Sprint KPI's Sprint 2");
	}

	/**
	 * Test header elements from table.
	 */
	@Test
	@DisplayName("Test whether third table column has title 'Team Mango'")
	void testHeaderElementsFromTable() {
		// Get list of web elements
		List<WebElement> webElementList = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementsByCssSelector("thead tr th");
		// Assert expected and actual content
		assertThat(webElementList.get(2).getText()).isEqualTo("Team Mango");
	}
}
