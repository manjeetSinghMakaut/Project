package pageclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import baseclasses.PageBaseClass;

public class LandingPage extends PageBaseClass {

	private JavascriptExecutor javascript;

	// Locators - All xpaths and element identifiers at the top
	private static final By MENU_ITEM_CALCULATOR = By.id("menu-item-dropdown-2696");
	private static final By CALCULATOR_OPTION_LOAN_CALCULATOR = By.id("menu-item-2423");
	private static final By LOAN_AMOUNT_TEXT_FIELD = By.id("loanamount");
	private static final By LOAN_INTEREST_TEXT_FIELD = By.id("loaninterest");
	private static final By LOAN_TENURE_TEXT_FIELD = By.id("loanterm");
	private static final By CAR_LOAN_TAB = By.xpath("//li[@id='car-loan']//a");
	private static final By EMI_AMOUNT_VALUE = By.xpath("//*[@id='emiamount']/p");
	private static final By TOTAL_INTEREST_VALUE = By.xpath("//*[@id='emitotalinterest']/p");
	private static final By TOTAL_PAYMENT_VALUE = By.xpath("//*[@id='emitotalamount']/p");

	// Creates LandingPage object with the browser driver
	// Sets up JavaScript executor for page actions
	public LandingPage(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	// Enters the loan amount in the loan amount field
	// Clears the field first, then types the new amount
	public void setLoanAmount(String amount) {
		try {
			WebElement loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
			loanAmount_textField.clear();
			loanAmount_textField.sendKeys(amount);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Sets the loan interest rate using JavaScript
	public void setLoanIntrest(String interestRate) {
		try {
			WebElement loanInterest_textField = driver.findElement(LOAN_INTEREST_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + interestRate + "'", loanInterest_textField);
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter interest rate - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Sets the loan tenure using JavaScript and clicks the field
	public void setLoanTenure(String tenure) {
		try {
			WebElement loanTenure_textField = driver.findElement(LOAN_TENURE_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
			loanTenure_textField.click();
		} catch (Exception e) {
			System.out.println("ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	// Checks if the EMI amount shown on page matches the expected value
	public void verifyEmiAmount(String expectedEmiAmount) {
		try {
			WebElement emiAmount_value = driver.findElement(EMI_AMOUNT_VALUE);
			String emiAmount = emiAmount_value.getText();
			Assert.assertEquals(emiAmount, expectedEmiAmount);
		} catch (Exception e) {
			WebElement emiAmount_value = null;
			try {
				emiAmount_value = driver.findElement(EMI_AMOUNT_VALUE);
			} catch (Exception ex) {
				// Element not found
			}
			String actual = emiAmount_value != null ? emiAmount_value.getText() : "Element not found";
			System.out.println("ERROR: EMI Amount verification failed - Expected: " + expectedEmiAmount + ", Actual: " + actual);
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

	// Clicks on the Car Loan tab to switch to car loan calculator
	public void clickCarLoan() {
		try {
			WebElement carLoan_tab = driver.findElement(CAR_LOAN_TAB);
			carLoan_tab.click();
		} catch (Exception e) {
			System.out.println("ERROR: Failed to click Car Loan tab - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}


	// Goes to the Loan Calculator page from the main menu
	// Clicks Calculator menu and then Loan Calculator option
	// Returns the LoanCalculator page object
	public LoanCalculator navigateToLoanCalc() {
		try {
			WebElement menuItem_calculator = driver.findElement(MENU_ITEM_CALCULATOR);
			menuItem_calculator.click();
			WebElement calculatorOption_loanCalculator = driver.findElement(CALCULATOR_OPTION_LOAN_CALCULATOR);
			calculatorOption_loanCalculator.click();
			LoanCalculator loanCalculator = new LoanCalculator(driver);
			return loanCalculator;
		} catch (Exception e) {
			System.out.println("ERROR: Failed to navigate to Loan Calculator - " + e.getMessage());
			reportFail(e.getMessage());
			return null;
		}
	}

}
