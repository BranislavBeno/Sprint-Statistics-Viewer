package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.sprint.repository.impl.SprintProgressDAO;

/**
 * The Class SprintProgressControllerTest.
 */
@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintProgressControllerTest extends DatabaseBaseTest {

	/** The sprint progress DAO. */
	@Autowired
	private SprintProgressDAO sprintProgressDAO;

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
		sprintProgressDAO.setDataSource(dataSource());

		// Get web driver's URL
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.get(ChromeBrowserInitializer.URL + port + "/sprintprogress?sprint=");
	}

	/**
	 * Test sprints progress.
	 */
	@Test
	@DisplayName("Test whether model attributes are shown on web page")
	void testSprintsProgress() {
		// Get web element
		WebElement webElement = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementById("footerText");

		// Assert expected and actual content
		assertThat(webElement.getText()).isEqualTo("Last update: 2020-06-02 08:05");
	}
}
