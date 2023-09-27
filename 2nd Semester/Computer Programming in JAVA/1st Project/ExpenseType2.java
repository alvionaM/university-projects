public class ExpenseType2 extends ExpenseType{
    private final double percentage;

    public ExpenseType2( String description, double maxComp, double percentage){
        super( description, maxComp);
        this.percentage = percentage;
    }

    public double getPercentage(){
        return percentage;
    }

    public double calcComp(double vq) {
        return percentage * vq;
    } //ypologizei to poso apozhmiwshs gia th dapanh
}