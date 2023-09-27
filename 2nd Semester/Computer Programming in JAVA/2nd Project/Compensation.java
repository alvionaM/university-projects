import java.io.*;
import java.util.*;

public class Compensation extends Statement {
    private final ExpenseType type;

    public Compensation(Employee empl, ExpenseType type) {
        super(empl);
        this.type = type;
        this.amount = empl.getEmployee_sExpenses().sumOfCompPerType(type);
    }
    public Compensation(Employee empl, ExpenseType type, double amount){ //xrhsimopoieitai kata thn anagnwsh twn arxeiwn
        super(empl, amount);
        this.type = type;
    }

    public ExpenseType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public static Compensation parse(BufferedReader buff, int i, ExpenseTypeList expenseTypes, Employee empl, double val){
        int position = -1;
        int expCode = -1;
        ExpenseType type = null;
        int expType = -1;

        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;
            line = buff.readLine();

            while (!(line.trim().equals("}"))) {
                lineTokens = new StringTokenizer(line);

                if (lineTokens.countTokens() >= 1) { //elegxos gia to an h grammh einai kenh

                    token = lineTokens.nextToken();

                    if (token.toUpperCase().equals("EXPENSE_TYPE")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();

                            if (token.equals("1") || token.equals("2")) {
                                //an exei hdh bre8ei to EXPENSE_CODE, elegxetai h antistoixia tou EXPENSE_TYPE me ayto
                                if ((type != null && type.getIntType() == Integer.parseInt(token)) || type == null) {
                                    expType = Integer.parseInt(token);
                                } else
                                    System.out.println("\tExpense type not in accordance with Expense Code. Statement ["+ i +"] cannot be resolved");
                            } else
                                System.out.println("\tInvalid Expense type. Statement ["+ i +"] cannot be resolved");
                        } else
                            System.out.println("\tExpense type not found. Statement ["+ i +"] cannot be resolved");

                    } else if (token.toUpperCase().equals("EXPENSE_CODE")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityInt(token)) {    //elegxos egkyrothtas
                                expCode = Integer.parseInt(token);

                                //entopismos ths 8eshs tou typou dapanhs mesa sth lista
                                position = expenseTypes.findExpTypeByCode(expCode);

                                if (position != -1) {
                                    //an exei hdh bre8ei to EXPENSE_TYPE, elegxetai h antistoixia tou EXPENSE_CODE me ayto
                                    if ((expType != -1 && expenseTypes.getExpenseType(position).getIntType() == expType) || expType == -1) {
                                        type = expenseTypes.getExpenseType(position);
                                    } else
                                        System.out.println("\tExpense type not in accordance with Expense Code. Statement ["+ i +"] cannot be resolved");
                                } else
                                    System.out.println("\tNo expense type has this code. Statement ["+ i +"] cannot be resolved");
                            }else
                                System.out.println("\tInvalid Expense Code. Statement ["+ i +"] cannot be resolved");
                        } else
                            System.out.println("\tExpense Code not found. Statement ["+ i +"] cannot be resolved");
                    }
                }
                line = buff.readLine();
            }
            } catch (IOException ex) {
                System.err.println("Error reading statements' file.");
            }
            if (type!=null && expType!=-1) {
                return new Compensation(empl, type, val);
            }else
                return null;
        }

    public String toString() {
        return "Compensation Statement | Type: " + type.getDescription() + " | " + super.toString();
    }
}