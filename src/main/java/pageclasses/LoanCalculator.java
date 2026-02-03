package pageclasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import baseclasses.PageBaseClass;
import utilities.ExcelDataFile;

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
	private static final By LOAN_PAYMENT_TABLE_HEADER_ROW = By.xpath("//*[@id='loanpaymenttable']/table/tbody/tr[1]");
	private static final By LOAN_PAYMENT_TABLE_DATA_ROWS = By.xpath("//*[@id='loanpaymenttable']/table/tbody/tr[contains(@class, 'yearlypaymentdetails')]");
	private static final By TABLE_HEADER_COLUMNS = By.xpath("//th");

	WebElement adParent_iFrame;
	WebElement adChild_iFrame;
	WebElement dismissAdButton;
	WebElement loanAmount_textField;
	WebElement loanInterest_textField;
	WebElement loanTenure_textField;
	WebElement fees_textField;
	WebElement loanEmi_value;
	WebElement loanApr_value;
	WebElement totalInterest_value;
	WebElement totalPayment_value;
	WebElement loanPaymentTable_headerRow;
	List<WebElement> loanPaymentTable_dataRows;

	public LoanCalculator(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	public void setLoanAmount(String amount) {
		try {
			loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + 0 + "'", loanAmount_textField);
			loanAmount_textField.sendKeys(amount);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanIntrest(String intrestRate) {
		try {
			loanInterest_textField = driver.findElement(LOAN_INTEREST_TEXT_FIELD);
			loanInterest_textField.clear();
			loanInterest_textField.sendKeys(intrestRate);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter interest rate - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanTenure(String tenure) {
		try {
			loanTenure_textField = driver.findElement(LOAN_TENURE_TEXT_FIELD);
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setFees(String fees) {
		try {
			fees_textField = driver.findElement(FEES_TEXT_FIELD);
			fees_textField.clear();
			fees_textField.sendKeys(fees);
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter fees - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}
	
	public void clickLoanAmountField() {
		loanAmount_textField = driver.findElement(LOAN_AMOUNT_TEXT_FIELD);
		loanAmount_textField.click();
	}

	public void verifyLoanEmi(String expectedLoanEmi) {
		try {
			loanEmi_value = driver.findElement(LOAN_EMI_VALUE);
			String loanEmi = loanEmi_value.getText();
			Assert.assertEquals(loanEmi, expectedLoanEmi);
		} catch (Exception e) {
			String actual = loanEmi_value != null ? loanEmi_value.getText() : "Element not found";
			System.out.println("❌ ERROR: Loan EMI verification failed - Expected: " + expectedLoanEmi + ", Actual: " + actual);
			reportFail(e.getMessage());
		}
	}

	public void verifyLoanApr(String expectedLoanApr) {
		try {
			loanApr_value = driver.findElement(LOAN_APR_VALUE);
			String loanApr = loanApr_value.getText();
			Assert.assertEquals(loanApr, expectedLoanApr);
		} catch (Exception e) {
			String actual = loanApr_value != null ? loanApr_value.getText() : "Element not found";
			System.out.println("❌ ERROR: Loan APR verification failed - Expected: " + expectedLoanApr + ", Actual: " + actual);
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

	public void handleAd() {
		try {
			adParent_iFrame = driver.findElement(AD_PARENT_IFRAME);
			driver.switchTo().frame(adParent_iFrame);
			adChild_iFrame = driver.findElement(AD_CHILD_IFRAME);
			driver.switchTo().frame(adChild_iFrame);
			dismissAdButton = driver.findElement(DISMISS_AD_BUTTON);
			dismissAdButton.click();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			// Ad may not be present - silently continue
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

			loanPaymentTable_headerRow = driver.findElement(LOAN_PAYMENT_TABLE_HEADER_ROW);
			List<WebElement> columns = loanPaymentTable_headerRow.findElements(TABLE_HEADER_COLUMNS);

			int colNum = 1;
			for (int i = 0; i < columns.size(); i++) {
				if (!(columns.get(i).getText().isBlank() || columns.get(i).getText().isEmpty())) {
					excelfile.setCellData(sheetName, colNum, 1, columns.get(i).getText());
					colNum++;
				}
			}

			loanPaymentTable_dataRows = driver.findElements(LOAN_PAYMENT_TABLE_DATA_ROWS);
			for (int i = 0; i < loanPaymentTable_dataRows.size(); i++) {
				columns = loanPaymentTable_dataRows.get(i).findElements(By.tagName("td"));
				for (int j = 0; j < columns.size(); j++) {
					excelfile.setCellData(sheetName, j + 1, i + 2, columns.get(j).getText());
				}
			}
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to extract data - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

}
