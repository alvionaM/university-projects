import java.util.ArrayList;
import java.util.Random;

public abstract class ExpenseType{
    protected int code;
    protected String description;
    protected double maxComp;
    protected int type;

    static ArrayList<Integer> allCodes = new ArrayList<>(); //sygkratei tous kwdikous gia na ginei o elegxos

    public ExpenseType( String description, double maxComp){
        //Paragwgh neou tyxaiou 5pshfiou kwdikou
        Random ran =new Random();
        int i;
        do {
             i = 10000 + ran.nextInt(10000);
        } while (allCodes.contains(i));
        allCodes.add(i);

        this.code = i;
        this.description = description;
        this.maxComp = maxComp;
    }

    public ExpenseType(int code, String description, double maxComp){ //xrhsimopoieitai kata thn anagnwsh twn arxeiwn
        this.code = code;
        this.description = description;
        this.maxComp = maxComp;
    }


    public String getDescription(){
        return description;
    }

    public int getCode(){
        return code;
    }

    public double getMaxComp() {
        return maxComp;
    }

    public int getIntType(){return type; }

    public abstract double calcComp(double vq); //ypologizei to poso apozhmiwshs gia th dapanh

    public String toString(){
        return "Code: " + code + " | Description: " + description + " | Maximum Compensation: " + maxComp;
    }

}