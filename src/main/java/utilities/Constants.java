package utilities;

public class Constants {
	
	// Test 1: Home Loan Calculator - Higher amount, longer tenure
	public static String homeLoanAmount = "2000000";      // ₹20,00,000
	public static String homeLoanInterestRate = "8.5";   // 8.5%
	public static String homeLoanTenure = "2";           // 2 years
	
	// Test 2: Car Loan Calculator - Medium amount, shorter tenure
	public static String carLoanAmount = "800000";        // ₹8,00,000
	public static String carLoanInterestRate = "10.5";    // 10.5%
	public static String carLoanTenure = "1";             // 1 year
	
	// Test 3: EMI Calculator - Different amount with fees
	public static String emiLoanAmount = "1200000";       // ₹12,00,000
	public static String emiLoanInterestRate = "9.5";    // 9.5%
	public static String emiLoanTenure = "3";            // 3 years
	public static String emiLoanFees = "15000";          // ₹15,000
	
	// Default values (for backward compatibility)
	public static String loanAmount = "1500000";
	public static String intrestRate = "9.5";
	public static String loanTenure = "1";
	public static String fees = "10000";

}
