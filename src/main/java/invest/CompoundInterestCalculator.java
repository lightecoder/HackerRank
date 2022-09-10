package invest;

public class CompoundInterestCalculator {

    private double startSum;            //USD
    private double annualMargin;        //%
    private double monthlyTopUp;        //USD
    private int yearsPeriod;            //years

    public static void main(String[] args) {
        CompoundInterestCalculator calculator = new CompoundInterestCalculator();
        calculator.setStartSum(0)
                .setAnnualMargin(10)
                .setMonthlyTopUp(10000)
                .setYearsPeriod(10);
        calculator.getOverAllSumInYears();
    }

    public double getOverAllSumInYears() {
        if (annualMargin == 0 && monthlyTopUp > 0) {
            int monthInYears = 12;
            startSum += monthInYears * monthlyTopUp * yearsPeriod;
            System.out.println("Total sum: " + startSum);
            return startSum;
        }
        else if (annualMargin == 0 && monthlyTopUp == 0) {
            System.out.println("Total sum: " + startSum);
            return startSum;
        }

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
        }
        System.out.println("Total sum: " + startSum);
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
}
