package pageclasses;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import baseclasses.PageBaseClass;
import utilities.ExcelDataFile;

public class LandingPage extends PageBaseClass {

	private JavascriptExecutor javascript;

	public LandingPage(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	@FindBy(id = "menu-item-dropdown-2696")
	WebElement menuItem_calculator;

	@FindBy(id = "menu-item-2423")
	WebElement calculatorOption_loanCalculator;

	@FindBy(id = "loanamount")
	WebElement loanAmount_textField;

	@FindBy(id = "loaninterest")
	WebElement loanInterest_textField;

	@FindBy(id = "loanterm")
	WebElement loanTenure_textField;

	@FindBy(xpath = "//li[@id='car-loan']//a")
	WebElement carLoan_tab;

	@FindBy(xpath = "//*[@id='emiamount']/p")
	WebElement emiAmount_value;

	@FindBy(xpath = "//*[@id='emitotalinterest']/p")
	WebElement totalInterest_value;

	@FindBy(xpath = "//*[@id='emitotalamount']/p")
	WebElement totalPayment_value;

	@FindBy(xpath = "//*[@id='emipaymenttable']/table/tbody/tr[1]")
	WebElement emiPaymentTable_headerRow;

	@FindBy(xpath = "//*[@id='emipaymenttable']/table/tbody/tr[contains(@class, 'yearlypaymentdetails')]")
	List<WebElement> emiPaymentTable_dataRows;

	public void setLoanAmount(String amount) {
		try {
			System.out.println("💰 Entering Loan Amount: ₹" + amount);
			loanAmount_textField.clear();
			loanAmount_textField.sendKeys(amount);
			waitLoad(1); // Visual delay so you can see the value being entered
			System.out.println("✅ Loan Amount entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanIntrest(String interestRate) {
		try {
			System.out.println("📊 Entering Interest Rate: " + interestRate + "%");
			javascript.executeScript("arguments[0].value='" + interestRate + "'", loanInterest_textField);
			waitLoad(1); // Visual delay
			System.out.println("✅ Interest Rate entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter interest rate - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanTenure(String tenure) {
		try {
			System.out.println("📅 Entering Loan Tenure: " + tenure + " year(s)");
			javascript.executeScript("arguments[0].value='" + tenure + "'", loanTenure_textField);
			loanTenure_textField.click();
			waitLoad(1); // Visual delay
			System.out.println("✅ Loan Tenure entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void verifyEmiAmount(String expectedEmiAmount) {
		try {
			System.out.println("🔍 Verifying EMI Amount...");
			String emiAmount = emiAmount_value.getText();
			System.out.println("   Expected: " + expectedEmiAmount);
			System.out.println("   Actual:   " + emiAmount);
			Assert.assertEquals(emiAmount, expectedEmiAmount);
			System.out.println("✅ EMI Amount verification passed!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: EMI Amount verification failed - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void verifyTotalInterest(String expectedTotalIntrest) {
		try {
			System.out.println("🔍 Verifying Total Interest...");
			String totalIntrest = totalInterest_value.getText();
			System.out.println("   Expected: " + expectedTotalIntrest);
			System.out.println("   Actual:   " + totalIntrest);
			Assert.assertEquals(totalIntrest, expectedTotalIntrest);
			System.out.println("✅ Total Interest verification passed!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: Total Interest verification failed - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void verifyTotaPayment(String expectedTotalPayment) {
		try {
			System.out.println("🔍 Verifying Total Payment...");
			String totalPayment = totalPayment_value.getText();
			System.out.println("   Expected: " + expectedTotalPayment);
			System.out.println("   Actual:   " + totalPayment);
			Assert.assertEquals(totalPayment, expectedTotalPayment);
			System.out.println("✅ Total Payment verification passed!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: Total Payment verification failed - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void clickCarLoan() {
		try {
			System.out.println("🚗 Clicking Car Loan tab...");
			carLoan_tab.click();
			waitLoad(1); // Visual delay so you can see the tab change
			System.out.println("✅ Car Loan tab clicked successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to click Car Loan tab - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void extractDataFromTable(String sheetName) {
		try {
			System.out.println("\n📊 Extracting table data and storing in Excel...");
			System.out.println("   Sheet Name: " + sheetName);
			ExcelDataFile excelfile = new ExcelDataFile(
					System.getProperty("user.dir") + "\\testdata\\TestOutputData.xlsx");

			if (excelfile.isSheetExist(sheetName)) {
				excelfile.removeSheet(sheetName);
			}
			excelfile.addSheet(sheetName);

			List<WebElement> columns = emiPaymentTable_headerRow.findElements(By.xpath("//th"));

			int colNum = 1;
			System.out.println("   Extracting headers...");
			for (int i = 0; i < columns.size(); i++) {
				if (!(columns.get(i).getText().isBlank() || columns.get(i).getText().isEmpty())) {
					System.out.println("     Header: " + columns.get(i).getText());
					excelfile.setCellData(sheetName, colNum, 1, columns.get(i).getText());
					colNum++;
				}
			}

			System.out.println("   Extracting data rows...");
			for (int i = 0; i < emiPaymentTable_dataRows.size(); i++) {
				columns = emiPaymentTable_dataRows.get(i).findElements(By.tagName("td"));
				for (int j = 0; j < columns.size(); j++) {
					excelfile.setCellData(sheetName, j + 1, i + 2, columns.get(j).getText());
				}
			}
			System.out.println("✅ Data extracted and stored in Excel successfully!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to extract data - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public LoanCalculator navigateToLoanCalc() {
		try {
			System.out.println("🧭 Navigating to Loan Calculator...");
			menuItem_calculator.click();
			waitLoad(1); // Visual delay
			System.out.println("✅ Clicked Calculator Menu Option");

			calculatorOption_loanCalculator.click();
			waitLoad(1); // Visual delay
			System.out.println("✅ Clicked Loan Calculator Option");

			LoanCalculator loanCalculator = new LoanCalculator(driver);
			PageFactory.initElements(driver, loanCalculator);
			System.out.println("✅ Navigated to Loan Calculator Page successfully!");
			waitLoad(2); // Visual delay to see the page change
			return loanCalculator;
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to navigate to Loan Calculator - " + e.getMessage());
			reportFail(e.getMessage());
			return null;
		}
	}

}
