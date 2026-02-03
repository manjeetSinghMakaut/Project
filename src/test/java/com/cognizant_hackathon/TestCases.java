package com.cognizant;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
			pageBaseClass = new PageBaseClass(driver);
		} catch (Exception e) {
			System.out.println("❌ ERROR in setUp: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Browser setup failed", e);
		}
	}
	
	@Test(priority = 1)
	public void test_homeLoan() {
		try {
			System.out.println("\n=== TEST CASE 1: HOME LOAN CALCULATOR TEST ===");
			
			driver.get("https://emicalculator.net/");
			landingPage = new LandingPage(driver);
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			
			pageBaseClass.scrollPage(200);
			landingPage.setLoanAmount(Constants.homeLoanAmount);
			landingPage.setLoanIntrest(Constants.homeLoanInterestRate);
			landingPage.setLoanTenure(Constants.homeLoanTenure);
			
			pageBaseClass.scrollPage(400);
			pageBaseClass.scrollPage(1200);
			landingPage.extractDataFromTable("home_loan");
			
			pageBaseClass.reportPass("Home Loan Test Passed Successfully");
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_homeLoan: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test(priority = 2)
	public void test_carLoan() {
		try {
			System.out.println("\n=== TEST CASE 2: CAR LOAN CALCULATOR TEST ===");
			
			driver.get("https://emicalculator.net/");
			landingPage = new LandingPage(driver);
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			
			pageBaseClass.scrollPage(200);
			landingPage.clickCarLoan();
			
			landingPage.setLoanAmount(Constants.carLoanAmount);
			landingPage.setLoanIntrest(Constants.carLoanInterestRate);
			landingPage.setLoanTenure(Constants.carLoanTenure);
			
			pageBaseClass.scrollPage(400);
			pageBaseClass.scrollPage(1200);
			landingPage.extractDataFromTable("car_loan");
			
			pageBaseClass.reportPass("Car Loan Test Passed Successfully");
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_carLoan: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test(priority = 3)
	public void test_EmiCalculator() {
		try {
			System.out.println("\n=== TEST CASE 3: EMI CALCULATOR UI VALIDATION TEST ===");
			
			driver.get("https://emicalculator.net/");
			landingPage = new LandingPage(driver);
			landingPage.getTitle("EMI Calculator for Home Loan, Car Loan & Personal Loan in India");
			
			loanCalculator = landingPage.navigateToLoanCalc();
			waitForPageLoad();
			loanCalculator.handleAd();
			
			landingPage.getTitle("Loan Calculator — Calculate EMI, Affordability, Tenure & Interest Rate");
			
			loanCalculator.setLoanAmount(Constants.emiLoanAmount);
			loanCalculator.setLoanIntrest(Constants.emiLoanInterestRate);
			loanCalculator.setLoanTenure(Constants.emiLoanTenure);
			loanCalculator.setFees(Constants.emiLoanFees);
			loanCalculator.clickLoanAmountField();
			
			pageBaseClass.scrollPage(600);
			pageBaseClass.scrollPage(1000);
			loanCalculator.extractDataFromTable("emi_calculator");
			
			pageBaseClass.reportPass("EMI Calculator Test Passed Successfully");
			
		} catch (Exception e) {
			System.out.println("\n❌ ERROR in test_EmiCalculator: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

}
