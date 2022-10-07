import java.util.Scanner;
/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
public class Main {
    public static void main(String[]args){
        WeightedGrades ceci = new WeightedGrades(); // My preferred name is Cecillya and ceci is just what my friends usually call me.
        int c = 23;
        System.out.print(c);
        Scanner input = new Scanner(System.in); // Use scanner to get what is being input.
        System.out.println("Please input the total point: ");
        ceci.setPointTotal(input.nextInt()); // directly use method to set the input value to be total point; int type
        System.out.println("Please input the earned points: ");
        ceci.setEarnedPoints(input.nextInt()); //earned point is int type
        System.out.println("Please input the assignment percent: ");
        ceci.setAssignmentPercent(input.nextDouble()); // assignment percent is double type
        double weightedGrade = ceci.getTotalGrades(ceci.getPointTotal(), ceci.getEarnedPoints(), ceci.getAssignmentPercent()); // calculate the final grades
        System.out.println("Success! The weighted grade is = " + ceci.setDecimal(weightedGrade, 3)); // use another method to keep 3 decimal places

    }
}
