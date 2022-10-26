/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CeciGUI{
    private JFrame frame = new JFrame();;
    private JPanel panel = new JPanel();
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel finalScore;
    private JLabel finalNumber;
    private JTextField totalPoints = new JTextField(20);
    private JTextField earnedPoints = new JTextField(20);
    private JTextField percent = new JTextField(20);
    private JButton button;
    private double weightedScore;

    public void buildGUI(){

        frame.setSize(450, 260);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        label1 = new JLabel("Total Assignment Points");
        label1.setBounds(10, 20, 380, 25);
        panel.add(label1);

        totalPoints.setBounds(200, 20, 165, 25);
        panel.add(totalPoints);

        label2 = new JLabel("Earned Points");
        label2.setBounds(10, 60, 380, 25);
        panel.add(label2);

        earnedPoints.setBounds(200, 60, 165, 25);
        panel.add(earnedPoints);

        label3 = new JLabel("Percentage of Class");
        label3.setBounds(10, 100, 380, 25);
        panel.add(label3);

        percent.setBounds(200, 100, 165, 25);
        panel.add(percent);

        button = new JButton("Click to calculate score");
        button.setForeground(Color.blue);
        button.setBounds(10, 140, 180, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weightedScore = WeightedGrades.setDecimal(WeightedGrades.getTotalGrades(Integer.parseInt(totalPoints.getText()),
                        Integer.parseInt(earnedPoints.getText()),
                        Integer.parseInt(percent.getText())),1);
                finalScore.setText("Weighted Score:" + weightedScore);
            }
        });
        panel.add(button);

        finalScore = new JLabel();
        finalScore.setBounds(10, 180, 300, 25);
        panel.add(finalScore);
        finalScore.setText("Weighted Score");

        finalNumber = new JLabel();
        finalNumber.setBounds(10, 180, 300, 25);
        panel.add(finalScore);
        finalScore.setText("Weighted Score:");

        frame.setVisible(true);
    }

}
