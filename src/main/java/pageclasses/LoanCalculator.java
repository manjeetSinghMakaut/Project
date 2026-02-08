package pageclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import baseclasses.PageBaseClass;

public class LoanCalculator extends PageBaseClass {

	private JavascriptExecutor javascript;

	// Locators - All xpaths and element identifiers at the top
	private static final By AD_PARENT_IFRAME = By.xpath("//html/ins/div/iframe[contains(@id, 'aswift_')]");
	private static final By AD_CHILD_IFRAME = By.id("ad_iframe");
	private static final By DISMISS_AD_BUTTON = By.xpath("//div[@id='dismiss-button']/div");
	private static final By LOAN_AMOUNT_TEXT_FIELD = By.id("loanamount");
	private static final By LOAN_INTEREST_TEXT_FIELD = By.id("loaninterest");
	private static final By LOAN_TENURE_TEXT_FIELD = By.id("loanterm");
	private static final By FEES_TEXT_FIELD = By.id("loanfees");
	private static final By LOAN_EMI_VALUE = By.xpath("//*[@id='loansummary-emi']/p/span");
	private static final By LOAN_APR_VALUE = By.xpath("//*[@id='loansummary-apr']/p/span");
	private static final By TOTAL_INTEREST_VALUE = By.xpath("//*[@id='loansummary-totalinterest']/p/span");
	private static final By TOTAL_PAYMENT_VALUE = By.xpath("//*[@id='loansummary-totalamount']/p/span");

	// Creates LoanCalculator object with the browser driver
	// Sets up JavaScript executor for page actions
	public LoanCalculator(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	// Enters the loan amount in the loan amount field
	// Uses JavaScript to clear field first, then types the new amount
	public void setLoanAmount(String amount) {
		try {
			WebElement loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + 0 + "'", loanAmount_textField);
			loanAmount_textField.sendKeys(amount);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Enters the loan interest rate in the interest field
	// Clears the field first, then types the new interest rate
	public void setLoanIntrest(String intrestRate) {
		try {
			WebElement loanInterest_textField = driver.findElement(LOAN_INTEREST_TEXT_FIELD);
			loanInterest_textField.clear();
			loanInterest_textField.sendKeys(intrestRate);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter interest rate - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Sets the loan tenure using JavaScript
	public void setLoanTenure(String tenure) {
		try {
			WebElement loanTenure_textField = driver.findElement(LOAN_TENURE_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Enters the loan fees in the fees field
	// Clears the field first, then types the new fees amount
	public void setFees(String fees) {
		try {
			WebElement fees_textField = driver.findElement(FEES_TEXT_FIELD);
			fees_textField.clear();
			fees_textField.sendKeys(fees);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter fees - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}
	
	// Clicks on the loan amount field to start the calculation
	public void clickLoanAmountField() {
		WebElement loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
		loanAmount_textField.click();
	}

	// Checks if the loan EMI shown on page matches the expected value
	public void verifyLoanEmi(String expectedLoanEmi) {
		try {
			WebElement loanEmi_value = driver.findElement(LOAN_EMI_VALUE);
			String loanEmi = loanEmi_value.getText();
			Assert.assertEquals(loanEmi, expectedLoanEmi);
		} catch (Exception e) {
			WebElement loanEmi_value = null;
			try {
				loanEmi_value = driver.findElement(LOAN_EMI_VALUE);
			} catch (Exception ex) {
				// Element not found
			}
			String actual = loanEmi_value != null ? loanEmi_value.getText() : "Element not found";
			System.out.println("ERROR: Loan EMI verification failed - Expected: " + expectedLoanEmi + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	// Checks if the loan APR shown on page matches the expected value
	public void verifyLoanApr(String expectedLoanApr) {
		try {
			WebElement loanApr_value = driver.findElement(LOAN_APR_VALUE);
			String loanApr = loanApr_value.getText();
			Assert.assertEquals(loanApr, expectedLoanApr);
		} catch (Exception e) {
			WebElement loanApr_value = null;
			try {
				loanApr_value = driver.findElement(LOAN_APR_VALUE);
			} catch (Exception ex) {
				// Element not found
			}
			String actual = loanApr_value != null ? loanApr_value.getText() : "Element not found";
			System.out.println("ERROR: Loan APR verification failed - Expected: " + expectedLoanApr + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	// Checks if the total interest shown on page matches the expected value
	public void verifyTotalInterest(String expectedTotalIntrest) {
		try {
			WebElement totalInterest_value = driver.findElement(TOTAL_INTEREST_VALUE);
			String totalIntrest = totalInterest_value.getText();
			Assert.assertEquals(totalIntrest, expectedTotalIntrest);
		} catch (Exception e) {
			WebElement totalInterest_value = null;
			try {
				totalInterest_value = driver.findElement(TOTAL_INTEREST_VALUE);
			} catch (Exception ex) {
				// Element not found
			}
			String actual = totalInterest_value != null ? totalInterest_value.getText() : "Element not found";
			System.out.println("ERROR: Total Interest verification failed - Expected: " + expectedTotalIntrest + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	// Checks if the total payment shown on page matches the expected value
	public void verifyTotaPayment(String expectedTotalPayment) {
		try {
			WebElement totalPayment_value = driver.findElement(TOTAL_PAYMENT_VALUE);
			String totalPayment = totalPayment_value.getText();
			Assert.assertEquals(totalPayment, expectedTotalPayment);
		} catch (Exception e) {
			WebElement totalPayment_value = null;
			try {
				totalPayment_value = driver.findElement(TOTAL_PAYMENT_VALUE);
			} catch (Exception ex) {
				// Element not found
			}
			String actual = totalPayment_value != null ? totalPayment_value.getText() : "Element not found";
			System.out.println("ERROR: Total Payment verification failed - Expected: " + expectedTotalPayment + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	// Closes any ads that appear on the page
	// Switches to ad iframe, clicks dismiss button, then switches back
	public void handleAd() {
		try {
			WebElement adParent_iFrame = driver.findElement(AD_PARENT_IFRAME);
			driver.switchTo().frame(adParent_iFrame);
			WebElement adChild_iFrame = driver.findElement(AD_CHILD_IFRAME);
			driver.switchTo().frame(adChild_iFrame);
			WebElement dismissAdButton = driver.findElement(DISMISS_AD_BUTTON);
			dismissAdButton.click();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			// Ad may not be present - silently continue
		}
	}
	

}
