package com.sprint.extension;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * The Class ScreenCaptureOnFailure.
 */
public class ScreenCaptureOnFailure implements AfterEachCallback {

	/** The log. */
	Log log = LogFactory.getLog(ScreenCaptureOnFailure.class);

	/**
	 * After each.
	 *
	 * @param extensionContext the extension context
	 * @throws Exception the exception
	 */
	@Override
	public void afterEach(ExtensionContext extensionContext) throws Exception {
		if (extensionContext.getExecutionException().isPresent()) {
			Object testInstance = extensionContext.getRequiredTestInstance();
			Field containerField = testInstance.getClass().getDeclaredField("container");
			containerField.setAccessible(true);
			BrowserWebDriverContainer<?> browserContainer = (BrowserWebDriverContainer<?>) containerField
					.get(testInstance);
			byte[] screenshot = browserContainer.getWebDriver().getScreenshotAs(OutputType.BYTES);

			try {
				Path path = Paths.get("capture/selenium-screenshots")
						.resolve(String.format("%s-%s-%s.png", LocalDateTime.now(),
								extensionContext.getRequiredTestClass().getName(),
								extensionContext.getRequiredTestMethod().getName()));

				Files.createDirectories(path.getParent());
				Files.write(path, screenshot);
			} catch (IOException e) {
				log.error("Screen capture was not saved.");
			}
		}
	}
}