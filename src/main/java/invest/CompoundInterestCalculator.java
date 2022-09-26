package invest;

public class CompoundInterestCalculator {

    private static final double DIVIDEND_FEE = 16.5; // 15% USA taxes + 1.5% Ukraine (military)
    private static final double STOCK_SELL_FEE = 19.5; // 18% PDFO  + 1.5% Ukraine (military)
    private static final int CURRENT_YEAR = 2022;
    private static final double INFLATION = 1; //

    private double startSum;                                         // USD
    private double annualMargin;                                     // %
    private double monthlyTopUp;                                     // USD
    private int yearsPeriod;                                         // years
    private double expenseRatio;                                     // %
    private int retirementStartAfterYears;                           // years after we stop to top up the account
    private double dividendYield;                                    // % annually
    private double monthlyCacheFlowThreshold;                        // USD, max cache flow monthly

    public static void main(String[] args) {
        //        CompoundInterestCalculator calculator = new CompoundInterestCalculator();
        //        calculator.setStartSum(50)
        //                .setAnnualMargin(11.5)
        //                .setMonthlyTopUp(0)
        //                .setYearsPeriod(14)
        //                .setExpenseRatio(0.1);
        //        calculator.getOverAllSumInvestments();
        //        System.out.println("\n\n\n#########################################################################\n\n\n");

        CompoundInterestCalculator calculator2 = new CompoundInterestCalculator();
        calculator2.setStartSum(50000) // USD
                .setAnnualMargin(17.08) // %
                .setMonthlyTopUp(10000) // USD
                .setExpenseRatio(0.1) // %
                .setYearsPeriod(50) // years
                .setRetirementStartAfterYears(14) // years
                .setDividendYield(0.83)// must != 0 , %
                .setMonthlyCacheFlowThreshold(20000); // must != 0
        calculator2.getRetirementCacheFlow();
    }

    public double getRetirementCacheFlow() {
        double expenseRatioTotal = 0;
        int monthsInYear = 12;
        double expenseRatioAnnuallyFee = 0;
        double dividendsYearly = 0.0;
        int year = CURRENT_YEAR;
        double cacheFlowYearly;

        for (int i = 0; i < yearsPeriod; i++) {
            // no retirements
            // dividends reinvest
            if (year < (CURRENT_YEAR + retirementStartAfterYears)) {
                // top up
                if (monthlyTopUp > 0) {
                    for (int j = 0; j < monthsInYear; j++) {
                        startSum += (startSum * (annualMargin / monthsInYear / 100)) + monthlyTopUp;
                        dividendsYearly = startSum / 100 * dividendYield;
                        dividendsYearly -= dividendsYearly / 100 * DIVIDEND_FEE;
                        startSum += dividendsYearly / monthsInYear;
                    }
                    System.out.println(
                            "Total sum : " + startSum + ", Dividend sum reinvested : " + dividendsYearly + " in " + year
                            + " year");
                }
                // no top up
                else {
                    startSum += startSum * annualMargin / 100;
                    dividendsYearly = startSum / 100 * dividendYield;
                    dividendsYearly -= dividendsYearly / 100 * DIVIDEND_FEE;
                    startSum += dividendsYearly;
                    System.out.println(
                            "Total sum : " + startSum + ", Dividend sum reinvested : " + dividendsYearly + " in " + year
                            + " year");
                }
            }
            // retirements
            // no top up
            else {
                if (year == (CURRENT_YEAR + retirementStartAfterYears)) {
                    System.out.println("\n\n\n############## RETIREMENT #################\n\n\n");
                }
                startSum += startSum * annualMargin / 100;
                dividendsYearly = startSum / 100 * dividendYield;
                dividendsYearly -= dividendsYearly / 100 * DIVIDEND_FEE;

                // no CacheFlow from selling, only Dividends
                if (dividendsYearly > monthlyCacheFlowThreshold * monthsInYear) {
                    double cacheFlowAboveThreshold = dividendsYearly - monthlyCacheFlowThreshold * monthsInYear;
                    startSum += cacheFlowAboveThreshold;
                    System.out.println(
                            "Total sum : " + startSum + ", CacheFlow withdrawal: " + monthlyCacheFlowThreshold
                            + ", Dividend yield sum : " + dividendsYearly
                            + ", Dividends reinvested : " + cacheFlowAboveThreshold
                            + " in " + year + " year");
                }
                // CacheFlow from selling + Dividends
                else {
                    cacheFlowYearly = monthlyCacheFlowThreshold * monthsInYear - dividendsYearly;
                    double taxes = cacheFlowYearly / 100 * STOCK_SELL_FEE;
                    cacheFlowYearly += taxes;
                    startSum -= cacheFlowYearly;
                    cacheFlowYearly -= taxes;
                    System.out.println(
                            "Total sum : " + startSum + ", CacheFlow withdrawal: " + monthlyCacheFlowThreshold
                            + ", CacheFlow from selling sum: " + cacheFlowYearly
                            + ", Dividend yield sum : " + dividendsYearly
                            + " in " + year + " year");

                }
            }
            expenseRatioAnnuallyFee = (startSum / 100) * expenseRatio;
            startSum -= expenseRatioAnnuallyFee;
            expenseRatioTotal += expenseRatioAnnuallyFee;
            year++;
        }
        System.out.println("Total sum : " + startSum);
        System.out.println("Total Top Up : " + retirementStartAfterYears * monthsInYear * monthlyTopUp);
        System.out.println(
                "Compound margin income : " + (startSum - (retirementStartAfterYears * monthsInYear * monthlyTopUp)));
        System.out.println("Expense Ratio Total: " + expenseRatioTotal);
        System.out.println("Last year Expense Ratio: " + expenseRatioAnnuallyFee);
        System.out.println("Last Dividend annual sum : " + dividendsYearly);
        return startSum;
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

    public CompoundInterestCalculator setStartSum(double startSum) {
        this.startSum = startSum;
        return this;
    }

    public CompoundInterestCalculator setAnnualMargin(double annualMargin) {
        this.annualMargin = annualMargin;
        return this;
    }

    public CompoundInterestCalculator setMonthlyTopUp(double monthlyTopUp) {
        this.monthlyTopUp = monthlyTopUp;
        return this;
    }

    public CompoundInterestCalculator setYearsPeriod(int yearsPeriod) {
        this.yearsPeriod = yearsPeriod;
        return this;
    }

    public CompoundInterestCalculator setExpenseRatio(double expenseRatio) {
        this.expenseRatio = expenseRatio;
        return this;
    }

    public CompoundInterestCalculator setRetirementStartAfterYears(int retirementStartAfterYears) {
        this.retirementStartAfterYears = retirementStartAfterYears;
        return this;
    }

    public CompoundInterestCalculator setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
        return this;
    }

    public CompoundInterestCalculator setMonthlyCacheFlowThreshold(double monthlyCacheFlowThreshold) {
        this.monthlyCacheFlowThreshold = monthlyCacheFlowThreshold;
        return this;
    }
}
