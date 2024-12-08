package org.example;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Blackboard {
    private static Blackboard instance;
    private final ConcurrentLinkedQueue<ContourParser.Point> points = new ConcurrentLinkedQueue<>();

    private Blackboard() {}

    public static synchronized Blackboard getInstance() {
        if (instance == null) {
            instance = new Blackboard();
        }
        return instance;
    }

    public void addPoint(ContourParser.Point point) {
        points.add(point);
    }

    public ContourParser.Point getPoint() {
        return points.poll();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }
}