package com.sprint.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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

@Testcontainers
@ExtendWith({ ScreenCaptureOnFailure.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AboutControllerTest {

	/** The container. */
	@Container
	private BrowserWebDriverContainer<?> container = new BrowserWebDriverContainer<>()
			.withCapabilities(new ChromeOptions()).withReuse(true);

	/** The port. */
	@LocalServerPort
	private int port;

	@BeforeEach
	private void getWebDriverUrl() {
		container.getWebDriver().get("http://172.17.0.1:" + port + "/about");
	}

	@Test
	@DisplayName("Test whether page title is 'About'")
	void testAboutPageTitle() {
		// Get page title
		WebElement pageTitle = container.getWebDriver().findElementByTagName("title");
		// Assert expected and actual content
		assertThat(pageTitle.getAttribute("text")).isEqualTo("About");
	}

	@Test
	@DisplayName("Test whether unordered list has size 8")
	void testUnsortedL() {
		// Get list of web elements
		List<WebElement> webElementList = container.getWebDriver().findElementsByCssSelector(".lead li");
		// Assert expected and actual content
		assertThat(webElementList.size()).isEqualTo(8);
	}
}
