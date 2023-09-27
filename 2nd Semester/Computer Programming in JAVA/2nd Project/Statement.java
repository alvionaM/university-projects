import java.io.*;
import java.util.*;

public abstract class Statement {
    protected Employee empl;
    protected double amount;

    public Statement(Employee empl) {
        this.empl = empl;
    }

    public Statement(Employee empl, double amount) { //xrhsimopoieitai kata thn anagnwsh twn arxeiwn
        this.empl = empl;
        this.amount = amount;
    }

    public Employee getEmployee() {
        return empl;
    }

    public abstract double getAmount();

    public static Statement parse(String stType, BufferedReader buff, int i, EmployeesList employees, ExpenseTypeList expenses) {
        int emplCode = -1;
        Employee empl = null;
        int position = -1;
        double val = -1;

        try {
            buff.reset();
            StringTokenizer lineTokens;
            String token;
            String line;
            line = buff.readLine();

            while (!(line.trim().equals("}"))) {
                lineTokens = new StringTokenizer(line);

                if(lineTokens.countTokens()>=1) { //elegxos gia to an h grammh einai kenh
                    token = lineTokens.nextToken();

                    if (token.toUpperCase().equals("EMPLOYEE_CODE")) {
                        if (lineTokens.countTokens() >= 1) {

                            token = lineTokens.nextToken();

                            if(mainApp.dataValidityInt(token)) { //elegxos egkyrothtas
                                emplCode = Integer.parseInt(token);

                                //entopismos ths 8eshs tou ergazomenou sth lista
                                position = employees.findEmployeeByCode(emplCode);

                                if (position != -1) {
                                    empl = employees.getEmployee(position);
                                } else
                                    System.out.println("\tNo employee has this code. Statement ["+ i +"] cannot be resolved");
                            }else
                                System.out.println("\tInvalid Employee Code. Statement ["+ i +"] cannot be resolved");
                        } else
                            System.out.println("\tEmployee Code not found. Statement ["+ i +"] cannot be resolved");

                    } else if (token.toUpperCase().equals("VAL")) {
                        if (lineTokens.countTokens() >= 1) {
                            token = lineTokens.nextToken();
                            if(mainApp.dataValidityDouble(token)) {   //elegxos egkyrothtas
                                val = Double.parseDouble(token);
                            }
                        } else
                            System.out.println("\tAmount not found. Statement ["+ i +"] cannot be resolved");

                    }
                }

                line = buff.readLine();
            }
        } catch (IOException ex) {
            System.err.println("Error reading statements' file.");
        }
        if (empl != null && val!=-1) {
            if(stType.equals("PROKATAVOLI")){
                return new Deposit(empl, val);
            }else if(stType.equals("DIAFORA")){
                return new Difference(empl, val);
            }else if(stType.equals("FINAL_APOZIMIOSI")){
                return new SpecificStatement(empl, val);
            }else{
                //kaleitai monon an exoun vre8ei o ergazomenos kai to poso, alliws outws 'h allws den dhmiourgeitai antikeimeno
                return Compensation.parse(buff, i, expenses, empl, val);
            }
        }
        return null;
    }


    public String toString() { return  "Amount: " + (Math.round(amount * 100)) / 100.0; }
}
