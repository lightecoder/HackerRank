package leetcode;

public class CreditCalculator {

    private static final Integer LOAN_YEARLY_FEE = 10; // % on yearly basis
    private static final Integer START_LOAN_BODY = 436_000; // total credit sum
    private static final Integer MONTHS_IN_YEAR = 12;
    private static final Integer CREDIT_MONTHS_DURATION = 240;

    public static void main(String[] args) {
        calculateCredit();
    }

    private static void calculateCredit() {
        int monthlyBodyPayment = START_LOAN_BODY / CREDIT_MONTHS_DURATION;
        int currentLoanBody = START_LOAN_BODY;
        int totalFeeAggregator = 0;
        int year = 0;
        for (int i = 1; i <= CREDIT_MONTHS_DURATION; i++) {
            if (i % MONTHS_IN_YEAR == 1) {
                year++;
            }
            int monthlyLoanFee = (currentLoanBody / 100) * LOAN_YEARLY_FEE / MONTHS_IN_YEAR;
            int totalPayment = monthlyLoanFee + monthlyBodyPayment;
            totalFeeAggregator += monthlyLoanFee;
            if (currentLoanBody > monthlyBodyPayment) {
                currentLoanBody -= monthlyBodyPayment;
                System.out.println(
                        "Year = " + year + ", Month = " + i + " , Payment body = " + monthlyBodyPayment + " , Fee = "
                        + monthlyLoanFee + ", Total Monthly payment = " + totalPayment);
            }
            else {
                System.out.println(
                        "Year = " + year + "Month = " + i + " , Last payment body = " + currentLoanBody + " , Fee = "
                        + monthlyLoanFee + ", Total Monthly payment = " + (currentLoanBody + monthlyLoanFee));
                break;
            }
        }
        System.out.println("Total Fee payed: " + totalFeeAggregator);
    }
}
