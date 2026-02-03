package baseclasses;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class BaseTestClass {

	public WebDriver driver;

	/**
	 * Opens the browser - You can see the browser opening visually!
	 * 
	 * @param 	browserName
	 *			name of the browser to be opened (chrome, firefox, edge)
	 */
	public void invokeBrowser(String browserName) {
		System.out.println("\n==========================================");
		System.out.println("STEP: Opening " + browserName.toUpperCase() + " browser...");
		System.out.println("==========================================\n");
		
		try {
			if (browserName.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
				System.out.println("Firefox browser opened successfully!");
			} else if (browserName.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
				System.out.println("Edge browser opened successfully!");
			} else {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
				System.out.println("Chrome browser opened successfully!");
			}
			
			// Only configure driver if it was successfully initialized
			if (driver != null) {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
				driver.manage().window().maximize();
			} else {
				throw new RuntimeException("Driver initialization failed - driver is null");
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: Failed to open browser - " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Browser initialization failed", e);
		}
	}
	
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("❌ Error closing browser: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/* Holds the execution until page load */
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		int i = 0;
		while(i != 30) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if(pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
			i++;
		}
		
		i = 0;
		while(i != 30) {
			boolean jsState = (boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if(jsState) {
				break;
			} else {
				waitLoad(1);
			}
			i++;
		}
	}
	
	/**
	 * Holds the execution for given time - This helps you see what's happening visually!
	 * 
	 * @param 	seconds
	 * 			seconds to wait
	 */
	public void waitLoad(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
