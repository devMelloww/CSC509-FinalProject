package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blackboard {

    private static final List<Point> Points = new ArrayList<>();
    private static Blackboard blackboard = null;

    private Blackboard() {}

    public static Blackboard getInstance() {
        if (Objects.isNull(blackboard)) {
            return blackboard = new Blackboard();
        }

        return blackboard;
    }

    public void addPoint(Point point) {
        Points.add(point);
    }

    public Point getPoint() {
        if (!Points.isEmpty()) {
            return Points.removeFirst();
        }

        return null;
    }
}
