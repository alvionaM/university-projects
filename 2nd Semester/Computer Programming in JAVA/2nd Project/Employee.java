import java.util.ArrayList;
import java.util.Random;


public  class Employee {
    private final int code;
    private final String lastname;
    private final String firstname;
    private final double genMaxComp;
    private final SpecificExpenseList employee_sExpenses;
    private final StatementList employee_sStatements;

    static ArrayList<Integer> allCodes = new ArrayList<>(); //sygkratei tous kwdikous gia na ginei o elegxos

    public Employee(String lastname, String firstname, double genMaxComp) {
        //Paragwgh neou tyxaiou 5pshfiou kwdikou
        Random ran = new Random();
        int i;
        do {
            i = 20000 + ran.nextInt(10000);
        } while (allCodes.contains(i));
        allCodes.add(i);

        this.code = i;
        this.lastname = lastname;
        this.firstname = firstname;
        this.genMaxComp = genMaxComp;
        this.employee_sExpenses = new SpecificExpenseList();
        this.employee_sStatements = new StatementList();
    }

    public Employee(int code, String lastname, String firstname, double genMaxComp) {
        this.code = code;
        this.lastname = lastname;
        this.firstname = firstname;
        this.genMaxComp = genMaxComp;
        this.employee_sExpenses = new SpecificExpenseList();
        this.employee_sStatements = new StatementList();
    }

    public String getLastName() {
        return lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public double getGenMaxComp() {
        return genMaxComp;
    }

    public int getCode() {
        return code;
    }

    public SpecificExpenseList getEmployee_sExpenses() {
        return employee_sExpenses;
    }

    public StatementList getEmployee_sStatements() {
        return employee_sStatements;
    }


    public int numOfExpenses() {
        return employee_sExpenses.numOfExpenses();
    }

    public int numOfStatements() {
        return employee_sStatements.numOfStatements();
    }

    public void newExpense(SpecificExpense exp) {
        employee_sExpenses.newExpense(exp);
    }

    public void newStatement(Statement st) {
        employee_sStatements.newStatement(st);
    }

    public void deleteStatement(int i) {
        employee_sStatements.deleteStatement(i);
    }


    private double sumOfCompensations(ExpenseTypeList expenseTypes) { //xrhsimopoieitai sth liquidation
        boolean flag;
        ExpenseType type;
        //elegxos gia ka8e typo dapanhs an o ergazomenos exei dapanes autou tou typou
        for (int i = 0; i < expenseTypes.numOfExpTypes(); i++) {
            type = expenseTypes.getExpenseType(i);
            flag = false;
            int j = 0;
            while (j < this.numOfExpenses() && (!flag)) {
                if (type == this.getEmployee_sExpenses().getSpecificExpense(j).getSpecificType()) {
                    flag = true;
                }
                j++;
            }
            //dhmiourgia kinhsewn apozhmiwshs gia ka8e typo dapanhs
            if (flag) {
                Compensation compSt = new Compensation(this, type);
                this.newStatement(compSt);
            }
        }
        //ypologismos tou synolou twn kinhsewn apozhmiwhs
        double sum = 0.0;
        for (int i = 0; i < employee_sStatements.numOfStatements(); i++) {
            Statement item = employee_sStatements.getStatement(i);
            if (item instanceof Compensation) {
                sum += item.getAmount();
            }
        }
        return sum;
    }


    private void calcDiff(double sum) { //xrhsimopoieitai sth liquidation
        //ypologismos kai dhmiourgia kinhshs diaforas
        double diff = sum - this.getGenMaxComp();
        if (diff > 0) {
            Difference diffStatement = new Difference(this, diff);
            this.newStatement(diffStatement);
        }
    }

    public void liquidation(ExpenseTypeList expenseTypes) { //kaleitai sthn Clearance tou mainApp (gia thn ekka8arish)
        double sum = sumOfCompensations(expenseTypes);
        calcDiff(sum);
        double sumGeneral = 0.0;
        for (int i = 0; i < employee_sStatements.numOfStatements(); i++) {
            Statement item = employee_sStatements.getStatement(i);
            sumGeneral += item.getAmount();
        }
        //dhmiourgia eidikhs kinhshs
        SpecificStatement specStatement = new SpecificStatement(this, sumGeneral);
        this.newStatement(specStatement);
    }


    public String toString() {
        return firstname + " " + lastname + " | Maximum possible Compensation (per month): " + genMaxComp;
    }
}