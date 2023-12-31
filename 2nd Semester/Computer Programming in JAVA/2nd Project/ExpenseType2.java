import java.io.*;
import java.util.*;

public class ExpenseType2 extends ExpenseType{
    private final double percentage;

    public ExpenseType2( String description, double maxComp, double percentage){
        super( description, maxComp);
        this.percentage = percentage;
        this.type = 2;
    }

    //xrhsimopoieitai kata thn anagnwsh twn arxeiwn
    public ExpenseType2(int code, String description, double maxComp, double percentage){
        super(code, description, maxComp);
        this.percentage = percentage;
        this.type = 2;
    }
    public double getPercentage(){
        return percentage;
    }


    public double calcComp(double vq) {
        return percentage * vq;
    } //ypologizei to poso apozhmiwshs gia th dapanh

    public static ExpenseType parse(BufferedReader buff, int i){
        int code = -1;
        String descr = "";
        double maxComp = 100; //proka8orismenh timh
        double perc = 1;     //proka8orismenh timh

        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;
            line = buff.readLine();

            while ( !(line.trim().equals("}")) ) {
                lineTokens = new StringTokenizer(line);

                if(lineTokens.countTokens()>=1) { //elegxos gia to an h grammh einai kenh
                    token = lineTokens.nextToken();

                    if (token.toUpperCase().equals("CODE")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityInt(token)) {  //elegxos egkyrothtas
                                code = Integer.parseInt(token);
                            }else
                                System.out.println("\tInvalid Code. Expense type ["+ i +"] cannot be resolved");
                        } else
                            System.out.println("Code not found. Expense type ["+ i +"] cannot be resolved");

                    } else if (token.toUpperCase().equals("DECSR")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            descr = token;
                        } else
                            System.out.println("Description not found. Expense type ["+ i +"] cannot be resolved");

                    } else if (token.equals("MAXCOMP")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityDouble(token)) {  //elegxos egkyrothtas
                                maxComp = Double.parseDouble(token);
                            }
                        }

                    } else if (token.equals("PERC")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityPerc(token)) {  //elegxos egkyrothtas
                                perc = Double.parseDouble(token);
                            }
                        }
                    }
                }

                line = buff.readLine();
            }
        } catch (IOException ex) {
            System.err.println("Error reading expense types' file.");
        }
        if (code!=-1 &&  !descr.equals("")) {
            return new ExpenseType2( code, descr, maxComp, perc);
        }else
            return null;
    }
}