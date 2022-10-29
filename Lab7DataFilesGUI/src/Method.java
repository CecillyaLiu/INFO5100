/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
import java.io.*;
import java.util.ArrayList;

public class Method {
    private static String csvFile = "annual.csv";
    private static BufferedReader ceci;
    private static BufferedReader newCsv;
    private static String head1;
    private static String newFile;

    static {
        try {
            ceci = new BufferedReader(new FileReader(csvFile)); // use BufferedReader to read csv file
            head1 = ceci.readLine(); // read the heading
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readCsv1() throws IOException { // everytime use this command will read one line of the file
        String line = ceci.readLine();
        return(line);
    }

    public static void setNewFile(String name) throws IOException { // user input
        newFile = name;
    }

    public static String readCsv2() throws IOException {
        String line = newCsv.readLine();
        return(line);
    }

    public static void writeFile(String content) throws IOException { // content is the content in the left tex box
        File writeFile = new File("annual_2.csv");
        BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
        writeText.write(head1);
        writeText.newLine();
        writeText.write(content);
        writeText.close();
    }

    public static void createFile() throws IOException {
        BufferedReader wr = new BufferedReader(new FileReader(csvFile)); // read the 5 rows 3 columns data
        ArrayList<String> fiveLines = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String[] par = wr.readLine().split(",");
            fiveLines.add(par[0] + "," + par[1] + "," + par[2]);
        }

        File writeFile = new File(newFile); // write into the third file
        BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
        for(int i = 0; i < 6; i++) {
            writeText.write(fiveLines.get(i));
            writeText.newLine();
        }
        writeText.close();

        try {
            newCsv = new BufferedReader(new FileReader(newFile)); // this part is for the next command in GUI class to read the third file
            newCsv.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
