package geometry;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;

import visuals.sprites.Sprite;

/**
 * The class represents a single line in a 2D plain with 2 "Point" variables.
 */
public class Line implements Sprite {
    // The point representing the start of the line.
    private Point start;
    // The point representing the end of the line.
    private Point end;
    private Color color;

    /**
     * A constructor function that assign the variables to the "start" and
     * "end" 'x' and 'y' respectively.
     *
     * @param start the staring point of the line.
     * @param end   the ending point of the line.
     * @param color the color of the line.
     */
    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    /**
     * A constructor function that assign the variables to the "start" and
     * "end" 'x' and 'y' respectively.
     *
     * @param x1 a numeric value to be put in the 'x' of "start".
     * @param y1 a numeric value to be put in the 'y' of "start".
     * @param x2 a numeric value to be put in the 'x' of "end".
     * @param y2 a numeric value to be put in the 'y' of "end".
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * The function returns the "Point" variable representing the start of
     * the line.
     *
     * @return the starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * The function returns the "Point" variable representing the end of
     * the line.
     *
     * @return the ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * The function return the length of the line.
     *
     * @return the numeric value of the distance between the "start" and
     * "end" points.
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * The function creates and return a "Point" variable representing
     * the middle of the line.
     *
     * @return the point in the middle of the line.
     */
    public Point middle() {
        double xMiddle = (this.start.getX() + this.end.getX()) / 2;
        double yMiddle = (this.start.getY() + this.end.getY()) / 2;
        return new Point(xMiddle, yMiddle);
    }

    /**
     * The function finds the point of intersection between the line that
     * launched the function and another line that it received.
     *
     * @param other the other line that the function received.
     * @return a "Point" variable that represent the point of intersection.
     */
    public Point intersectionWith(Line other) {
        // Calculate this "Line" variable's slope.
        double thisSlope = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        // Calculate the other "Line" variable's slope.
        double otherSlope = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());
        // Calculate the 'x' value if the lines were infinite in length.
        double xInter = (thisSlope * this.start.getX() - this.start.getY() - otherSlope * other.start.getX()
                + other.start.getY()) / (thisSlope - otherSlope);
        // Calculate the 'y' value if the lines were infinite in length.
        double yInter = thisSlope * (xInter - this.start.getX()) + this.start.getY();
        // Checks if this "Line" variable is vertical.
        if (this.start.getX() == this.end.getX()) {
            if (isBiggerOrEqual(Math.min(other.start.getX(), other.end.getX()), this.start.getX())
                    && isBiggerOrEqual(this.start.getX(), Math.max(other.start.getX(), other.end.getX()))) {
                yInter = otherSlope * (this.start.getX() - other.start.getX()) + other.start.getY();
                if (isBiggerOrEqual(Math.min(this.start.getY(), this.end.getY()), yInter)
                        && isBiggerOrEqual(yInter, Math.max(this.start.getY(), this.end.getY()))) {
                    return new Point(this.start.getX(), yInter);
                }
            }
        }
        // Checks if the other "Line" variable is vertical.
        if (other.start.getX() == other.end.getX()) {
            if (isBiggerOrEqual(Math.min(this.start.getX(), this.end.getX()), other.start.getX())
                    && isBiggerOrEqual(other.start.getX(), Math.max(this.start.getX(), this.end.getX()))) {
                yInter = thisSlope * (other.start.getX() - this.start.getX()) + this.start.getY();
                if (isBiggerOrEqual(Math.min(other.start.getY(), other.end.getY()), yInter)
                        && isBiggerOrEqual(yInter, Math.max(other.start.getY(), other.end.getY()))) {
                    return new Point(other.start.getX(), yInter);
                }
            }
        }
        // If both lines are parallel then return null.
        if (thisSlope == otherSlope) {
            return null;
        }
        // Checks if the xInter and yInter values represent a point on both lines.
        if (isBiggerOrEqual(Math.max(Math.min(this.start.getX(), this.end.getX()), Math.min(other.start.getX(),
                other.end.getX())), xInter)) {
            if (isBiggerOrEqual(Math.max(Math.min(this.start.getY(), this.end.getY()), Math.min(other.start.getY(),
                    other.end.getY())), yInter)) {
                if (isBiggerOrEqual(xInter, Math.min(Math.max(this.start.getX(),
                        this.end.getX()), Math.max(other.start.getX(),
                        other.end.getX())))) {
                    if (isBiggerOrEqual(yInter, Math.min(Math.max(this.start.getY(),
                            this.end.getY()), Math.max(other.start.getY(),
                            other.end.getY())))) {
                        // If so than return the point.
                        return new Point(xInter, yInter);
                    }
                }
            }
        }
        // Otherwise return null.
        return null;
    }

    /**
     * The function checks if the first number is either smaller, equal or at most bigger by 0.1 than the second number.
     *
     * @param num1 the first number of in the comparison.
     * @param num2 the second number in the comparison.
     * @return true or false based on the check mentioned earlier.
     */
    public boolean isBiggerOrEqual(double num1, double num2) {
        return num1 < num2 || Math.abs(num2 - num1) < 0.1;
    }

    /**
     * The function returns true if the "Line" variable the activated the function intersects with the "Line"
     * variable "other", and false otherwise.
     *
     * @param other a "Line" variable.
     * @return true or false whether the lines intersect.
     */
    public boolean isIntersecting(Line other) {
        Point inter = intersectionWith(other);
        return !(inter == null);
    }

    /**
     * The function creates a list of intersection points with a rectangle's outer lines using another function, and
     * then return the point that is closest to the starting point of the "Line" variable that called for the function.
     *
     * @param rect the "Rectangle" variable that's used in the function.
     * @return a single "Point" variable which represents the closest intersection point with the rectangles outer
     * lines.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Firstly it creates a list of intersection points with the given rectangle.
        List<Point> intersections = rect.intersectionPoints(this);
        // Checks if the first point is "null" if so then then there are no intersection points.
        if (intersections.get(0) == null) {
            return null;
        } else {
            // Otherwise use standard algorithm to find the point with which the distance from the star of the line.
            double minDistance = this.start.distance(intersections.get(0));
            Point minDistPoint = intersections.get(0);
            for (int i = 1; i < 4; i++) {
                if (intersections.get(i) != null && this.start.distance(intersections.get(i)) < minDistance) {
                    minDistPoint = intersections.get(i);
                    minDistance = this.start.distance(intersections.get(i));
                }
            }
            // Return the point that was found after the algorithm.
            return minDistPoint;
        }
    }

    /**
     * The function uses a "DrawSurface" variable to draw the line.
     *
     * @param d a "DrawSurface" variable used to draw the sprite.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(), (int) this.end.getY());
    }

    /**
     * The function activates as a single unit of time is passes. The unit of time is based on the game itself.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
    }
}