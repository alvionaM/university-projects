import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/*
 *          ALVIONA MANCHO
 *              3200098
 */

/*  Test class for TaxEstimator class */
public class TaxEstimatorTest {
    private TaxEstimator taxEst;

    @BeforeEach
    public void setUp(){
        taxEst = new TaxEstimator();
        TaxCalculatorStub taxCalc = new TaxCalculatorStub(0.2); //let tax be 20% (0.2) of the income
        taxEst.setCalculator(taxCalc);
    }

    /*
    Case when income argument is negative
    Then IllegalArgumentException should be thrown
    */
    @Test
    public void negativeIncome(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            taxEst.estimateTax(-5000);
        });
    }

    /*
    Case when income argument equals to zero
    Then estimated tax should be zero
    */
    @Test
    public void zeroIncome(){
        int income = 0;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(0, estimatedTax);
    }

    /*
    Case when calculated tax (calc_tax) > 3000
    Then estimated tax should be calc_tax - calc_tax / 10
    */
    @Test
    public void taxGreater3000(){
        int income = 20000;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(3600, estimatedTax);
    }

    /*
    Case when calculated tax (calc_tax) == 3000
    Then estimated tax should be calc_tax - 10
    */
    @Test
    public void tax3000(){
        int income = 15000;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(2990, estimatedTax);
    }

    /*
    Case when 1000 < calculated tax (calc_tax) <= 3000
    Then estimated tax should be calc_tax - 10
    */
    @Test
    public void taxGreater1000Less3000(){
        int income = 10000;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(1990, estimatedTax);
    }

    /*
    Case when calculated tax (calc_tax) == 1000
    Then estimated tax should be calc_tax
    */
    @Test
    public void tax1000(){
        int income = 5000;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(1000, estimatedTax);
    }

    /*
    Case when calculated tax (calc_tax) < 1000
    Then estimated tax should be calc_tax
    */
    @Test
    public void taxLess1000(){
        int income = 500;
        int estimatedTax = taxEst.estimateTax(income);
        Assertions.assertEquals(100, estimatedTax);
    }
}
