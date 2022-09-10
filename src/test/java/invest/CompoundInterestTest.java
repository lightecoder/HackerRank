package invest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CompoundInterestTest {

    private CompoundInterestCalculator sut = new CompoundInterestCalculator();

    @Test
    void testNoMarginNoTopUp() {
        sut.setStartSum(1000)
                .setYearsPeriod(10);
        assertEquals(1000, sut.getOverAllSumInvestments());
    }

    @Test
    void test1YearWithMargin10NoTopUp() {
        sut.setStartSum(1000)
                .setAnnualMargin(10)
                .setYearsPeriod(1);
        assertEquals(1100, sut.getOverAllSumInvestments());
    }

    @Test
    void test2YearsWithMargin12NoTopUp() {
        sut.setStartSum(1000)
                .setAnnualMargin(12)
                .setYearsPeriod(2);
        assertEquals(1254.4, sut.getOverAllSumInvestments());
    }

    @Test
    void test3YearsWithMarginNoTopUp() {
        sut.setStartSum(1000)
                .setAnnualMargin(10)
                .setYearsPeriod(3);
        assertEquals(1331, sut.getOverAllSumInvestments());
    }

    @Test
    void test1YearWithMarginWithTopUp() {
        sut.setStartSum(1000)
                .setAnnualMargin(12)
                .setYearsPeriod(1)
                .setMonthlyTopUp(100);
        assertEquals(2395, (int) sut.getOverAllSumInvestments());
    }

    @Test
    void test2YearsWithMarginWithTopUp() {
        sut.setStartSum(1000)
                .setAnnualMargin(12)
                .setYearsPeriod(2)
                .setMonthlyTopUp(100);
        assertEquals(3967, (int) sut.getOverAllSumInvestments());
    }

    @Test
    void test2YearsNoMarginWithTopUp() {
        sut.setStartSum(1000)
                .setYearsPeriod(2)
                .setMonthlyTopUp(100);
        assertEquals(3400, (int) sut.getOverAllSumInvestments());
    }

    @Test
    void test1YearsNoMarginNoStartSumWithTopUp() {
        sut.setYearsPeriod(1)
                .setMonthlyTopUp(100);
        assertEquals(1200, (int) sut.getOverAllSumInvestments());
    }

    @Test
    void test1YearWithMarginNoStartSumWithTopUp() {
        sut.setYearsPeriod(1)
                .setAnnualMargin(12)
                .setMonthlyTopUp(100);
        assertEquals(1268, (int) sut.getOverAllSumInvestments());
    }
}