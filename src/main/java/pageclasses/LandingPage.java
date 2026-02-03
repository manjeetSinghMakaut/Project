package pageclasses;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import baseclasses.PageBaseClass;
import utilities.ExcelDataFile;

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
	private static final By EMI_PAYMENT_TABLE_HEADER_ROW = By.xpath("//*[@id='emipaymenttable']/table/tbody/tr[1]");
	private static final By EMI_PAYMENT_TABLE_DATA_ROWS = By.xpath("//*[@id='emipaymenttable']/table/tbody/tr[contains(@class, 'yearlypaymentdetails')]");
	private static final By TABLE_HEADER_COLUMNS = By.xpath("//th");

	WebElement menuItem_calculator;
	WebElement calculatorOption_loanCalculator;
	WebElement loanAmount_textField;
	WebElement loanInterest_textField;
	WebElement loanTenure_textField;
	WebElement carLoan_tab;
	WebElement emiAmount_value;
	WebElement totalInterest_value;
	WebElement totalPayment_value;
	WebElement emiPaymentTable_headerRow;
	List<WebElement> emiPaymentTable_dataRows;

	public LandingPage(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	public void setLoanAmount(String amount) {
		try {
			loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
			loanAmount_textField.clear();
			loanAmount_textField.sendKeys(amount);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanIntrest(String interestRate) {
		try {
			loanInterest_textField = driver.findElement(LOAN_INTEREST_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + interestRate + "'", loanInterest_textField);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter interest rate - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanTenure(String tenure) {
		try {
			loanTenure_textField = driver.findElement(LOAN_TENURE_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
			loanTenure_textField.click();
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void verifyEmiAmount(String expectedEmiAmount) {
		try {
			emiAmount_value = driver.findElement(EMI_AMOUNT_VALUE);
			String emiAmount = emiAmount_value.getText();
			Assert.assertEquals(emiAmount, expectedEmiAmount);
		} catch (Exception e) {
			String actual = emiAmount_value != null ? emiAmount_value.getText() : "Element not found";
			System.out.println("❌ ERROR: EMI Amount verification failed - Expected: " + expectedEmiAmount + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	public void verifyTotalInterest(String expectedTotalIntrest) {
		try {
			totalInterest_value = driver.findElement(TOTAL_INTEREST_VALUE);
			String totalIntrest = totalInterest_value.getText();
			Assert.assertEquals(totalIntrest, expectedTotalIntrest);
		} catch (Exception e) {
			String actual = totalInterest_value != null ? totalInterest_value.getText() : "Element not found";
			System.out.println("❌ ERROR: Total Interest verification failed - Expected: " + expectedTotalIntrest + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	public void verifyTotaPayment(String expectedTotalPayment) {
		try {
			totalPayment_value = driver.findElement(TOTAL_PAYMENT_VALUE);
			String totalPayment = totalPayment_value.getText();
			Assert.assertEquals(totalPayment, expectedTotalPayment);
		} catch (Exception e) {
			String actual = totalPayment_value != null ? totalPayment_value.getText() : "Element not found";
			System.out.println("❌ ERROR: Total Payment verification failed - Expected: " + expectedTotalPayment + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	public void clickCarLoan() {
		try {
			carLoan_tab = driver.findElement(CAR_LOAN_TAB);
			carLoan_tab.click();
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to click Car Loan tab - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void extractDataFromTable(String sheetName) {
		try {
			ExcelDataFile excelfile = new ExcelDataFile(
					System.getProperty("user.dir") + "\\testdata\\TestOutputData.xlsx");

			if (excelfile.isSheetExist(sheetName)) {
				excelfile.removeSheet(sheetName);
			}
			excelfile.addSheet(sheetName);

			emiPaymentTable_headerRow = driver.findElement(EMI_PAYMENT_TABLE_HEADER_ROW);
			List<WebElement> columns = emiPaymentTable_headerRow.findElements(TABLE_HEADER_COLUMNS);

			int colNum = 1;
			for (int i = 0; i < columns.size(); i++) {
				if (!(columns.get(i).getText().isBlank() || columns.get(i).getText().isEmpty())) {
					excelfile.setCellData(sheetName, colNum, 1, columns.get(i).getText());
					colNum++;
				}
			}

			emiPaymentTable_dataRows = driver.findElements(EMI_PAYMENT_TABLE_DATA_ROWS);
			for (int i = 0; i < emiPaymentTable_dataRows.size(); i++) {
				columns = emiPaymentTable_dataRows.get(i).findElements(By.tagName("td"));
				for (int j = 0; j < columns.size(); j++) {
					excelfile.setCellData(sheetName, j + 1, i + 2, columns.get(j).getText());
				}
			}
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to extract data - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public LoanCalculator navigateToLoanCalc() {
		try {
			menuItem_calculator = driver.findElement(MENU_ITEM_CALCULATOR);
			menuItem_calculator.click();
			calculatorOption_loanCalculator = driver.findElement(CALCULATOR_OPTION_LOAN_CALCULATOR);
			calculatorOption_loanCalculator.click();
			LoanCalculator loanCalculator = new LoanCalculator(driver);
			return loanCalculator;
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to navigate to Loan Calculator - " + e.getMessage());
			reportFail(e.getMessage());
			return null;
		}
	}

}
