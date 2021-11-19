package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AboutControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutControllerTest.class);

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        Configuration.baseUrl = WebBrowserInitializer.URL + port;

        open("/about");
    }

    @Test
    @DisplayName("Test whether page title is 'About'")
    void testPageTitle() {
        assertThat(Selenide.title()).isEqualTo("About");

        String screenshotPath = screenshot("about");
        LOGGER.info(() -> "Screenshot is available under %s".formatted(screenshotPath));
    }

    @Test
    @DisplayName("Test whether unordered web elements list has size 8")
    void testUnsortedWebElementsList() {
        List<WebElement> elementList = $(By.className("lead")).findElements(By.cssSelector("li"));
        assertThat(elementList.size()).isEqualTo(8);
    }
}
