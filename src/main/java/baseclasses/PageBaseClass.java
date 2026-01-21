package baseclasses;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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
		System.out.println("\n🌐 Opening website: " + url);
		driver.get(url);
		System.out.println("✅ Website opened successfully!");
		waitLoad(2); // Visual delay so you can see the page loading
		LandingPage landingPage = new LandingPage(driver);
		PageFactory.initElements(driver, landingPage);
		return landingPage;
	}
	
	public void getTitle(String expectedTitle) {
		try {
			String actualTitle = driver.getTitle();
			System.out.println("📄 Checking page title...");
			System.out.println("   Expected: " + expectedTitle);
			System.out.println("   Actual:   " + actualTitle);
			Assert.assertEquals(actualTitle, expectedTitle);
			System.out.println("✅ Page title matches! Test passed!");
			waitLoad(1); // Visual delay
		} catch(Exception e) {
			System.out.println("❌ ERROR: Page title mismatch - " + e.getMessage());
			takeScreenshot("Fail");
			Assert.fail("Page title verification failed: " + e.getMessage());
		}
	}
	
	public void scrollPage(int pixels) {
		System.out.println("📜 Scrolling page by " + pixels + " pixels...");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + pixels + ")");
		waitLoad(1); // Visual delay so you can see the scrolling
		System.out.println("✅ Scrolled successfully!");
	}
	
	public void scrollPageToElement(WebElement element) {
		System.out.println("📜 Scrolling to element...");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
		waitLoad(1); // Visual delay
		System.out.println("✅ Scrolled to element!");
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
		System.out.println("📸 Taking screenshot...");
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File sourceFile = takescreenshot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(
				System.getProperty("user.dir") + "\\screenshots\\" + status + "-" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			System.out.println("✅ Screenshot saved: " + destFile.getName());

		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to save screenshot - " + e.getMessage());
			e.printStackTrace();
		}
	}

}
