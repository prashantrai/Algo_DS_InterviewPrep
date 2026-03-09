package Parafin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoanPaymentCalculator_with_Fixed_and_RevenueBased_fasterXML {

    public static void main(String[] args) throws Exception {

        // Read JSON file (Java 11+)
        String json = Files.readString(Path.of("loan.json"));

        ObjectMapper mapper = new ObjectMapper();
        LoanData loanData = mapper.readValue(json, LoanData.class);

        RepaymentResult result = calculateSchedule(loanData);

        System.out.println("Total Repaid: " + result.totalRepaid);
        System.out.println("Remaining Balance: " + result.balanceRemaining);
    }

    private static RepaymentResult calculateSchedule(LoanData loanData) {

        double remainingBalance = loanData.totalPayback;
        double totalRepaid = 0;

        List<RepaymentEntry> schedule = new ArrayList<>();

        double fixedRepayment = 0;

        // Pre-calc for fixed loan
        if ("FIXED_TERM".equalsIgnoreCase(loanData.loanType)) {
            fixedRepayment = loanData.totalPayback / loanData.termDays;
        }

        for (Revenue r : loanData.revenues) {

            if (remainingBalance <= 0) {
                break;
            }

            double repayment = calculateRepayment(loanData, r, fixedRepayment);

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

    private static double calculateRepayment(LoanData loanData,
                                             Revenue r,
                                             double fixedRepayment) {

        if ("REVENUE_BASED".equalsIgnoreCase(loanData.loanType)) {

            return (r.amount * loanData.repaymentPercentage) / 100.0;

        } else {

            return fixedRepayment;
        }
    }

    static double roundUsingBigDecimal(double value) {

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}