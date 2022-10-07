public class ExtendedWeightedGrades {
    private int[] totalPoints = new int[8];
    private int[] earnedPoints = new int[8];
    private int[] percents = new int[8]; // lab3 is different from lab2 since percents are integers not doubles.
    private double calculatedPoint = 0; // assume it to be zero for cumulative calculation
    private String grade;

    public void setTotalPoints(int[] input) {
        totalPoints = input;
    }

    public int[] getTotalPoints() {
        return totalPoints;
    }

    public void setEarnedPoints(int[] input) {
        earnedPoints = input;
    }

    public int[] getEarnedPoints(){
        return earnedPoints;
    }

    public void setPercents(int[] input) {
        percents = input;
    }

    public int[] getPercents(){
        return percents;
    }

    public void setCalculatedPoint(int[] totalPoints, int[] earnedPoints, int[] percents){ // to set the calculated points
        for (int i = 0; i < 8; i++) {
            calculatedPoint += (double) earnedPoints[i] / totalPoints[i] * percents[i];
        }
    }
    public double getCalculatedPoint (int[] totalPoints, int[] earnedPoints, int[] percents) {
        setCalculatedPoint(totalPoints, earnedPoints, percents);
        return calculatedPoint;
    }

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
/*
    This method is used to convert string to int[]. Since the data read by scanner's next line() is of type String,
    it needs to be converted to string[] and then to int[].
 */
    public int[] StringToIntArray(String input) {
        String[] str = input.split(" ");
        int[] intArray = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            intArray[i] = Integer.parseInt(str[i]);
        }
        return intArray;
    }
}
