package Parafin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Parse JSON (maybe json file)to calculate a repayment schedule. 


public class RepaymentScheduleCalculator_using_JsonObject_JsonArray {

	// Test
    public static void main(String[] args) throws Exception {

        RepaymentResponse response = calculate("loan.json");

        System.out.println("LoanId: " + response.loanId);
        System.out.println("Total Repaid: " + response.totalRepaid);
        System.out.println("Balance Remaining: " + response.balanceRemaining);

        for (ScheduleItem s : response.schedule) {
            System.out.println(s.date + " revenue=" + s.revenue +
                    " repayment=" + s.repayment +
                    " remaining=" + s.remainingBalance);
        }
    }
    
    /* Java does not include a JSON parser in the core JDK. 
     * If external libraries are allowed, I would use the lightweight org.json 
     * library because it keeps the code very concise. Otherwise, 
     * I can fall back to manual parsing.
     * 
     * This solution uses org.json library
     * */
	
    public static RepaymentResponse calculate(String filePath) throws Exception {

        // Step 1: Read JSON file
        String json = Files.readString(Path.of(filePath));

        // Step 2: Parse JSON
        JSONObject obj = new JSONObject(json);

        String loanId = obj.getString("loanId");
        double totalPayback = obj.getDouble("totalPayback");
        double percentage = obj.getDouble("repaymentPercentage");

        JSONArray revenues = obj.getJSONArray("revenues");

        List<ScheduleItem> schedule = new ArrayList<>();

        double remainingBalance = totalPayback;
        double totalRepaid = 0;

        // Step 3: Process revenue entries
        for (int i = 0; i < revenues.length(); i++) {

            JSONObject rev = revenues.getJSONObject(i);

            String date = rev.getString("date");
            double revenue = rev.getDouble("amount");

            double repayment = revenue * percentage / 100.0;

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