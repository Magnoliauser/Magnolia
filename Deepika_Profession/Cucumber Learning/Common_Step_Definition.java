package com.stepdefinitions;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.config.Config;
import com.webdrivermanager.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Common_Step_Definition {
	private static String ScenarioName = null;

	private static final Logger LOGGER = LogManager.getLogger(Common_Step_Definition.class);

	@Before
	public void beforescenario(Scenario scenario) {
		ScenarioName = scenario.getName();
		LOGGER.info("test");
		try {

	//		if (DriverManager.getDriver() == null) {
	//			LOGGER.info("DRIVER is NULL");
	//		}
		DriverManager.launchBrowser();

		}

		catch (Exception e) {
		}
	}

	@After(order = 1)
	public void screenhot(Scenario scenario) {
		if (scenario.isFailed()) {
			String ScenarioName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", ScenarioName);
		}

	}

	@After(order = 0)
	public void tearDown(Scenario scenario) {
		ScenarioName = scenario.getName();
		if (scenario.isFailed()) {
			File screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(screenshot, new File(Config.SCREENSHOT_FAILURE, ScenarioName + ".png"));
			}

			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		DriverManager.getDriver().quit();
	}

	public static String getScenarioName() {
		return ScenarioName;
	}
}
