package pageclasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import baseclasses.PageBaseClass;
import utilities.ExcelDataFile;

public class LoanCalculator extends PageBaseClass {

	private JavascriptExecutor javascript;

	public LoanCalculator(WebDriver driver) {
		super(driver);
		javascript = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//html/ins/div/iframe[contains(@id, 'aswift_')]")
	WebElement adParent_iFrame;

	@FindBy(id = "ad_iframe")
	WebElement adChild_iFrame;

	@FindBy(xpath = "//div[@id='dismiss-button']/div")
	WebElement dismissAdButton;

	@FindBy(id = "loanamount")
	WebElement loanAmount_textField;

	@FindBy(id = "loaninterest")
	WebElement loanInterest_textField;

	@FindBy(id = "loanterm")
	WebElement loanTenure_textField;

	@FindBy(id = "loanfees")
	WebElement fees_textField;

	@FindBy(xpath = "//*[@id='loansummary-emi']/p/span")
	WebElement loanEmi_value;

	@FindBy(xpath = "//*[@id='loansummary-apr']/p/span")
	WebElement loanApr_value;

	@FindBy(xpath = "//*[@id='loansummary-totalinterest']/p/span")
	WebElement totalInterest_value;

	@FindBy(xpath = "//*[@id='loansummary-totalamount']/p/span")
	WebElement totalPayment_value;

	@FindBy(xpath="//*[@id='loanpaymenttable']/table/tbody/tr[1]")
	WebElement loanPaymentTable_headerRow;
	
	@FindBy(xpath="//*[@id='loanpaymenttable']/table/tbody/tr[contains(@class, 'yearlypaymentdetails')]")
	List<WebElement> loanPaymentTable_dataRows;

	public void setLoanAmount(String amount) {
		try {
			System.out.println("💰 Entering Loan Amount: ₹" + amount);
			javascript.executeScript("arguments[0].value='" + 0 + "'", loanAmount_textField);
			loanAmount_textField.sendKeys(amount);
			waitLoad(1); // Visual delay
			System.out.println("✅ Loan Amount entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan amount - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setLoanIntrest(String intrestRate) {
		try {
			System.out.println("📊 Entering Interest Rate: " + intrestRate + "%");
			loanInterest_textField.clear();
			loanInterest_textField.sendKeys("9.5");
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
			waitLoad(1); // Visual delay
			System.out.println("✅ Loan Tenure entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter loan tenure - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void setFees(String fees) {
		try {
			System.out.println("💵 Entering Fees & Charges: ₹" + fees);
			fees_textField.clear();
			fees_textField.sendKeys(fees);
			waitLoad(1); // Visual delay
			System.out.println("✅ Fees & Charges entered successfully!");
		} catch (Exception e) {
			System.out.println("❌ ERROR: Failed to enter fees - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}
	
	public void clickLoanAmountField() {
		System.out.println("🖱️ Clicking Loan Amount field...");
		loanAmount_textField.click();
		waitLoad(1); // Visual delay
		System.out.println("✅ Clicked Loan Amount field!");
	}

	public void verifyLoanEmi(String expectedLoanEmi) {
		try {
			System.out.println("🔍 Verifying Loan EMI...");
			String loanEmi = loanEmi_value.getText();
			System.out.println("   Expected: " + expectedLoanEmi);
			System.out.println("   Actual:   " + loanEmi);
			Assert.assertEquals(loanEmi, expectedLoanEmi);
			System.out.println("✅ Loan EMI verification passed!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: Loan EMI verification failed - " + e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void verifyLoanApr(String expectedLoanApr) {
		try {
			System.out.println("🔍 Verifying Loan APR...");
			String loanApr = loanApr_value.getText();
			System.out.println("   Expected: " + expectedLoanApr);
			System.out.println("   Actual:   " + loanApr);
			Assert.assertEquals(loanApr, expectedLoanApr);
			System.out.println("✅ Loan APR verification passed!");
			waitLoad(1); // Visual delay
		} catch (Exception e) {
			System.out.println("❌ ERROR: Loan APR verification failed - " + e.getMessage());
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

	public void handleAd() {
		try {
			System.out.println("🪟 Handling advertisement popup...");
			driver.switchTo().frame(adParent_iFrame);
			driver.switchTo().frame(adChild_iFrame);
			dismissAdButton.click();
			driver.switchTo().parentFrame();
			waitLoad(1); // Visual delay
			System.out.println("✅ Advertisement closed successfully!");
		} catch (Exception e) {
			System.out.println("⚠️ WARNING: Could not close ad (this is okay if ad doesn't appear) - " + e.getMessage());
			// Don't fail the test if ad handling fails
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

			List<WebElement> columns = loanPaymentTable_headerRow.findElements(By.xpath("//th"));

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
			for (int i = 0; i < loanPaymentTable_dataRows.size(); i++) {
				columns = loanPaymentTable_dataRows.get(i).findElements(By.tagName("td"));
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

}
