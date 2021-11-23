package com.sprint.controllers;

import com.codeborne.selenide.Selenide;
import com.sprint.SprintStatsViewerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = SprintStatsViewerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
class SprintStatsErrorControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintStatsErrorControllerTest.class);

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        String url = WebBrowserInitializer.URL + port + "/error";
        WebBrowserInitializer.DRIVER.get(url);
    }

    @Test
    @DisplayName("Test whether page title is 'Error on sprint statistics'")
    void testPageTitle() {
        assertThat(Selenide.title()).isEqualTo("Error on sprint statistics");

        String screenshotPath = screenshot("error");
        LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
    }

    @Test
    @DisplayName("Test whether after button click will be page redirected to 'About' page")
    void testErrorPageButtonClick() {
        $(By.xpath("/html/body/a")).click();
        String caption = $(By.tagName("title")).getOwnText();

        assertThat(caption).isEqualTo("About");
    }
}
