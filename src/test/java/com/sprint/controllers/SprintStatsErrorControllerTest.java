package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sprint.extension.ScreenCaptureOnFailure;

/**
 * The Class SprintStatsErrorControllerTest.
 */
@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintStatsErrorControllerTest {

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
		container.getWebDriver().get("http://172.17.0.1:" + port + "/error");
	}

	/**
	 * Test about page title.
	 */
	@Test
	@DisplayName("Test whether page title is 'Error on sprint statistics'")
	void testAboutPageTitle() {
		// Get page title
		WebElement pageTitle = container.getWebDriver().findElementByTagName("title");
		// Assert expected and actual content
		assertThat(pageTitle.getAttribute("text")).isEqualTo("Error on sprint statistics");
	}
}
