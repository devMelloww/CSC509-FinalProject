package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Blackboard {
    private static Blackboard instance;
    private final ConcurrentLinkedQueue<Point> points = new ConcurrentLinkedQueue<>();

    private Blackboard() {}

    public static Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint() {
        return points.poll();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }
}