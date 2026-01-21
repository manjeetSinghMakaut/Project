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
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				System.out.println("Firefox browser opened successfully!");
			} else if (browserName.equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe");
				driver = new EdgeDriver();
				System.out.println("Edge browser opened successfully!");
			} else {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
				System.out.println("Chrome browser opened successfully!");
			}
			
			waitLoad(1);
			
			// Only configure driver if it was successfully initialized
			if (driver != null) {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
				driver.manage().window().maximize();
				System.out.println("Browser window maximized!");
				waitLoad(1);
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
		System.out.println("\n==========================================");
		System.out.println("🔒 CLOSING BROWSER");
		System.out.println("==========================================\n");
		if (driver != null) {
			try {
				System.out.println("⏳ Waiting 2 seconds before closing browser...");
				waitLoad(2);
				driver.quit();
				System.out.println("✅ Browser closed successfully!");
			} catch (Exception e) {
				System.out.println("❌ Error closing browser: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("⚠️  No browser to close - driver was null");
		}
		System.out.println("==========================================\n");
	}
	
	/* Holds the execution until page load */
	public void waitForPageLoad() {
		System.out.println("Waiting for page to load completely...");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		int i = 0;
		while(i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if(pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
			i++;
		}
		
		waitLoad(2);
		
		i = 0;
		while(i != 180) {
			boolean jsState = (boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if(jsState) {
				break;
			} else {
				waitLoad(1);
			}
			i++;
		}
		System.out.println("Page loaded successfully!");
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
