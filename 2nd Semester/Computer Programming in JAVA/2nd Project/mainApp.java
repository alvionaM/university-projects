import java.util.*;
import java.io.*;



/*************************************************************
 *  Team Number:            5
 *
 *	1st Student Name:	    MILTIADHS TSOLKAS
 *  1st Student ID Number:	3200213
 *
 *  2nd Student Name:       ALVIONA MANTSO
 *  2nd Student ID Number:  3200098
 *************************************************************/

/* Shmeiwseis:
*   1. 8ewroume oti an apo ena arxeio apousiazei to tag pou shmatodotei thn enar3h tou (px EMPLOYEE_LIST) kai/'h oi
*      agkyles enar3hs ( { )/ lh3hs( } ) lamvanetai kanonika ypopsin to arxeio, efoson vriskontai grammena ta tags
*      ths ontothtas pou prepei na perilamvanei to arxeio (px EMPLOYEE).
*
*   2. 8ewroume oti stis kinhseis ola ta pedia einai aparaithto na exoun kapoia timh sto arxeio, alliws den mporei
*      na dhmiourgh8ei to katallhlo antikeimeno ka8ws kapoia proka8orismenh timh den 8a htan eulogh.
*
*   3. 8ewroume oti kata thn anagnwsh twn kinhsewn apo to sxetiko arxeio h egkyrh morfh gia ton typo ths kinhshs einai
*      entos twn xarakthrwn << " " >> (px "PROKATAVOLI") kai diaforetika den lamvanontai ypopsin.
*/

public class mainApp {

    static Scanner input = new Scanner(System.in);

    static ExpenseTypeList expenseTypes = new ExpenseTypeList();
    static EmployeesList employees = new EmployeesList();

    static SpecificExpenseList allExpenses = new SpecificExpenseList(); //genikh lista dapanwn (olwn twn ergazomenwn)
    static StatementList allStatements = new StatementList(); //genikh lista kinhsewn (olwn twn ergazomenwn)

    public static void main (String[] args) {

        //Arxikopoihsh ths listas twn ergazomenwn:
        parseEmployeesList("EMPLOYEE_LIST.txt");

        //Arxikopoihsh ths listas twn typwn dapanwn:
        parseExpenseTypeList("EXPENSE_TYPES_LIST.txt");

        //Arxikopoihsh ths listas twn dapanwn ergazomenwn:
        parseExpenseList("EXPENSE_LIST.txt");

        //Arxikopoihsh ths listas twn kinhsewn ergazomenwn:
        parseStatementList("TRN_LIST.txt");


        int answer;
        do {
            System.out.println("\n-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
            System.out.println(" 1. Insert new expense type;" +
                    "\n 2. Insert new employee;" +
                    "\n 3. Insert an employee's expense;" +
                    "\n 4. Insert an employee's deposit;" +
                    "\n 5. Show all eployees' expenses;" +
                    "\n 6. Compensate an employee for their expenses;" +
                    "\n 7. Show an employee's statements;" +
                    "\n 8. Compensate all employees for their expenses;" +
                    "\n 9. Show the final compensation of all employees;" +
                    "\n 10.Transfer lists of expenses or statements to text files" + //epilogh gia eggrafh twn dapanwn kai kinhsewn se arxeio
                    "\n 11.Exit;"+
                    "\n-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --");
            System.out.print(">Enter number for the desired operation: ");

            answer = inputValidityInt(input.next(),11); //elegxos egkyrothtas
            System.out.println();
            switch (answer) {
                case 1:
                    procedure01();
                    break;
                case 2:
                    procedure02();
                    break;
                case 3:
                    procedure03();
                    break;
                case 4:
                    procedure04();
                    break;
                case 5:
                    procedure05();
                    break;
                case 6:
                    procedure06();
                    break;
                case 7:
                    procedure07();
                    break;
                case 8:
                    procedure08();
                    break;
                case 9:
                    procedure09();
                    break;
                case 10:
                    procedure10();
            }
            System.out.println("\n\n");

        } while (answer != 11);

        //Meta th lh3h tou programmatos enhmerwnontai pantote ta arxeia
        writeExpenseList("EXPENSE_LIST.txt");
        writeStatementList("TRN_LIST.txt");
    }//main


    static void procedure01() {
        System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
        System.out.println("\t>Insert new expense type");

        System.out.print("\t\tDescription: ");
        String desc = input.next();
        if (desc.equals("-1")){return; } //epistrofh sto kentriko menou

        System.out.print("\t\tMaximum possible amount of Compensation (give 0 if this expense type has no upper bound): ");
        double mc = inputValidityDouble(input.next()); //elegxos egkyrothtas
        if (mc == -1){return; } //epistrofh sto kentriko menou

        System.out.print("\t\tGive expense type: " +
                "\n\t\t\t1.Quantity X Price" +
                "\n\t\t\t2.Value X Compensation percentage" +
                "\n\t\t\tEnter: ");
        int type = inputValidityInt(input.next(), 2); //elegxos egkyrothtas
        if (type == -1){return; } //epistrofh sto kentriko menou

        if (type == 1) {
            System.out.print("\t\tPrice per Unit: ");
            double ppu = inputValidityDouble(input.next()); //elegxos egkyrothtas
            if (ppu == -1){return; } //epistrofh sto kentriko menou

            System.out.print("\t\tUnit Measure: ");
            String um = input.next();
            if (um.equals("-1")){return; } //epistrofh sto kentriko menou

            expenseTypes.newExpenseType(new ExpenseType1( desc, mc, ppu, um));

        } else {
            System.out.print("\t\tPercentage: ");
            double perc = percentageValidity(input.next()); //elegxos egkyrothtas
            if (perc == -1){return; } //epistrofh sto kentriko menou

            expenseTypes.newExpenseType(new ExpenseType2( desc, mc, perc));
        }
    }//procedure01


        static void procedure02(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert new employee");

            System.out.print("\t\tFirst Name: ");
            String fn = input.next();
            if (fn.equals("-1")){return; } //epistrofh sto kentriko menou

            System.out.print("\t\tLast name: ");
            String ln = input.next();
            if (ln.equals("-1")){return; } //epistrofh sto kentriko menou

            System.out.print("\t\tMaximum possible amount of Compensation per month: ");
            double gmc = inputValidityDouble(input.next()); //elegxos egkyrothtas
            if (gmc == -1){return; } //epistrofh sto kentriko menou

            employees.newEmployee(new Employee(fn, ln, gmc));
        }//procedure02


        static void procedure03(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert an employee's expense\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //elegxos egkyrothtas
            if (i == -1){return; } //epistrofh sto kentriko menou
            Employee empl = employees.getEmployee(i-1);

            System.out.print(expenseTypes);
            System.out.print("\n\t\tSelect an expense type: ");
            i = inputValidityInt(input.next(), expenseTypes.numOfExpTypes()); //elegxos egkyrothtas
            if (i == -1){return; } //epistrofh sto kentriko menou
            ExpenseType type1 = expenseTypes.getExpenseType(i-1);

            if(type1 instanceof ExpenseType1){
                System.out.print("\t\t\tQuantity: ");
            }else{
                System.out.print("\t\t\tValue: ");
            }
            double vq = inputValidityDouble(input.next()); //elegxos egkyrothtas
            if (vq == -1){return; } //epistrofh sto kentriko menou


            System.out.print("\t\t\tJustification: ");
            String just = input.next();
            if (just.equals("-1")){return; } //epistrofh sto kentriko menou

            empl.newExpense(new SpecificExpense(empl, type1, vq, just)); //eisagwgh sth lista dapanwn tou ergazomenou
            allExpenses.newExpense(new SpecificExpense(empl, type1, vq, just)); //eisagwgh sth genikh lista dapanwn
        }//procedure03


        static void procedure04(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Insert an employee's deposit\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees() ); //elegxos egkyrothtas
            if (i == -1){return; } //epistrofh sto kentriko menou

            Employee empl = employees.getEmployee(i-1);

            System.out.print("\n\t\tAmount of deposit: ");
            double dep = inputValidityDouble(input.next()); //elegxos egkyrothtas
            if (dep == -1){return; } //epistrofh sto kentriko menou

            empl.newStatement(new Deposit(empl, dep)); //eisagwgh sth lista kinhsewn tou ergazomenou
            allStatements.newStatement(new Deposit(empl, dep)); //eisagwgh sth genikh lista kinhsewn
        }//procedure04


        static void procedure05(){
            System.out.println("\t>Show all eployees' expenses");
            for( int i=0; i< employees.numOfEmployees(); i++){
                System.out.println("\t\t_._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");
                System.out.println("\t\t"+employees.getEmployee(i));
                if (employees.getEmployee(i).numOfExpenses() > 0) {
                    System.out.println(employees.getEmployee(i).getEmployee_sExpenses());
                }
                else {
                    System.out.println("\t\t\tNo expenses have been made yet"); //an h lista dapanwn einai kenh
                }
            }
        }//procedure05


        static void procedure06(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Compensate an employee for their expenses\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //elegxos egkyrothtas
            if (i == -1){return; } //epistrofh sto kentriko menou

            System.out.print("\t\tAre you sure you want to continue? 1.Yes  2.No : ");
            int answer = inputValidityInt(input.next(), 2);
            if (answer == 2 || answer==-1){ return; }//epistrofh sto kentriko menou

            Employee empl = employees.getEmployee(i-1);

            Clearance(empl); //ginetai h ekka8arish gia ton ergazomeno

            System.out.println("\t\t\tOperation successfully completed");
        }//procedure06


        static void procedure07(){
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\t>Show an employee's statements\n");

            System.out.print(employees);
            System.out.print("\n\t\tSelect an employee: ");
            int i = inputValidityInt(input.next(), employees.numOfEmployees()); //elegxos egkyrothtas
            if (i == -1){return; } //epistrofh sto kentriko menou

            Employee empl = employees.getEmployee(i-1);
            if (empl.numOfStatements() > 0) {
                System.out.println("\t\t_._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._._");
                System.out.println("\t\t"+empl);
                System.out.println(empl.getEmployee_sStatements());
            }else {
                System.out.println("\t\tNothing yet"); //an h lista kinhsewn einai kenh
            }
        }//procedure07


        static void procedure08(){
            System.out.println("\t>Compensate all employees for their expenses");

            System.out.print("\t\tAre you sure you want to continue? 1.Yes  2.No : ");
            int answer = inputValidityInt(input.next(), 2);
            if (answer == 2 || answer == -1){ return; }//epistrofh sto kentriko menou

            for (int i=0; i<employees.numOfEmployees(); i++){
                Clearance(employees.getEmployee(i)); //ekka8arish gia ka8e ergazomeno
            }
            System.out.println("\t\t\tOperation successfully completed");
        }//procedure08


        static void procedure09(){
            System.out.println("\t>Show the final compensation of all employees\n");
            double sum = 0;
            for(int i=0; i<employees.numOfEmployees(); i++){
                Employee empl = employees.getEmployee(i);
                double amount = 0;
                boolean flag = false;
                int j=0;
                while ( j<empl.numOfStatements() && !flag){
                    //to SpecificStatment periexei to teliko poso apozhmiwhs tou ergazomenou
                    if (empl.getEmployee_sStatements().getStatement(j) instanceof SpecificStatement){
                        amount = empl.getEmployee_sStatements().getStatement(j).getAmount();
                        flag = true;
                    }
                    j++;
                }
                System.out.print("\t\t"+empl.getFirstName()+" "+empl.getLastName()+": ");
                if (flag){ //an exei ginei ekka8arish
                    System.out.println("The estimated compensation is "+(Math.round(amount * 100))/100.0);
                }else{
                    System.out.println("No clearance has occurred");
                }
                sum += amount;
            }
            //emfanish synolikou posou apozhmiwshs (gia olous tous ergazomenous)
            System.out.println("\tThe estimated total sum of compensation is "+(Math.round(sum * 100))/100.0);
        }//procedure09

        static void procedure10() {
            System.out.println("{ Note: You can enter -1 at any time to go back to the main menu }\n");
            System.out.println("\tTransfer lists of expenses or statements to text files\n");
            System.out.print("\t\tSelect an operation: " +
                    "\n\t\t\t1.Transfer list of  expenses  to text file" +
                    "\n\t\t\t2.Transfer list of statements to text file" +
                    "\n\t\t\tEnter: ");
            int op = inputValidityInt(input.next(), 2); //elegxos egkyrothtas

            if (op == -1) { return; } //epistrofh sto kentriko menou

            if (op == 1) {
                writeExpenseList("EXPENSE_LIST.txt");
            } else {
                writeStatementList("TRN_LIST.txt");
            }
        }

        static void Clearance(Employee empl){
            //diagrafh prohgoumenwn kinhsewn tou ergazomenou (ektos twn prokatavolwn)
            for(int i=empl.numOfStatements()-1 ; i >=0; i--){
                if(!(empl.getEmployee_sStatements().getStatement(i) instanceof Deposit)){
                    empl.deleteStatement(i);
                }
            }
            //diagrafh apo th genikh lista twn kinhsewn
            Statement st;
            for(int i=allStatements.numOfStatements()-1 ; i >=0; i--){
                st = allStatements.getStatement(i);
                if (st.getEmployee() == empl && !(st instanceof Deposit)){
                    allStatements.deleteStatement(i);
                }
            }

            empl.liquidation(expenseTypes); //me8odos gia apozhmiwsh-ekka8arish

            //pros8hkh twn newn kinhsewn (meta thn ekka8arish) tou ergazomenou sth genikh lista kinhsewn
            for (int i=0; i < empl.numOfStatements(); i++){
                st = empl.getEmployee_sStatements().getStatement(i);
                if (!(st instanceof  Deposit)){
                    allStatements.newStatement(st);
                }
            }

        }//Clearance


        //me8odoi egkyrothtas dedomenwn gia akeraious

        static boolean isInt(String in){
            int validIn=0;
            try{
                validIn = Integer.parseInt(in);
            }
            catch (Exception ex){
                System.err.println("Integer expected, but not found");
                return false;
            }
            return true;
        }//isInt

        //gia tis eisodous typou int apo ton xrhsth
        static int inputValidityInt(String in, int upperBound){
            boolean ok = false;
            do {
                if (in.equals("-1")){ //to -1 einai h epilogh gia epistrofh sto kentriko menou
                    ok = true;
                    break;
                }
                for (int i = 1; i <= upperBound; i++) {
                    if (in.equals(String.valueOf(i))) {
                        ok = true;
                    }
                }
                if(!ok ){
                    System.out.print("\t\t\tInvalid Input! Please enter a number between 1 and "+upperBound+": ");
                    in = input.next();
                }
            }while(!ok);

            return Integer.parseInt(in);

        }//inputValidityInt


        //gia ta dedomena typou int sta arxeia
        static boolean dataValidityInt(String data){
            if(isInt(data)){
                if(Integer.parseInt(data)>=0){
                    return true;
                }
            }
            return false;
        }//dataValidityInt


        //me8odoi egkyrothtas dedomenwn gia dekadikous

        static boolean isDouble(String in){
            double validIn;
            try{
                validIn = Double.parseDouble(in);
            }
            catch (Exception ex){
                System.err.println("Double expected, but not found");
                return false;
            }
            return true;
        }//isDouble

         //gia tis eisodous typou double apo ton xrhsth
        static double inputValidityDouble(String in){
            boolean ok = false;
            while (!ok) {
                if (isDouble(in)) {
                    //to -1 einai h epilogh gia epistrofh sto kentriko menou
                    if ((Double.parseDouble(in)>=0) || (Double.parseDouble(in)==(-1))){
                        ok = true;
                        break;
                    }else{
                        System.out.print("\t\t\tInvalid Input! Please enter a positive number: ");
                    }
                }else{
                    System.out.print("\t\t\tPlease enter a number: ");
                }
                in = input.next();
            }
            return Double.parseDouble(in);
         }//inputValidityDouble

        //gia ta dedomena typou double sta arxeia
        static boolean dataValidityDouble(String data){
            if(isDouble(data)){
                if(Double.parseDouble(data)>=0){
                    return true;
                }
            }
            return false;
        }//dataValidityDouble

        //me8odoi egkyrothtas dedomenwn gia pososta

        //gia tis eisodous typou posostou apo ton xrhsth
        static double percentageValidity(String in){
            double perc = 2; //epeidh to pososto einai <=1
            while (perc>=1 && perc!=-1){
                perc = inputValidityDouble(in);
            }
            return perc;
        }//percentageValidity

        //gia ta dedomena typou posostou sta arxeia
        static boolean dataValidityPerc(String data){
            if(isDouble(data)){
                if(Double.parseDouble(data)>=0 && Double.parseDouble(data)<=1){
                    return true;
                }
            }
            return false;
        }//dataValidityPerc



    public static void parseEmployeesList(String aFileName) {
        try {
            FileReader file = new FileReader(aFileName);
            BufferedReader buff=new BufferedReader(file);
            System.out.println("\n>File of employees opened");

            int i=0; //deikths gia ton ekastote ergazomeno sth lista, xrhsimeuei sta mhnymata sfalmatos
            StringTokenizer lineTokens;
            String token;
            String line;

            boolean eof = false;
            int code = -1;
            String surname = "";
            String firstname = "";
            double maxComp = 540; //proka8orismenh timh

            while (!eof) {
                line = buff.readLine();
                if (line == null)
                    eof = true;
                else {
                    if (line.trim().toUpperCase().equals("EMPLOYEE")) {
                        //gia thn periptwsh pou meta3u tou 'EMPLOYEE' kai ths '{' mesolavoun eite axrhsta dedomena
                        // eite h le3h 'EMPLOYEE' gia deyterh fora
                        do{
                            line = buff.readLine();
                            if (line==null){
                                break;
                            }
                        }while(!line.trim().equals("{"));

                        i++; //deikths gia ton ekastote ergazomeno sth lista

                        if (line!=null) {
                            while ( !(line.trim().equals("}")) && (!eof) ) {

                                line = buff.readLine();
                                lineTokens = new StringTokenizer(line);

                                if (lineTokens.countTokens() >= 1) { //elegxos gia to an h grammh einai kenh

                                    token = lineTokens.nextToken();

                                    if (token.toUpperCase().equals("CODE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();

                                            if(dataValidityInt(token)) {    //elegxos egkyrothtas
                                                code = Integer.parseInt(token);
                                            }else
                                                System.out.println("\tInvalid Code. Employee ["+ i +"] cannot be resolved");
                                        } else
                                            System.out.println("\tCode not found. Employee ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("SURNAME")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            surname = token.replace("\"", "");
                                        } else
                                            System.out.println("\tSurname not found. Employee ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("FIRSTNAME")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            firstname = token.replace("\"", "");
                                        } else
                                            System.out.println("\tFirstname not found. Employee ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("MAX_MONTHLY_VAL")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();

                                            if(dataValidityDouble(token)) {   //elegxos egkyrothtas
                                                maxComp = Double.parseDouble(token);
                                            }
                                        }
                                    }
                                }
                            }
                            if(code!=-1 && !surname.equals("") && !firstname.equals("")){
                                employees.newEmployee(new Employee(code, surname, firstname, maxComp));
                            }
                        }
                    }
                }
            }

            buff.close();
            file.close();
        }
        catch (IOException ex) {
            System.err.println("Error reading employees' file.");
        }
    }//parseEmployeesList

    public static void parseExpenseTypeList(String aFileName){
        try {
            FileReader file = new FileReader(aFileName);
            BufferedReader buff=new BufferedReader(file);

            System.out.println("\n>File of expense types opened");

            int i=0; //deikths gia ton ekastote typo dapanhs sth lista, xrhsimeuei sta mhnymata sfalmatos

            StringTokenizer lineTokens;
            String token;
            String line;
            ExpenseType exp = null;
            boolean eof = false;

            while (!eof) {
                line = buff.readLine();
                if (line == null)
                    eof = true;
                else {
                    if (line.trim().toUpperCase().equals("EXPENSE_TYPE")) {
                        //gia thn periptwsh pou meta3u tou 'EXPENSE_TYPE' kai ths '{' mesolavoun eite axrhsta dedomena
                        // eite h le3h 'EXPENSE_TYPE' gia deyterh fora
                        do{
                            line = buff.readLine();
                            if (line==null){
                                break;
                            }
                        }while(!line.trim().equals("{"));

                        i++; //deikths gia ton ekastote typo dapanhs sth lista

                        if (line!=null) {
                            buff.mark(2048);
                            while ( !(line.trim().equals("}")) && (!eof) ) {
                                line = buff.readLine();
                                lineTokens = new StringTokenizer(line);

                                if (lineTokens.countTokens() >= 1) { //elegxos gia to an h grammh einai kenh

                                    token = lineTokens.nextToken();

                                    if (token.toUpperCase().equals("TYPE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();

                                            //kaleitai h me8odos pou antistoixei ston typo gia na synexistei h anagnwsh toy arxeiou
                                            if (token.equals("1"))
                                                exp = ExpenseType1.parse(buff, i);
                                            else if (token.equals("2"))
                                                exp = ExpenseType2.parse(buff, i);
                                            else
                                                System.out.println("\tInvalid type. Expense type ["+ i +"] cannot be resolved");
                                        } else
                                            System.out.println("\tType not found. Expense type ["+ i +"] cannot be resolved");
                                        break;
                                    }
                                }
                            }
                            if (exp!=null){
                                expenseTypes.newExpenseType(exp);
                            }
                        }
                    }
                }
            }

            buff.close();
            file.close();
        }
        catch (IOException ex) {
            System.err.println("Error reading expense types' file.");
        }
    }//parseExpenseTypeList


    public static void parseExpenseList(String aFileName) {
        try {
            FileReader file = new FileReader(aFileName);
            BufferedReader buff=new BufferedReader(file);

            System.out.println("\n>File of expenses opened");

            int i=0; //deikths gia ton ekastote typo dapanhs sth lista, xrhsimeuei sta mhnymata sfalmatos

            StringTokenizer lineTokens;
            String token;
            String line;

            boolean eof = false;
            SpecificExpense specExp = null;

            while (!eof) {
                line = buff.readLine();
                if (line == null)
                    eof = true;
                else {
                    if (line.trim().toUpperCase().equals("EXPENSE")) {
                        //gia thn periptwsh pou meta3u tou 'EXPENSE' kai ths '{' mesolavoun eite axrhsta dedomena
                        // eite h le3h 'EXPENSE' gia deyterh fora
                        do{
                            line = buff.readLine();
                            if (line==null){
                                break;
                            }
                        }while(!line.trim().equals("{"));

                        i++;//deikths gia ton ekastote typo dapanhs sth lista

                        if (line!=null) {

                            int emplCode = -1;
                            Employee empl = null;
                            int position = -1;
                            int expCode = -1;
                            ExpenseType type = null;
                            int expType = -1;
                            double val = -1;
                            String just = "No justification"; //proka8orismenh timh

                            while ( !(line.trim().equals("}")) && (!eof) ) {

                                line = buff.readLine();
                                lineTokens = new StringTokenizer(line);

                                if(lineTokens.countTokens()>=1) { //elegxos gia to an h grammh einai kenh
                                    token = lineTokens.nextToken();

                                    if (token.toUpperCase().equals("EMPLOYEE_CODE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            if(dataValidityInt(token)) {   //elegxos egkyrothtas
                                                emplCode = Integer.parseInt(token);
                                            }else
                                                System.out.println("\tInvalid Employee code. Expense ["+ i +"] cannot be resolved");

                                            //entopismos ths 8eshs tou ergazomenou sth lista
                                            position = employees.findEmployeeByCode(emplCode);
                                            if (position != -1) {
                                                empl = employees.getEmployee(position);
                                            } else
                                                System.out.println("\tNo employee has this code. Expense ["+ i +"] cannot be resolved");

                                        } else
                                            System.out.println("\tEmployee Code not found. Expense ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("EXPENSE_TYPE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            if (token.equals("1") || token.equals("2")) {
                                                //an exei hdh bre8ei to EXPENSE_CODE, elegxetai h antistoixia tou EXPENSE_TYPE me ayto
                                                if ((type != null && type.getIntType() == Integer.parseInt(token)) || type == null) {
                                                    expType = Integer.parseInt(token);
                                                } else
                                                    System.out.println("\tExpense type not in accordance with Expense Code. Expense ["+ i +"] cannot be resolved");
                                            } else
                                                System.out.println("\tInvalid Expense type. Expense ["+ i +"] cannot be resolved");
                                        } else
                                            System.out.println("\tExpense type not found. Expense ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("EXPENSE_CODE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();

                                            if(dataValidityInt(token)) {     //elegxos egkyrothtas
                                                expCode = Integer.parseInt(token);

                                                //entopismos ths 8eshs tou typou dapanhs sth lista
                                                position = expenseTypes.findExpTypeByCode(expCode);

                                                if (position != -1) {

                                                    //an exei hdh bre8ei to EXPENSE_TYPE, elegxetai h antistoixia tou EXPENSE_CODE me ayto
                                                    if ((expType != -1 && expenseTypes.getExpenseType(position).getIntType() == expType) || expType == -1) {
                                                        type = expenseTypes.getExpenseType(position);
                                                    } else
                                                        System.out.println("\tExpense type not in accordance with Expense Code. Expense ["+ i +"] cannot be resolved");
                                                } else
                                                    System.out.println("\tNo expense type has this code. Expense ["+ i +"] cannot be resolved");
                                            }else
                                                System.out.println("\tInvalid Expense Code. Expense ["+ i +"] cannot be resolved");
                                        } else
                                            System.out.println("\tExpense Code not found. Expense ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("VAL")) {
                                        if (lineTokens.countTokens() >= 1) {

                                            token = lineTokens.nextToken();

                                            if(dataValidityDouble(token)) {   //elegxos egkyrothtas
                                                val = Double.parseDouble(token);
                                            }else
                                                System.out.println("\tInvalid Value/Quantity. Expense ["+ i +"] cannot be resolved");
                                        } else
                                            System.out.println("\tValue/Quantity not found. Expense ["+ i +"] cannot be resolved");

                                    } else if (token.toUpperCase().equals("JUSTIF")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            just = token;
                                        }
                                    }
                                }
                            }
                            if(empl!= null && type!=null && val!=-1 && expType!=-1){
                               specExp = new SpecificExpense(empl, type, val, just);
                               allExpenses.newExpense(specExp);
                               empl.newExpense(specExp);
                            }
                        }
                    }
                }
            }

            buff.close();
            file.close();
        }
        catch (IOException ex) {
            System.err.println("Error reading expenses' file.");
        }
    }//parseExpenseList


    public static void parseStatementList(String aFileName){
        try {
            FileReader file = new FileReader(aFileName);
            BufferedReader buff = new BufferedReader(file);

            System.out.println("\n>File of statements opened");

            int i=0; //deikths gia ton ekastote typo dapanhs sth lista, xrhsimeuei sta mhnymata sfalmatos

            StringTokenizer lineTokens;
            String token;
            String line;
            boolean eof = false;
            Statement st = null;

            while (!eof) {
                line = buff.readLine();
                if (line == null)
                    eof = true;
                else {
                    if (line.trim().toUpperCase().equals("TRN")) {

                        //gia thn periptwsh pou meta3u tou 'TRN' kai ths '{' mesolavoun eite axrhsta dedomena
                        // eite h le3h 'TRN' gia deyterh fora
                        do{
                            line = buff.readLine();
                            if (line==null){
                                break;
                            }
                        }while(!line.trim().equals("{"));

                        i++;//deikths gia ton ekastote typo dapanhs sth lista

                        if (line!=null) {
                            buff.mark(2048);
                            String stType = "";

                            while (!(line.trim().equals("}")) && (!eof)) {
                                line = buff.readLine();
                                lineTokens = new StringTokenizer(line);

                                if(lineTokens.countTokens()>=1){ //elegxos gia to an h grammh einai kenh
                                    token = lineTokens.nextToken();

                                    if (token.toUpperCase().equals("TYPE")) {
                                        if (lineTokens.countTokens() >= 1) {
                                            token = lineTokens.nextToken();
                                            if (token.toUpperCase().equals("\"PROKATAVOLI\"") ||
                                                    token.toUpperCase().equals("\"APOZIMIOSI\"") ||
                                                    token.toUpperCase().equals("\"DIAFORA\"") ||
                                                    token.toUpperCase().equals("\"FINAL_APOZIMIOSI\"")) {
                                                stType = token.toUpperCase().replace("\"", "");

                                            } else {
                                                System.out.println("\tInvalid statement type. Statement ["+ i +"] cannot be resolved");
                                            }
                                            if (!stType.equals("")) {
                                                //kaleitai h parse() ths Statement gia na synexistei to parsing
                                                st = Statement.parse(stType, buff, i, employees, expenseTypes);
                                            }
                                        } else{
                                            System.out.println("\tStatement type not found. Statement ["+ i +"] cannot be resolved");
                                        }
                                        break;
                                    }
                                }
                            }
                            if (!stType.equals("") && st!=null) {
                                st.getEmployee().newStatement(st);
                                allStatements.newStatement(st);
                            }
                        }
                    }
                }
            }

            buff.close();
            file.close();
        }
        catch (IOException ex) {
            System.err.println("Error reading statements' file.");
        }
    }//parseStatementList


    public static void writeExpenseList(String aFileName){
        try	{
            FileWriter file = new FileWriter(aFileName);
            BufferedWriter buff = new BufferedWriter(file);

            buff.write("EXPENSE_LIST"+"\n"+"{"+"\n");
            SpecificExpense exp;

            for (int i=0; i<allExpenses.numOfExpenses(); i++){
                exp = allExpenses.getSpecificExpense(i);
                buff.write ("\tEXPENSE"+ "\n"+
                              "\t{"+"\n"+
                              "\t\t"+"EMPLOYEE_CODE "+exp.getEmployee().getCode()+ "\n"+
                              "\t\t"+"EXPENSE_TYPE "+ exp.getSpecificType().getIntType()+ "\n"+
                              "\t\t"+"EXPENSE_CODE "+ exp.getSpecificType().getCode()+ "\n"+
                              "\t\t"+"VAL "	+ exp.getValueQuantity() + "\n"+
                              "\t\t"+"JUSTIF "	+ exp.getJustification()+"\n"+
                              "\t}"+"\n");
            }
            buff.write("}");
            buff.close();
            file.close();
            System.out.println("Expenses successfully transferred to file");

        }

        catch (IOException e) {
            System.err.println("Error writing expenses' file.");
        }
    }//writeExpenseList


    public static void writeStatementList(String aFileName){

    try	{
            FileWriter file = new FileWriter(aFileName);
            BufferedWriter buff = new BufferedWriter(file);

            buff.write("TRN_LIST"+"\n"+"{"+"\n");
            Statement st;
            for (int i=0; i<allStatements.numOfStatements(); i++){
                st = allStatements.getStatement(i);
                buff.write ("\tTRN"+ "\n"+
                                "\t{"+"\n"+
                                "\t\t"+"EMPLOYEE_CODE "+st.getEmployee().getCode()+ "\n");

                if (st instanceof Deposit){
                    buff.write("\t\t"+"TYPE "+"\"PROKATAVOLI\""+"\n");

                } else if( st instanceof Compensation){
                    buff.write("\t\t"+"TYPE "+"\"APOZIMIOSI\""+"\n"+
                                   "\t\t"+"EXPENSE_TYPE "+((Compensation)st).getType().getIntType()+"\n"+
                                   "\t\t"+"EXPENSE_CODE "+((Compensation)st).getType().getCode()+"\n");

                }else if( st instanceof Difference){
                    buff.write("\t\t"+"TYPE "+"\"DIAFORA\""+"\n");

                }else {
                    buff.write("\t\t"+"TYPE "+"\"FINAL_APOZIMIOSI\""+"\n");
                }
                if(st instanceof SpecificStatement) {
                    buff.write("\t\t" + "VAL " + ((Math.round(st.getAmount() * 100)) / 100.0) + "\n" +
                            "\t}" + "\n");
                }else{
                    buff.write("\t\t" + "VAL " + (Math.abs((Math.round(st.getAmount() * 100))) / 100.0) + "\n" +
                            "\t}" + "\n");
                }
            }
            buff.write("}");
            buff.close();
            file.close();
            System.out.println("Statements successfully transferred to file");
        }

        catch (IOException e) {
            System.err.println("Error writing statements' file.");
        }
    }//writeStatementList

    }//mainApp