package baseclasses;

import java.io.File;

import org.apache.commons.io.FileUtils;
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

	public PageBaseClass(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Opens the website using the url - You can see the page loading!
	 * 
	 * @param url url of the website to be opened
	 * 
	 * @return Landing page object
	 */
	public LandingPage openApplication(String url) {
		driver.get(url);
		LandingPage landingPage = new LandingPage(driver);
		return landingPage;
	}
	
	public void getTitle(String expectedTitle) {
		try {
			String actualTitle = driver.getTitle();
			Assert.assertEquals(actualTitle, expectedTitle);
		} catch(Exception e) {
			System.out.println("❌ ERROR: Page title mismatch - Expected: " + expectedTitle + ", Actual: " + driver.getTitle());
			takeScreenshot("Fail");
			Assert.fail("Page title verification failed: " + e.getMessage());
		}
	}
	
	public void scrollPage(int pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixels + ")");
	}
	
	public void scrollPageToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
	}

	public void reportFail(String reportString) {
		System.out.println("❌ TEST FAILED: " + reportString);
		takeScreenshot("Fail");
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		System.out.println("✅ TEST PASSED: " + reportString);
		takeScreenshot("Pass");
	}

	public void takeScreenshot(String status) {
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourceFile = takescreenshot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + status + "-" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to save screenshot - " + e.getMessage());
			e.printStackTrace();
		}
	}

}
