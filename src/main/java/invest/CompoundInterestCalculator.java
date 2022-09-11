package invest;

public class CompoundInterestCalculator {

    private double startSum;            //USD
    private double annualMargin;        //%
    private double monthlyTopUp;        //USD
    private int yearsPeriod;            //years
    private double expenseRatio;        //%

    public static void main(String[] args) {
        CompoundInterestCalculator calculator = new CompoundInterestCalculator();
        calculator.setStartSum(50000)
                .setAnnualMargin(20)
                .setMonthlyTopUp(10000)
                .setYearsPeriod(10)
                .setExpenseRatio(0.69);
        calculator.getOverAllSumInvestments();
    }

    public double getOverAllSumInvestments() {
        double expenseRatioTotal = 0;
        int monthsInYear = 12;
        if (annualMargin == 0 && monthlyTopUp > 0) {
            startSum += monthsInYear * monthlyTopUp * yearsPeriod;
            System.out.println("Total sum: " + startSum);
            System.out.println("Total Top Up: " + yearsPeriod * monthsInYear * monthlyTopUp);
            System.out.println("NOTE: no Expense Ratio included in this calculation!");
            return startSum;
        }
        else if (annualMargin == 0 && monthlyTopUp == 0) {
            System.out.println("Total sum: " + startSum);
            System.out.println("NOTE: no Expense Ratio included in this calculation!");
            return startSum;
        }

        double expenseRatioAnnuallyFee = 0;
        for (int i = 0; i < yearsPeriod; i++) {
            if (monthlyTopUp > 0) {
                int monthInYears = 12;
                for (int j = 0; j < monthInYears; j++) {
                    startSum += (startSum * (annualMargin / monthInYears / 100)) + monthlyTopUp;
                }
            }
            else {
                startSum += startSum * annualMargin / 100;
            }
            expenseRatioAnnuallyFee = (startSum / 100) * expenseRatio;
            startSum -= expenseRatioAnnuallyFee;
            expenseRatioTotal += expenseRatioAnnuallyFee;
        }
        System.out.println("Total sum : " + startSum);
        System.out.println("Total Top Up : " + yearsPeriod * monthsInYear * monthlyTopUp);
        System.out.println("Compound margin income : " + (startSum - (yearsPeriod * monthsInYear * monthlyTopUp)));
        System.out.println("Expense Ratio Total: " + expenseRatioTotal);
        System.out.println("Last year Expense Ratio: " + expenseRatioAnnuallyFee);
        return startSum;
    }

    public double getStartSum() {
        return startSum;
    }

    public CompoundInterestCalculator setStartSum(double startSum) {
        this.startSum = startSum;
        return this;
    }

    public double getAnnualMargin() {
        return annualMargin;
    }

    public CompoundInterestCalculator setAnnualMargin(double annualMargin) {
        this.annualMargin = annualMargin;
        return this;
    }

    public double getMonthlyTopUp() {
        return monthlyTopUp;
    }

    public CompoundInterestCalculator setMonthlyTopUp(double monthlyTopUp) {
        this.monthlyTopUp = monthlyTopUp;
        return this;
    }

    public int getYearsPeriod() {
        return yearsPeriod;
    }

    public CompoundInterestCalculator setYearsPeriod(int yearsPeriod) {
        this.yearsPeriod = yearsPeriod;
        return this;
    }

    public double getExpenseRatio() {
        return expenseRatio;
    }

    public CompoundInterestCalculator setExpenseRatio(double expenseRatio) {
        this.expenseRatio = expenseRatio;
        return this;
    }
}
