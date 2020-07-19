package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;

/**
 * The Class SprintStatsErrorControllerTest.
 */
@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintStatsErrorControllerTest {

	/** The port. */
	@LocalServerPort
	private int port;

	/**
	 * Prepare resources.
	 */
	@BeforeEach
	private void prepareResources() {
		ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver().get(ChromeBrowserInitializer.URL + port + "/error");
	}

	/**
	 * Test about page title.
	 */
	@Test
	@DisplayName("Test whether page title is 'Error on sprint statistics'")
	void testErrorPageTitle() {
		// Get page title
		WebElement pageTitle = ChromeBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver()
				.findElementByTagName("title");
		// Assert expected and actual content
		assertThat(pageTitle.getAttribute("text")).isEqualTo("Error on sprint statistics");
	}
}
