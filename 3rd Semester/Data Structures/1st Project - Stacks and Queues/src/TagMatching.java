import java.io.*;
import java.util.StringTokenizer;

public class TagMatching {

    private static final StringStackImpl<String> tagStack = new StringStackImpl<>();
    private static boolean fileIsOkay = true;

    public static void main(String[] args){
        String file = args[0];

        System.out.println("\n\tStarting HTML file parsing...");
        boolean okay = parse_HTML_File(file);

        if (fileIsOkay) {
            System.out.println("\t...\n\t...\n\t...");
            if (okay)
                System.out.println("\tAll tags matching properly!");
            else
                System.out.println("\tTags are not correctly placed!");
        }
        System.out.println();
    }

    /**
    *
    * @param fileName file's local address
    * @return true if tags are placed correctly - no missing tags, false otherwise
    */
    private static boolean parse_HTML_File(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(fr);

            StringTokenizer lineTokens;
            String token;
            String line;
            boolean eof = false;
            boolean okay = true;    // true:    tags are placed correctly - no missing tags
                                    // false:   otherwise

            while (!eof) {
                line = buff.readLine();

                if (line == null) {
                    eof = true;
                } else {
                    lineTokens = new StringTokenizer(line);

                    while (lineTokens.hasMoreTokens()) {
                        token = lineTokens.nextToken();

                        String subToken; int startIndex, endIndex;
                        // subToken:    tag string "<..>" included in a token
                        // startIndex:  index of "<"
                        // endIndex:    index of ">"
                        while (token.length() > 0 && token.contains("<") && token.contains(">")) {

                            startIndex = token.indexOf("<");
                            endIndex = token.indexOf(">", startIndex);
                            if (endIndex == -1) break;  // in case ">" precedes "<" in a token


                            subToken = token.substring(startIndex, endIndex + 1).toUpperCase();
                            // all tags converted to uppercase
                            if (subToken.equals("<>"))  {
                                token = token.substring(endIndex + 1);
                                continue;
                            }

                            if (!subToken.startsWith("</")) {   // opening tag
                                tagStack.push(subToken);        // opening tag pushed in the stack
                            }

                            else {                              // ending tag
                                if (tagStack.isEmpty()) {       // empty stack -> no matching opening tag
                                    buff.close();
                                    fr.close();
                                    okay = false;
                                    return okay;
                                }

                                String stackTop = tagStack.peek();

                                if (subToken.contains(stackTop.substring(1)))   //  closing tag matches opening tag (top stack)
                                    tagStack.pop();                             //  opening tag pops out, okay = true

                                else {                                          //  closing tag not in right order / no matching opening tag
                                    buff.close();
                                    fr.close();
                                    okay = false;
                                    return okay;
                                }
                            }

                            token = token.substring(endIndex + 1);      // continue parsing
                        }
                    }
                }
            }
            buff.close();
            fr.close();

            // Checking stack after eof
            if (!tagStack.isEmpty())
                okay = false;
            return okay;

        }
        catch (IOException ex) {
            System.err.println("\n\t\tError: file could not be parsed!");
            fileIsOkay = false;
            return false;
        }

    }
}
