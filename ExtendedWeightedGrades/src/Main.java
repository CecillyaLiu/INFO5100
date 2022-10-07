/*
Name: Yixuan Liu (Cecillya)
NUID: 002746822
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExtendedWeightedGrades ceci = new ExtendedWeightedGrades(); // ceci is my nickname
        Scanner lab3 = new Scanner(System.in);
        System.out.println("Please input the Total Points, type -1 to end, type -2 to recall: ");
        ceci.begin_totalPoints(); // this is to set the status of totalPoints to be true, means that is can be changed and recalled.
        ceci.setTotalPoints(lab3);

        System.out.println("Please input the Earned Points, type -1 to end, type -2 to recall: ");
        ceci.begin_earnedPoints();  // this is to set the status of totalPoints to be true, means that is can be changed and recalled.
        ceci.setEarnedPoints(lab3);

        System.out.println("Please input the Percents, type -1 to end, type -2 to recall: ");
        ceci.begin_Percents(); // this is to set the status of totalPoints to be true, means that is can be changed and recalled.
        ceci.setPercents(lab3);

        System.out.println("Your final grade is " + ceci.getGrade(ceci.getCalculatedPoint(ceci.getTotalPoints(), ceci.getEarnedPoints(), ceci.getPercents())));


    }
}


