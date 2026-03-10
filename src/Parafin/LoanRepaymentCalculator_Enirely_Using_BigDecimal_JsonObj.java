package Parafin;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoanRepaymentCalculator_Enirely_Using_BigDecimal_JsonObj {
	
	// Parse JSON (maybe json file)to calculate a repayment schedule. 

	
	public static void main(String[] args) throws Exception {

        RepaymentResponse response =
                calculate("loan.json");

        System.out.println("LoanId: " + response.loanId);
        System.out.println("Total Repaid: " + response.totalRepaid);
        System.out.println("Balance Remaining: " + response.balanceRemaining);

        for (ScheduleItem s : response.schedule) {
            System.out.println(
                    s.date +
                    " revenue=" + s.revenue +
                    " repayment=" + s.repayment +
                    " remaining=" + s.remainingBalance
            );
        }
    }

    public static RepaymentResponse calculate(String filePath) throws Exception {

        // Step 1: Read JSON file
        String json = Files.readString(Path.of(filePath));

        // Step 2: Parse JSON
        JSONObject obj = new JSONObject(json);

        String loanId = obj.getString("loanId");

        BigDecimal totalPayback =
                obj.getBigDecimal("totalPayback");

        BigDecimal percentage =
                obj.getBigDecimal("repaymentPercentage");

        JSONArray revenues = obj.getJSONArray("revenues");

        List<ScheduleItem> schedule = new ArrayList<>();

        BigDecimal remainingBalance = totalPayback;
        BigDecimal totalRepaid = BigDecimal.ZERO;

        BigDecimal hundred = new BigDecimal("100");

        // Step 3: Process revenues
        for (int i = 0; i < revenues.length(); i++) {

            JSONObject r = revenues.getJSONObject(i);

            String date = r.getString("date");

            BigDecimal revenue =
                    r.getBigDecimal("amount");

            // repayment = revenue * percentage / 100
            BigDecimal repayment =
                    revenue.multiply(percentage)
                           .divide(hundred, 2, RoundingMode.HALF_UP);

            // Prevent overpayment
            if (repayment.compareTo(remainingBalance) > 0) {
                repayment = remainingBalance;
            }

            remainingBalance =
                    remainingBalance.subtract(repayment);

            totalRepaid =
                    totalRepaid.add(repayment);

            schedule.add(
                    new ScheduleItem(
                            date,
                            revenue,
                            repayment,
                            remainingBalance
                    )
            );

            if (remainingBalance.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }

        return new RepaymentResponse(
                loanId,
                schedule,
                totalRepaid,
                remainingBalance
        );
    }
    
    private static class RepaymentResponse {

        String loanId;
        List<ScheduleItem> schedule;
        BigDecimal totalRepaid;
        BigDecimal balanceRemaining;

        public RepaymentResponse(String loanId,
                                 List<ScheduleItem> schedule,
                                 BigDecimal totalRepaid,
                                 BigDecimal balanceRemaining) {
            this.loanId = loanId;
            this.schedule = schedule;
            this.totalRepaid = totalRepaid;
            this.balanceRemaining = balanceRemaining;
        }
    }
    
    private static class ScheduleItem {

        String date;
        BigDecimal revenue;
        BigDecimal repayment;
        BigDecimal remainingBalance;

        public ScheduleItem(String date, BigDecimal revenue,
                            BigDecimal repayment, BigDecimal remainingBalance) {
            this.date = date;
            this.revenue = revenue;
            this.repayment = repayment;
            this.remainingBalance = remainingBalance;
        }
    }
    
    
}