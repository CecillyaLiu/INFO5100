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

// THIS IS ANOTHER SOLUTION I wrote before the lecture today, since I forgot to use LinkedList.
//        ExtendedWeightedGrades ceci = new ExtendedWeightedGrades(); // ceci is my nickname
//        Scanner lab3 = new Scanner(System.in);
//        /*
//        Enter the data three times in sequence and remind the user to separate the data with Spaces.
//        In ExtendWeightedGrades, StringToIntArray() is used to convert the string read by the scanner
//        to int[] and assign values to private variables.
//         */
//        System.out.println("Please input the Total Points, use spaces to separate numbers: ");
//        ceci.setTotalPoints(ceci.StringToIntArray(lab3.nextLine()));
//        System.out.println("Please input the Earned Points, use spaces to separate numbers: ");
//        ceci.setEarnedPoints(ceci.StringToIntArray(lab3.nextLine()));
//        System.out.println("Please input the Percents, use spaces to separate numbers, note that percents are integers: ");
//        ceci.setPercents(ceci.StringToIntArray(lab3.nextLine()));
//
//        System.out.println("TOTAL weighted grade is: " + ceci.getGrade(ceci.getCalculatedPoint(ceci.getTotalPoints(),
//                ceci.getEarnedPoints(), ceci.getPercents())));
//        /*
//        running example:
//
//        Please input the total points, use spaces to separate numbers:
//        20 30 40 50 60 100 200 300
//        Please input the earned points, use spaces to separate numbers:
//        10 10 10 10 10 10 10 10
//        Please input the percents, use spaces to separate numbers, note that percents are integers:
//        10 10 10 10 10 10 15 25
//        TOTAL weighted grade is: F, sorry about that
//
//        Process finished with exit code 0
//        */
