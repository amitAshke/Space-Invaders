package geometry;

import biuoop.DrawSurface;
import visuals.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * The class represents a rectangle in a 2D plain with a "Point" variable for it's upper left corner, 2 "double"
 * variable representing is't width and height.
 */
public class Rectangle implements Sprite {
    private Point upperLeft;
    private double width;
    private double height;
    private Color color;

    /**
     * A constructor function that assign the "Rectangle" variable's values.
     *
     * @param upperLeft a "Point" variable to be set as the upper left point of the rectangle.
     * @param width     a "double" variable to be set as the width of the rectangle.
     * @param height    a "double variable to be set as the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * A constructor function that assign the "Rectangle" variable's values.
     *
     * @param upperLeft a "Point" variable to be set as the upper left point of the rectangle.
     * @param width     a "double" variable to be set as the width of the rectangle.
     * @param height    a "double variable to be set as the height of the rectangle.
     * @param color     the color of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * A constructor function that receives a "Point" variable and set it up as the new upper left point of the
     * "Rectangle" variable that called for the function.
     *
     * @param newPoint a point to be set as the new upper left point.
     */
    public void setUpperLeft(Point newPoint) {
        this.upperLeft = newPoint;
    }

    /**
     * The function return the width of the "Rectangle" that called for the function.
     *
     * @return the width of the "Rectangle" that called for the function
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * The function return the height of the "Rectangle" that called for the function.
     *
     * @return the height of the "Rectangle" that called for the function
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * The function return the upper left point of the "Rectangle" that called for the function.
     *
     * @return the upper left point of the "Rectangle" that called for the function
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * The function returns the color of the rectangle.
     *
     * @return the color of the rectangle.
     */
    public Color getColor() {
        return color;
    }

    /**
     * The function receives a "Line" variable and returns a "List" of "Point" variables. The "List" contains
     * intersection points between the "Line" variable and the outer lines of the "Rectangle" variable that called
     * for the function.
     *
     * @param line a "Line" variable used to find intersections with.
     * @return a "List" of intersection points between the "Line" variable and the outer lines of the "Rectangle"
     * variable that called for the function.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList(4);
        Point[] interArray = new Point[4];
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        Line top = new Line(this.upperLeft.getX(), this.upperLeft.getY(), upperRight.getX(), upperRight.getY());
        Line left = new Line(this.upperLeft.getX(), this.upperLeft.getY(), lowerLeft.getX(), lowerLeft.getY());
        Line bottom = new Line(lowerRight.getX(), lowerRight.getY(), lowerLeft.getX(), lowerLeft.getY());
        Line right = new Line(lowerRight.getX(), lowerRight.getY(), upperRight.getX(), upperRight.getY());
        Line[] borderLines = {top, left, bottom, right};
        int numOfInter = 0;
        int numOfFailedInter = 0;
        for (int i = 0; i < borderLines.length; i++) {
            if (line.isIntersecting(borderLines[i])) {
                interArray[numOfInter] = line.intersectionWith(borderLines[i]);
                numOfInter++;
            } else {
                interArray[3 - numOfFailedInter] = null;
                numOfFailedInter++;
            }
        }
        for (int i = 0; i < interArray.length; i++) {
            intersections.add(interArray[i]);
        }
        return intersections;
    }

    /**
     * The function uses a "DrawFunction" variable to draw the rectangle.
     *
     * @param d a "DrawSurface" variable used to draw the sprite.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(), (int) this.width, (int) this.height);
    }

    /**
     * The function activates as a single unit of time is passes. The unit of time is based on the game itself.
     *
     * @param dt a unit of time.
     */
    public void timePassed(double dt) {
    }
}