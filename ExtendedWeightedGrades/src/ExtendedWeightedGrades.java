import java.util.LinkedList;
import java.util.Scanner;

/*
Name: Yixuan Liu (Cecillya)
NUID: 002746822
 */
public class ExtendedWeightedGrades {
    LinkedList<Integer> totalPoints = new LinkedList<Integer>();
    LinkedList<Integer> percents = new LinkedList<Integer>();
    LinkedList<Integer> earnedPoints = new LinkedList<Integer>();
    double calculatedPoint = 0;
    String grade;
    Boolean status_totalPoints = false;
    Boolean status_earnedPoints = false;
    Boolean status_percents = false;

    // methods for total points, including setting, getting, changing status
    public void begin_totalPoints(){ // set the status to be true to be editable
        status_totalPoints = true;
    }

    public void end_totalPoints(){ // set the status to be false to be uneditable or recalled
        status_totalPoints = false;
    }

    public void set_single_TotalPoints(int item) { // change the single item in the linked list
        totalPoints.add(item);
    }

    public void setTotalPoints(Scanner lab){ // use a loop to set the value until the user input -1
        while (true) {
            int userInput = lab.nextInt();
            if (userInput == -1) {
                end_totalPoints(); // indicate that inputting tota points is end, revocation won't change that.
                break;
            } else if (userInput == -2) {
                revocation();
            } else {
                set_single_TotalPoints(userInput);
            }
        }
    }

    public LinkedList<Integer> getTotalPoints() {
        return totalPoints;
    }

    // methods for earned points, including setting, getting, changing status
    public void begin_earnedPoints(){ // set the status to be true to be editable
        status_earnedPoints = true;
    }

    public void end_earnedPoints(){ // set the status to be false to be uneditable or recalled
        status_earnedPoints = false;
    }

    public void set_single_EarnedPoints(int item) { // change the single item in the linked list
        earnedPoints.add(item);
    }

    public void setEarnedPoints(Scanner lab){ // use a loop to set the value until the user input -1
        while (true) {
            int userInput = lab.nextInt();
            if (userInput == -1) {
                end_earnedPoints();
                break;
            } else if (userInput == -2) {
                revocation();
            } else {
                set_single_EarnedPoints(userInput);
            }
        }
    }

    public LinkedList<Integer> getEarnedPoints() {
        return earnedPoints;
    }

    // methods for percents, including setting, getting, changing status
    public void begin_Percents(){ // set the status to be true to be editable
        status_percents = true;
    }

    public void end_Percents(){ // set the status to be false to be uneditable or recalled
        status_earnedPoints = false;
    }

    public void set_single_Percents(int item) { // change the single item in the linked list
        percents.add(item);
    }

    public void setPercents(Scanner lab){ // use a loop to set the value until the user input -1
        while (true) {
            int userInput = lab.nextInt();
            if (userInput == -1) {
                end_Percents();
                break;
            } else if (userInput == -2) {
                revocation();
            } else {
                set_single_Percents(userInput);
            }
        }
    }

    public LinkedList<Integer> getPercents() {
        return percents;
    }

    // method of revocation, in case that the number inputted is wrong
    public void revocation(){ // since only one status variable could be true, then if user inputs -2, delete the last item whose status is ture
        if (status_percents) {
            percents.remove(percents.size() - 1);
        } else if (status_totalPoints) {
            totalPoints.remove(totalPoints.size() - 1);
        } else {
            earnedPoints.remove(earnedPoints.size() - 1);
        }
    }

    // method to set the calculated points
    public void setCalculatedPoint(LinkedList<Integer> totalPoints, LinkedList<Integer> earnedPoints, LinkedList<Integer> percents){
        for (int i = 0; i < 8; i++) {
            calculatedPoint += (double) earnedPoints.get(i) / totalPoints.get(i) * percents.get(i);
        }
    }

    // method to get the calculated points
    public double getCalculatedPoint (LinkedList<Integer> totalPoints, LinkedList<Integer> earnedPoints, LinkedList<Integer> percents) {
        setCalculatedPoint(totalPoints, earnedPoints, percents);
        return calculatedPoint;
    }

    // methods to set and get grade
    public void setGrade(double calculatedPoint) {
          if (calculatedPoint >= 90) {
           grade = "A, Congratulations!";
        } else if (calculatedPoint < 90 && calculatedPoint >= 80){
            grade = "B, you got the brother of 8!";
        } else if (calculatedPoint < 80 && calculatedPoint >= 70){
            grade = "C, you need work harder~";
        } else if (calculatedPoint < 70 && calculatedPoint >= 60) {
            grade = "D, what are you doing this semester?";
            } else {
            grade = "F, sorry about that";
        }
    }
    public String getGrade(double calculatedPoint) {
        setGrade(calculatedPoint);
        return grade;
    }
}


