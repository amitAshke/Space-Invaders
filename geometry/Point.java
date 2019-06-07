package geometry;

/**
 * The class represents a single point in a 2D plain with 2 numeric variables.
 */
public class Point {
    // A numeric variable representing an value on an X axis.
    private double x;
    // A numeric variable representing an value on an Y axis.
    private double y;

    /**
     * A constructor function that assign the values 'x' and 'y' to the members respectively.
     *
     * @param x a numeric value to be put in 'x'.
     * @param y a numeric value to be put in 'y'.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The function returns the value of the member 'x'.
     *
     * @return the value of the member 'x'.
     */
    public double getX() {
        return this.x;
    }

    /**
     * The function returns the value of the member 'y'.
     *
     * @return the value of the member 'y'.
     */
    public double getY() {
        return this.y;
    }

    /**
     * //The function calculates the distance between the point that called the function and the point that was sent
     * to the function and return it.
     *
     * @param other a point to which a distance is calculated in relation to the point that called for the function.
     * @return the distance between the point that called the function and the
     * point that was sent to the function.
     */
    public double distance(Point other) {
        // The difference between the the 'x' values of each point.
        double xDistance = this.x - other.getX();
        // The difference between the the 'y' values of each point.
        double yDistance = this.y - other.getY();
        // Calculates and returns the distance between the two points.
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }

    /**
     * The function checks if two number are different by 0.001 and returns a boolean variable accordingly.
     *
     * @param num1 the first number in the comparison.
     * @param num2 the second number in the comparison.
     * @return true or false based on the comparison mentioned earlier.
     */
    public boolean isAlmostEqual(double num1, double num2) {
        return Math.abs(num2 - num1) < 0.001;
    }

    /**
     * The function compare two "Point" variable and return true if the 'x' and 'y' values are differentiated by 0.001.
     *
     * @param other a 'Point" variable to make the comparison.
     * @return true or false based on the comparison.
     */
    public boolean isSame(Point other) {
        return ((isAlmostEqual(this.x, other.getX())) && (isAlmostEqual(this.y, other.getY())));
    }

    /**
     * The function compare two "Point" variable and return true if either the 'x' and 'y' values are differentiated by
     * more than 0.001.
     *
     * @param other a 'Point" variable to make the comparison.
     * @return true or false based on the comparison.
     */
    public boolean isDifferent(Point other) {
        return (!isAlmostEqual(this.x, other.getX()) || !isAlmostEqual(this.y, other.getY()));
    }
}
