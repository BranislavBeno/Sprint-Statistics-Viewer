package com.sprint.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	/** The table name. */
	String tableName = "team_mango";

	/**
	 * Sets the data source for DAO.
	 */
	@BeforeEach
	private void setDataSource4Dao() {
		ScriptUtils.runInitScript(new JdbcDatabaseDelegate(DATABASE, ""), "CREATE_AND_INITIALIZE_TEAM_TABLE.sql");
		sprintProgressDAO.setDataSource(dataSource());
	}

	/** The port. */
	@LocalServerPort
	private int port;

	/** The container. */
	@Container
	private BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>()
			.withCapabilities(new ChromeOptions());

	/**
	 * Test sprints progress.
	 */
	@Test
	@DisplayName("Test whether model attributes are shown on web page")
	void testSprintsProgress() {
		// Get test container web driver
		container.getWebDriver().get("http://172.17.0.1:" + port + "/sprintprogress?sprint=");

		// Get web element
		WebElement webElement = this.container.getWebDriver().findElementById("footerText");

		// Assert expected and actual content
		assertEquals("Last update: 2020-06-02 08:05", webElement.getText());
	}
}
