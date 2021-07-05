package com.sprint.controllers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AboutControllerTest {

  @Container
  public static final BrowserWebDriverContainer<?> webDriverContainer =
      new BrowserWebDriverContainer<>()
          .withCapabilities(new ChromeOptions()
              .addArguments("--no-sandbox")
              .addArguments("--disable-dev-shm-usage"));

  @RegisterExtension
  public static ScreenShooterExtension screenShooterExtension =
      new ScreenShooterExtension().to("target/selenide");

  @LocalServerPort
  private Integer port;

  @Test
  @DisplayName("Test whether page title is 'About'")
  void testAboutPageTitle() {
    Configuration.timeout = 2000;
    Configuration.baseUrl = "http://172.17.0.1:" + port;

    RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/about");
    String caption = $(By.tagName("title")).getOwnText();
    assertThat(caption).isEqualTo("About");
  }

  @Test
  @DisplayName("Test whether unordered web elements list has size 8")
  void testUnsortedWebElementsList() {
    Configuration.timeout = 2000;
    Configuration.baseUrl = "http://172.17.0.1:" + port;

    RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
    WebDriverRunner.setWebDriver(remoteWebDriver);

    open("/about");
    List<WebElement> elementList = $(By.className("lead")).findElements(By.cssSelector("li"));
    assertThat(elementList.size()).isEqualTo(8);
  }
}
