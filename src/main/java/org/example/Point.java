package org.example;

/**
 * This class represents a point in a 2D space with x and y coordinates.
 * It provides methods to access the coordinates of the point.
 * This class is immutable, meaning that once an instance is created, its x and y values cannot be changed.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class Point {
    private final double X;
    private final double Y;

    /**
     * Constructs a Point with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        X = x;
        Y = y;
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x-coordinate of this point
     */
    public double getX() {
        return X;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y-coordinate of this point
     */
    public double getY() {
        return Y;
    }
}
