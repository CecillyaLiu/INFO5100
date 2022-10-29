/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DataFilesGUI {

    public void buildGUI(){ // this is the GUI interface class
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(950, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JLabel readFile_label = new JLabel("Reading File Name");
        readFile_label.setBounds(45, 20, 200, 25);
        panel.add(readFile_label);

        JLabel readFile_label_name = new JLabel("annual.csv");
        readFile_label_name.setBounds(45, 45, 200, 25);
        panel.add(readFile_label_name);

        JLabel readFile_label_first = new JLabel("First five lines of file");
        readFile_label_first.setBounds(45, 115, 200, 25);
        panel.add(readFile_label_first);

        JTable table1 = new JTable();

        JTextArea field1 = new JTextArea();
        field1.setBounds(45, 140, 400, 350);
        field1.setLineWrap(true); // must use this command or the "\n" won't work
        panel.add(field1);

        JButton button1 = new JButton("Click to read from file");
        button1.setBounds(40, 80, 200, 25);
        button1.setForeground(Color.BLUE);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // use loop to get the five string lines and use "\n" to change line
                try {
                    for (int i = 0; i < 5; i++) {
                        field1.setText(field1.getText() + Method.readCsv1() + "\n");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        panel.add(button1);

        // right part
        JLabel writeFile_label = new JLabel("File name to write");
        writeFile_label.setBounds(480, 20, 200, 25);
        panel.add(writeFile_label);

        JTextField writeFile_input = new JTextField();
        writeFile_input.setBounds(480, 45, 200, 25);
        panel.add(writeFile_input);

        JLabel writeFile_first = new JLabel("First five lines of new file");
        writeFile_first.setBounds(480, 115, 200, 25);
        panel.add(writeFile_first);

        JTextArea field2 = new JTextArea();
        field2.setBounds(480, 140, 400, 350);
        field2.setLineWrap(true);

        panel.add(field2);

        JButton button2 = new JButton("Click to write the file");
        button2.setBounds(475, 80, 200, 25);
        button2.setForeground(Color.BLUE);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Method.setNewFile(writeFile_input.getText()); // to set the input name as the new file name
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    Method.writeFile(field1.getText()); // to let the first five lines to be a new file
                    Method.createFile(); // create the third file
                    for (int i = 0; i < 5; i++) { // show the third file
                        field2.setText(field2.getText() + Method.readCsv2() + "\n");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(button2);

        frame.setVisible(true);
        }

}
