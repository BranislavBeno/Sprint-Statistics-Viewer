package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.sprint.extension.ScreenCaptureOnFailure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({ScreenCaptureOnFailure.class})
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SprintStatsErrorControllerTest {

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
      new ScreenShooterExtension().to("build/selenide");

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    Configuration.timeout = WebBrowserInitializer.TIMEOUT;
    Configuration.baseUrl = WebBrowserInitializer.URL + port;

    RemoteWebDriver remoteWebDriver = WebBrowserInitializer.WEB_DRIVER_CONTAINER.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/error");
  }

  @Test
  @DisplayName("Test whether page title is 'Error on sprint statistics'")
  void testPageTitle() {
    String caption = $(By.tagName("title")).getOwnText();
    assertThat(caption).isEqualTo("Error on sprint statistics");
  }

  @Test
  @DisplayName("Test whether after button click will be page redirected to 'About' page")
  void testErrorPageButtonClick() {
    $(By.xpath("/html/body/a")).click();
    String caption = $(By.tagName("title")).getOwnText();
    assertThat(caption).isEqualTo("About");
  }
}
