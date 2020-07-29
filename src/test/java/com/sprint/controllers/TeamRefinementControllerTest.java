package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;
import com.sprint.repository.DatabaseBaseTest;
import com.sprint.repository.impl.TeamRefinementDAO;
import com.sprint.repository.impl.TeamVelocityDAO;

@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamRefinementControllerTest extends DatabaseBaseTest {

	@Autowired
	private TeamRefinementDAO refinements;

	@Autowired
	private TeamVelocityDAO velocity;

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

		// Read refinement specific data
		refinements.setDataSource(dataSource());

		// Read velocity specific data
		velocity.setDataSource(dataSource());

		// Get web driver's URL
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.get(ChromeBrowserInitializer.URL + port + "/apple/refinement");
	}

	@Test
	@DisplayName("Test whether page title is 'Team Apple refinement'")
	void testAboutPageTitle() {
		// Get page title
		String pageTitle = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver().getTitle();
		// Assert expected and actual content
		assertThat(pageTitle).isEqualTo("Team Apple refinement");
	}
}
