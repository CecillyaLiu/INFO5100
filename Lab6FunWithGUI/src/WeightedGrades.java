/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
import java.math.BigDecimal;

public class WeightedGrades {
    private int pointTotal;
    private int earnedPoints;
    private int assignmentPercent;
    private static double totalGrades; // set the input values and result value

    public void setPointTotal(int point) { // to set the total point
        pointTotal = point;
    }
    public int getPointTotal() { // to get the total point after being set
        return pointTotal;
    }

    public void setEarnedPoints(int point) { // set the earned points
        earnedPoints = point;
    }

    public int getEarnedPoints() { // get the earned points after set
        return earnedPoints;
    }

    public void setAssignmentPercent(int percent) { // set the percent, which should be between 0 and 1
        assignmentPercent = percent;
    }

    public double getAssignmentPercent(){ // get the set percent
        return assignmentPercent;
    }

    public static double setDecimal(double ori, int n) { // use to set decimal places, or it will be 30.6249999999
        BigDecimal after = new BigDecimal(ori);
        return after.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getTotalGrades(int pointTotal, int earnedPoints, int assignmentPercent){ //calculation formulation
        totalGrades = (double) earnedPoints / pointTotal * assignmentPercent;
        return totalGrades;
    }

}
