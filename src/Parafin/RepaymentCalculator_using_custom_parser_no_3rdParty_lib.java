package Parafin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RepaymentCalculator_using_custom_parser_no_3rdParty_lib {

	public static void main(String[] args) throws Exception {
        RepaymentResponse response = calculate("loan.json");

        System.out.println("LoanId: " + response.loanId);
        System.out.println("Total Repaid: " + response.totalRepaid);
        System.out.println("Balance Remaining: " + response.balanceRemaining);

        for (ScheduleItem item : response.schedule) {
            System.out.println(
                    item.date + " revenue=" + item.revenue +
                    " repayment=" + item.repayment +
                    " remaining=" + item.remainingBalance
            );
        }
    }
	
    // Read entire file content into a String
    public static String readFile(String filePath) throws IOException {
    	System.out.println("user.dir = " + System.getProperty("user.dir"));

    	String absolutePath = "/Users/prashantrai/Documents/git/Algo_DS_InterviewPrep/src/Parafin/repayment_schedule.json";
    	absolutePath = "src/Parafin/repayment_schedule.json";
    	String json = Files.readString(Path.of(absolutePath)); // don't use this for very large file
    	//System.out.println("json1:: " + json);
    	
    	return json;
    }
    // NOT IN USE:: skip using this in interview - not in use in this class/implementation
    private static String readFileForVeryLargeFiles(String file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        br.close();

        return sb.toString();
    }


    /**
     | Field               | Description          |
	 | ------------------- | -------------------- |
	 | principalAmount     | Loan given           |
	 | feeAmount           | Parafin fee          |
	 | totalPayback        | Principal + Fee      |
	 | repaymentPercentage | % of daily revenue   |
	 | revenues            | Merchant daily sales |
     *
     * */
    
    
    
    public static RepaymentResponse calculate(String filePath) throws Exception {

        // Step 1: Read JSON file
    	String json = readFile("filePath");
    	
    	// Step 2: Parse basic fields
    	String loanId = getString(json, "loanId");
        double totalPayback = getDouble(json, "totalPayback");
        double repaymentPercentage = getDouble(json, "repaymentPercentage");
        
        /* Step 3: Parse revenue section */
        List<ScheduleItem> schedule = new ArrayList<>();
        
        String revenueSection = 
        		json.substring(json.indexOf("[") + 1, json.lastIndexOf("]"));
        String[] entries = 
        		revenueSection.split("\\},\\s*\\{");
        
        double remainingBalance = totalPayback;
        double totalRepaid = 0;
        
        /*  For each revenue:
	    	repayment = revenue * percentage / 100
	    	if repayment > remainingBalance
	    	    repayment = remainingBalance
	
	    	remainingBalance -= repayment
	    	totalRepaid += repayment
	
	    	Create schedule entry. */
        
        for (String entry : entries) {
        	String date = getString(entry, "date");
            double revenue = getDouble(entry, "amount");

            double repayment = revenue * repaymentPercentage / 100.0;
            
            // Ensure we don't overpay
            if (repayment > remainingBalance) {
                repayment = remainingBalance;
            }
            
            remainingBalance -= repayment;
            totalRepaid += repayment;

            schedule.add(new ScheduleItem(
                    date,
                    revenue,
                    roundUsingBigDecimal(repayment),
                    roundUsingBigDecimal(remainingBalance)
            ));

            if (remainingBalance <= 0) {
                break;
            }
        }
        
        return new RepaymentResponse(
                loanId,
                schedule,
                roundUsingBigDecimal(totalRepaid),
                roundUsingBigDecimal(remainingBalance)
        );
    	
    }
    
    
    // ---------------- Helper methods ----------------
    
    /* "loanId": "LN12345",
  "merchantId": "M56789",
  "principalAmount": 10000,
  "feeAmount": 800,
  "totalPayback": 10800,
  "repaymentPercentage": 10,
  "startDate": "2026-02-01",
  "currency": "USD",*/

    static String getString(String json, String key) {
    	int index = json.indexOf("\"" + key + "\"");
    	int colon = json.indexOf(":", index); // indexOf(String str, int fromIndex)
    	int start = json.indexOf("\"", colon + 1) + 1; 
    	int end  = json.indexOf("\"", start);
    	
    	return json.substring(start, end); // will return value, e.g., value against loanId i.e. LN12345 
    }
    
    static double getDouble(String json, String key) {
    	int index = json.indexOf("\"" + key + "\"");
    	int colon = json.indexOf(":", index); // indexOf(String str, int fromIndex)
    	
    	int comma = json.indexOf(",", colon + 1);
    	if(comma == -1) {
    		comma = json.indexOf("}", colon + 1);
    	}
    	
    	return Double.parseDouble(
    			json.substring(colon + 1, comma).trim()); 
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
    static double _round(double value) {
    	return Math.round(value * 100.0) / 100.0;
    }
    
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
    
    
    
    // Response
    private static class RepaymentResponse {
        String loanId;
        List<ScheduleItem> schedule;
        double totalRepaid;
        double balanceRemaining;

        public RepaymentResponse(String loanId, List<ScheduleItem> schedule,
                                 double totalRepaid, double balanceRemaining) {
            this.loanId = loanId;
            this.schedule = schedule;
            this.totalRepaid = totalRepaid;
            this.balanceRemaining = balanceRemaining;
        }
    }
    
    private static class ScheduleItem {
        String date;
        double revenue;
        double repayment;
        double remainingBalance;

        public ScheduleItem(String date, double revenue, double repayment, double remainingBalance) {
            this.date = date;
            this.revenue = revenue;
            this.repayment = repayment;
            this.remainingBalance = remainingBalance;
        }
    }
    

}
