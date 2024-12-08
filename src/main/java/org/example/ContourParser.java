package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContourParser {

    public static class Point {
        private final double X;
        private final double Y;

        public Point(double x, double y) {
            X = x;
            Y = y;
        }

        public double getX() {
            return X;
        }

        public double getY() {
            return Y;
        }
    }

    // Used to reduce scale of contours to within the 0-500mm range of the robot
    public static final int SCALOR = 6000;

    public void ParseContours() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./contours.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cords = line.split(",");

                // Adjust x and y coordinates to fit within robot movement space
                double x = Double.parseDouble(cords[0]) / SCALOR;
                double y = Double.parseDouble(cords[1]) / SCALOR;

                // Create a point and add it to the Blackboard
                Point point = new Point(x, y);
                Blackboard.getInstance().addPoint(point);
            }
        } catch (IOException e) {
            System.err.println("Error reading contours file: " + e.getMessage());
        }
    }
}