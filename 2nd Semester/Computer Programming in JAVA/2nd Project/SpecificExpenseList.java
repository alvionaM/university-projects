import java.util.ArrayList;

public class SpecificExpenseList {
    private final ArrayList<SpecificExpense> SpecificExpenseList = new ArrayList<>();

    public void newExpense(SpecificExpense exp){
        SpecificExpenseList.add(exp);
    }

    public SpecificExpense getSpecificExpense(int i){return SpecificExpenseList.get(i);}

    public int numOfExpenses(){return SpecificExpenseList.size();}

    public double sumOfCompPerType(ExpenseType type){ //ypologizei thn apozhmiwsh gia ton typo dapanhs pou dinetai
        double sum = 0.0;
        for (SpecificExpense item: SpecificExpenseList){
            if(item.getSpecificType() == type){sum += item.calcComp();}
        }
        //elegxos me to pedio ths megisths apozhmiwshs toy typou dapanhs
        if (type.getMaxComp()>0 && sum > type.getMaxComp()){
            return type.getMaxComp();
        }
        return sum;
    }

    public String toString(){
        String typesPrinted = "\n";
        int i = 1;
        for(SpecificExpense expense: SpecificExpenseList){
            typesPrinted += "\t\t\t"+i + ". " + expense.toString() +"\n";
            i++;
        }
        return typesPrinted;
    }
    
}
