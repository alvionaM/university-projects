import java.io.*;
import java.util.StringTokenizer;

public class NetBenefit {

    private static final IntQueueImpl<Integer> intQueue = new IntQueueImpl<>();
    private static boolean fileIsOkay = true;
    private static boolean insufficientShares = false;
    private static boolean negativeValue = false;
    private static String endingConditionToken = "";

    public static void main(String[] args) {
        String file = args[0];

        System.out.println("\n\tStarting stocks file parsing...");
        int total = parse_Shares_File(file);

        if (fileIsOkay) {
            System.out.println("\t...\n\t...\n\t...");

            if (insufficientShares) {
                System.out.println("\nNot sufficient shares; transaction cannot occur!\n"+
                                "Ending NetBenefit...\n");
                return;
            }

            if (negativeValue) {
                System.out.println("\nFound invalid (negative) value in txt file!\n" +
                        "Ending NetBenefit...\n");
                return;
            }
            
            if (total > 0)
                System.out.println("\tProfit:   "+total+" $");
            else if (total < 0)
                System.out.println("\tLoss:   "+(-total)+" $");
            else
                System.out.println("\tNo profit/loss:   0 $");

            
            if (endingConditionToken.equals("BUY"))
                System.out.println("\nWarning; last operation cannot be a \"BUY\" transaction!");
        }
        System.out.println();

    }

    /**
     *
     * @param fileName file's local address
     * @return total: final profit or loss
     */
    private static int parse_Shares_File(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(fr);

            StringTokenizer lineTokens;
            String token;
            String line;
            boolean eof = false;
            int total = 0;      // final profit or loss
            int dispShares = 0;

            while (!eof) {
                line = buff.readLine();

                if (line == null) {
                    eof = true;


                } else {
                    lineTokens = new StringTokenizer(line);

                    int price = 0;
                    int sharesToBuy = -1, sharesToSell = -1;
                    while (lineTokens.hasMoreTokens()) {
                        token = lineTokens.nextToken();

                        if (token.toUpperCase().equals("PRICE")) {
                            token = lineTokens.nextToken();

                            price = Integer.parseInt(token);
                            if(price<0){
                                negativeValue = true;
                                return total;
                            }
                        } else if (token.toUpperCase().equals("BUY")) {
                            endingConditionToken = token.toUpperCase();
                            token = lineTokens.nextToken();
                            
                            sharesToBuy = Integer.parseInt(token);
                            if(sharesToBuy<0){
                                negativeValue = true;
                                return total;
                            }
                        } else if (token.toUpperCase().equals("SELL")) {
                            endingConditionToken = token.toUpperCase();
                            token = lineTokens.nextToken();

                            sharesToSell = Integer.parseInt(token);
                            if(sharesToSell<0){
                                negativeValue = true;
                                return total;
                            }
                        }
                    }

                    // BUY CASE
                    /* 1st idea
                    if (sharesToBuy > 0) {
                        // sharesToBuy nodes = # of inserted nodes
                        for (int i = 1; i <= sharesToBuy; i++) {
                            intQueue.put(price);
                        }
                    }*/

                    //  2nd idea
                    if (sharesToBuy > 0){   //if not, then sharesToBuy==-1 or else the program would have terminated previously  
                        intQueue.put(sharesToBuy);  //pieces of this set
                        intQueue.put(price);        //price of this set of shares

                        dispShares = dispShares + sharesToBuy;
                    }

                    //SELL CASE
                    /*  1st idea
                    else if (sharesToSell > 0) {
                        if (sharesToSell > intQueue.size()) {
                            insufficientShares = true;
                            buff.close();
                            fr.close();
                            return total;
                        }

                        else
                            for (int i = 1; i <= sharesToSell; i++) {
                                total = total + 1 * (price - intQueue.peek());
                                intQueue.get();
                            }
                    }
                    */
                    //  2nd idea
                    else if (sharesToSell > 0) {  //if not, then sharesToSell==-1 or else the program would have terminated previously  
                        
                        if (dispShares >= sharesToSell) {
                            int temp = sharesToSell;

                            while ( !intQueue.isEmpty() && temp >= intQueue.peek() ) {
                                int currShares = intQueue.get();

                                temp = temp - currShares;
                                total = total + currShares * (price - intQueue.get());
                            }

                            if (temp > 0) {
                                int currHeadData = intQueue.getHead().getData();    //currHeadData > temp
                                intQueue.getHead().setData(currHeadData - temp);
                                total = total + temp * (price - intQueue.getHead().getNext().getData());
                                temp = 0;

                            }
                            dispShares = dispShares - sharesToSell;

                        } else {
                            insufficientShares = true;
                            buff.close();
                            fr.close();
                            return total;
                        }
                    }
                }


            }
            buff.close();
            fr.close();

            return total;
        } catch (IOException ex) {
            System.err.println("\n\t\tError: file could not be parsed!");
            fileIsOkay = false;
            return -1;
        }
    }
}
