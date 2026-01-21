package com.cognizant_hackathon;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseclasses.BaseTestClass;
import baseclasses.PageBaseClass;
import pageclasses.LandingPage;
import pageclasses.LoanCalculator;
import utilities.Constants;

public class TestCases extends BaseTestClass {
	
	LandingPage landingPage;
	LoanCalculator loanCalculator;
	PageBaseClass pageBaseClass;
	
	@BeforeMethod
	public void setUp() {
		try {
			System.out.println("\n==========================================");
			System.out.println("🔧 SETTING UP BROWSER FOR TEST");
			System.out.println("==========================================\n");
			
			// Setup Chrome browser
			driver = new ChromeDriver();	
			
			// Initialize PageBaseClass
			pageBaseClass = new PageBaseClass(driver);
			PageFactory.initElements(driver, pageBaseClass);
			System.out.println("✅ PageBaseClass initialized!");
			System.out.println("==========================================\n");
			
		} catch (Exception e) {
			System.out.println("❌ ERROR in setUp: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Browser setup failed", e);
		}
	}
	
	@Test(priority = 1)
	public void test_homeLoan() {
		try {
			System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
			System.out.println("║     TEST CASE 1: HOME LOAN CALCULATOR TEST                    ║");
			System.out.println("╚════════════════════════════════════════════════════════════╝\n");
			
			// Navigate to website
			System.out.println("🌐 Navigating to https://emicalculator.net/");
			driver.get("https://emicalculator.net/");
			System.out.println("⏳ Waiting 5 seconds for page to fully load...");
			waitLoad(5);
			
			// Initialize LandingPage
			landingPage = new LandingPage(driver);
			PageFactory.initElements(driver, landingPage);
			System.out.println("✅ LandingPage initialized!");
			
			// Verify page title
			System.out.println("📄 Verifying page title...");
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			waitLoad(2);
			
			// Scroll and set loan details
			System.out.println("📊 Setting loan details...");
			pageBaseClass.scrollPage(200);
			waitLoad(1);
			landingPage.setLoanAmount(Constants.loanAmount);
			waitLoad(1);
			landingPage.setLoanIntrest(Constants.intrestRate);
			waitLoad(1);
			landingPage.setLoanTenure(Constants.loanTenure);
			waitLoad(2);
			
			// Scroll to see results
			pageBaseClass.scrollPage(400);
			waitLoad(3);
			
			// Verify EMI calculations
			System.out.println("✅ Verifying EMI calculations...");
			landingPage.verifyEmiAmount("₹1,31,525");
			waitLoad(1);
			landingPage.verifyTotalInterest("₹78,303");
			waitLoad(1);
			landingPage.verifyTotaPayment("₹15,78,303");
			waitLoad(2);
			
			// Scroll to table and extract data
			System.out.println("📋 Extracting data from table...");
			pageBaseClass.scrollPage(1200);
			waitLoad(2);
			landingPage.extractDataFromTable("home_loan");
			waitLoad(2);
			
			System.out.println("\n✅✅✅ HOME LOAN TEST CASE PASSED SUCCESSFULLY! ✅✅✅\n");
			pageBaseClass.reportPass("Home Loan Test Passed Successfully");
			
			// Keep browser open for visual verification
			System.out.println("👁️  Browser will stay open for 8 seconds for visual verification...");
			waitLoad(8);
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_homeLoan: " + e.getMessage());
			e.printStackTrace();
			System.out.println("👁️  Browser will stay open for 15 seconds for debugging...");
			waitLoad(15);
			throw e;
		}
	}
	
	@Test(priority = 2)
	public void test_carLoan() {
		try {
			System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
			System.out.println("║     TEST CASE 2: CAR LOAN CALCULATOR TEST                     ║");
			System.out.println("║     Finding Interest & Principal for First Month              ║");
			System.out.println("╚════════════════════════════════════════════════════════════╝\n");
			
			// Navigate to website
			System.out.println("🌐 Navigating to https://emicalculator.net/");
			driver.get("https://emicalculator.net/");
			System.out.println("⏳ Waiting 5 seconds for page to fully load...");
			waitLoad(5);
			
			// Initialize LandingPage
			landingPage = new LandingPage(driver);
			PageFactory.initElements(driver, landingPage);
			System.out.println("✅ LandingPage initialized!");
			
			// Verify page title
			System.out.println("📄 Verifying page title...");
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			waitLoad(2);
			
			// Scroll and click Car Loan
			System.out.println("🚗 Clicking Car Loan tab...");
			pageBaseClass.scrollPage(200);
			waitLoad(1);
			landingPage.clickCarLoan();
			waitLoad(2);
			
			// Set loan details
			System.out.println("📊 Setting loan details...");
			landingPage.setLoanAmount(Constants.loanAmount);
			waitLoad(1);
			landingPage.setLoanIntrest(Constants.intrestRate);
			waitLoad(1);
			landingPage.setLoanTenure(Constants.loanTenure);
			waitLoad(2);
			
			// Scroll to see results
			pageBaseClass.scrollPage(400);
			waitLoad(3);
			
			// Verify EMI calculations
			System.out.println("✅ Verifying EMI calculations...");
			landingPage.verifyEmiAmount("₹1,31,525");
			waitLoad(1);
			landingPage.verifyTotalInterest("₹78,303");
			waitLoad(1);
			landingPage.verifyTotaPayment("₹15,78,303");
			waitLoad(2);
			
			// Scroll to table and extract data
			System.out.println("📋 Extracting data from table...");
			pageBaseClass.scrollPage(1200);
			waitLoad(2);
			landingPage.extractDataFromTable("car_loan");
			waitLoad(2);
			
			System.out.println("\n✅✅✅ CAR LOAN TEST CASE PASSED SUCCESSFULLY! ✅✅✅\n");
			pageBaseClass.reportPass("Car Loan Test Passed Successfully");
			
			// Keep browser open for visual verification
			System.out.println("👁️  Browser will stay open for 8 seconds for visual verification...");
			waitLoad(8);
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_carLoan: " + e.getMessage());
			e.printStackTrace();
			System.out.println("👁️  Browser will stay open for 15 seconds for debugging...");
			waitLoad(15);
			throw e;
		}
	}
	
	@Test(priority = 3)
	public void test_EmiCalculator() {
		try {
			System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
			System.out.println("║     TEST CASE 3: EMI CALCULATOR UI VALIDATION TEST           ║");
			System.out.println("║     Testing text boxes, scales, and tenure changes          ║");
			System.out.println("╚════════════════════════════════════════════════════════════╝\n");
			
			// Navigate to website
			System.out.println("🌐 Navigating to https://emicalculator.net/");
			driver.get("https://emicalculator.net/");
			System.out.println("⏳ Waiting 5 seconds for page to fully load...");
			waitLoad(5);
			
			// Initialize LandingPage
			landingPage = new LandingPage(driver);
			PageFactory.initElements(driver, landingPage);
			System.out.println("✅ LandingPage initialized!");
			
			// Verify page title
			System.out.println("📄 Verifying page title...");
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			waitLoad(2);
			
			// Navigate to Loan Calculator
			System.out.println("🔗 Navigating to Loan Calculator...");
			loanCalculator = landingPage.navigateToLoanCalc();
			waitForPageLoad();
			waitLoad(3);
			
			// Handle ad if present
			System.out.println("🔄 Handling ads...");
			loanCalculator.handleAd();
			waitLoad(2);
			
			// Verify new page title
			System.out.println("📄 Verifying Loan Calculator page title...");
			landingPage.getTitle("Loan Calculator — Calculate EMI, Affordability, Tenure & Interest Rate");
			waitLoad(2);
			
			// Set loan details
			System.out.println("📊 Setting loan details...");
			loanCalculator.setLoanAmount(Constants.loanAmount);
			waitLoad(1);
			loanCalculator.setLoanIntrest(Constants.intrestRate);
			waitLoad(1);
			loanCalculator.setLoanTenure(Constants.loanTenure);
			waitLoad(1);
			loanCalculator.setFees(Constants.fees);
			waitLoad(1);
			loanCalculator.clickLoanAmountField();
			waitLoad(2);
			
			// Verify calculations
			System.out.println("✅ Verifying loan calculations...");
			loanCalculator.verifyLoanEmi("1,31,525.27");
			waitLoad(1);
			loanCalculator.verifyLoanApr("10.76");
			waitLoad(2);
			
			// Scroll and verify totals
			pageBaseClass.scrollPage(600);
			waitLoad(2);
			loanCalculator.verifyTotalInterest("78,303");
			waitLoad(1);
			loanCalculator.verifyTotaPayment("15,88,303");
			waitLoad(2);
			
			// Scroll to table and extract data
			System.out.println("📋 Extracting data from table...");
			pageBaseClass.scrollPage(1000);
			waitLoad(2);
			loanCalculator.extractDataFromTable("emi_calculator");
			waitLoad(2);
			
			System.out.println("\n✅✅✅ EMI CALCULATOR TEST CASE PASSED SUCCESSFULLY! ✅✅✅\n");
			pageBaseClass.reportPass("EMI Calculator Test Passed Successfully");
			
			// Keep browser open for visual verification
			System.out.println("👁️  Browser will stay open for 8 seconds for visual verification...");
			waitLoad(8);
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_EmiCalculator: " + e.getMessage());
			e.printStackTrace();
			System.out.println("👁️  Browser will stay open for 15 seconds for debugging...");
			waitLoad(15);
			throw e;
		}
	}

}
