import java.math.BigDecimal;
/*
Name: Yixuan(Cecillya) Liu
NUID: 002746822
 */
public class WeightedGrades {
    private int pointTotal;
    private int earnedPoints;
    private double assignmentPercent;
    private double totalGrades; // set the input values and result value

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

    public void setAssignmentPercent(double percent) { // set the percent, which should be between 0 and 1
        assignmentPercent = percent;
    }

    public double getAssignmentPercent(){ // get the set percent
        return assignmentPercent;
    }

    public double setDecimal(double ori, int n) { // use to set decimal places, or it will be 30.6249999999
        BigDecimal after = new BigDecimal(ori);
        return after.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double getTotalGrades(int pointTotal, int earnedPoints, double assignmentPercent){ //calculation formulation
        totalGrades = (double) earnedPoints / pointTotal * assignmentPercent * 100;
        return totalGrades;
    }

}
