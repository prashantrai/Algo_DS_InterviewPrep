package Parafin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class RepaymentCalculator_using_faster_xml {
	
	
	// Parse JSON (maybe json file)to calculate a repayment schedule. 


	public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Read input JSON
        LoanData loanData = mapper.readValue(
                new File("loan.json"),
                LoanData.class
        );

        RepaymentResult result = calculateSchedule(loanData);

        // Print output JSON
        String output = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(result);

        System.out.println(output);
    }

    /**
     * Calculates repayment schedule
     */
    private static RepaymentResult calculateSchedule(LoanData loanData) {

        double remainingBalance = loanData.totalPayback;
        double totalRepaid = 0;

        List<RepaymentEntry> schedule = new ArrayList<>();

        for (Revenue r : loanData.revenues) {

            if (remainingBalance <= 0) {
                break;
            }

            // Calculate repayment
            double repayment =
                    (r.amount * loanData.repaymentPercentage) / 100.0;

            // Cap repayment if exceeding balance
            if (repayment > remainingBalance) {
                repayment = remainingBalance;
            }

            remainingBalance -= repayment;
            totalRepaid += repayment;

            RepaymentEntry entry = new RepaymentEntry();
            entry.date = r.date;
            entry.revenue = r.amount;
            entry.repayment = roundUsingBigDecimal(repayment);
            entry.remainingBalance = roundUsingBigDecimal(remainingBalance);

            schedule.add(entry);
        }

        RepaymentResult result = new RepaymentResult();
        result.loanId = loanData.loanId;
        result.schedule = schedule;
        result.totalRepaid = roundUsingBigDecimal(totalRepaid);
        result.balanceRemaining = roundUsingBigDecimal(remainingBalance);

        return result;
    }

    /* Why Do We Need This? When Java performs calculations with double, 
     * the result may contain many decimal digits due to floating-point precision.
     * Example: double repayment = 1200 * 10 / 100.0; will output 119.99999999997
     * But the correct financial value should be: 120.00 So we round it.
     * 
     * How the Method Works? value * 100
     * Example: 120.567
     * Step 1: 120.567 * 100 = 12056.7
     * Step 2: Math.round(12056.7) = 12057
     * Step 3: 12057 / 100.0 = 120.57
     * Final result: 120.57
     */
    
    /* In production financial systems I would prefer BigDecimal for precise 
     * decimal arithmetic. If payments are distributed across multiple transactions, 
     * I would combine it with a carry-forward rounding strategy to prevent cumulative 
     * rounding errors. */
    
    // In real financial system use this - use this in interview as well
    static double roundUsingBigDecimal(double value) {
        // Convert double to BigDecimal safely
        BigDecimal bd = BigDecimal.valueOf(value);
        // Round to 2 decimal places
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    // Another option - just for referrence, NOT USED in the solution
    private double carry = 0.0;
    public double roundViaCarryForward(double value) {
        // Add previous rounding remainder
        double adjusted = value + carry;
        // Round down to 2 decimal places
        double rounded = Math.floor(adjusted * 100.0) / 100.0;
        // Store remainder for next calculation
        carry = adjusted - rounded;
        return rounded;
    }
    
    /* =======================
    Input JSON Models
    ======================= */

	 @JsonIgnoreProperties(ignoreUnknown = true)
	 class LoanData {
	
	     public String loanId;
	     public String merchantId;
	
	     public double principalAmount;
	     public double feeAmount;
	     public double totalPayback;
	
	     public double repaymentPercentage;
	     public String startDate;
	     public String currency;
	
	     public List<Revenue> revenues;
	 }
	
	 class Revenue {
	
	     public String date;
	     public double amount;
	 }
	
	 /* =======================
	    Output JSON Models
	    ======================= */
	
	 class RepaymentResult {
	
	     public String loanId;
	     public List<RepaymentEntry> schedule;
	
	     public double totalRepaid;
	     public double balanceRemaining;
	 }
	
	 class RepaymentEntry {
	
	     public String date;
	     public double revenue;
	     public double repayment;
	     public double remainingBalance;
	 }
    
}
