/*
 *          ALVIONA MANCHO
 *              3200098
 */

/* Stub class that implements TaxCalculator interface, used to facilitate TaxEstimatorTest class */
public class TaxCalculatorStub implements TaxCalculator{
    private final double factor;

    public TaxCalculatorStub(double factor){
        this.factor = factor;
    }

    public int tax(int income){
        return (int)(factor * income);
    }

}