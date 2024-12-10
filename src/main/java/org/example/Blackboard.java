package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class implements a singleton design pattern to act as a shared
 * resource for storing and retrieving points. It uses a thread-safe ConcurrentLinkedQueue to ensure
 * safe concurrent access in a multithreaded environment.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class Blackboard {
    private static Blackboard instance;
    private final ConcurrentLinkedQueue<Point> points = new ConcurrentLinkedQueue<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     * This enforces the singleton pattern.
     */
    private Blackboard() {}

    /**
     * Returns the singleton instance of the Blackboard.
     * If the instance does not already exist, it is created.
     *
     * @return the singleton instance of the Blackboard
     */
    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    /**
     * Adds a Point to the blackboard. The point is stored in the queue for
     * future retrieval and processing.
     *
     * @param point the Point to be added to the blackboard.
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * Retrieves and removes the next Point from the blackboard.
     * If the queue is empty, this method returns null.
     *
     * @return the next Point in the queue, or null if the queue is empty.
     */
    public Point getPoint() {
        return points.poll();
    }

    /**
     * Checks if the blackboard is empty.
     *
     * @return true if the blackboard contains no points, otherwise false.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }
}