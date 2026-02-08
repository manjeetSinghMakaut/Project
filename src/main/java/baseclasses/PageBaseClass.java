package baseclasses;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import pageclasses.LandingPage;
import utilities.DateUtils;

public class PageBaseClass extends BaseTestClass {

	public WebDriver driver;

	// Creates PageBaseClass object with the browser driver
	public PageBaseClass(WebDriver driver) {
		this.driver = driver;
	}

	// Opens the website using the given URL
	// Returns the landing page object
	public LandingPage openApplication(String url) {
		driver.get(url);
		LandingPage landingPage = new LandingPage(driver);
		return landingPage;
	}
	
	// Checks if the page title matches the expected title
	// Takes screenshot and fails test if title is wrong
	public void getTitle(String expectedTitle) {
		try {
			String actualTitle = driver.getTitle();
			Assert.assertEquals(actualTitle, expectedTitle);
		} catch(Exception e) {
			System.out.println("ERROR: Page title mismatch - Expected: " + expectedTitle + ", Actual: " + driver.getTitle());
			takeScreenshot("Fail");
			Assert.fail("Page title verification failed: " + e.getMessage());
		}
	}
	
	// Scrolls the page down by the given number of pixels
	public void scrollPage(int pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixels + ")");
	}
	
	// Scrolls the page until the given element is visible
	public void scrollPageToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
	}

	// Reports that the test failed
	// Takes a screenshot and stops the test
	public void reportFail(String reportString) {
		System.out.println("TEST FAILED: " + reportString);
		takeScreenshot("Fail");
		Assert.fail(reportString);
	}

	// Reports that the test passed
	// Takes a screenshot for proof
	public void reportPass(String reportString) {
		System.out.println("TEST PASSED: " + reportString);
		takeScreenshot("Pass");
	}

	// Takes a screenshot and saves it to the screenshots folder
	// File name has status (Pass or Fail) and timestamp
	public void takeScreenshot(String status) {
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourceFile = takescreenshot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + status + "-" + DateUtils.getTimeStamp() + ".png");
		try {
			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to save screenshot - " + e.getMessage());
			e.printStackTrace();
		}
	}

}
