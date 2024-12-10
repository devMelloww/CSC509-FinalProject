package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContourParser {

    // Used to reduce scale of contours to within the 0-500mm range of the robot
    public static final int SCALAR = 6000;

    public void ParseContours() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./contours.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cords = line.split(",");

                // Adjust x and y coordinates to fit within robot movement space
                double x = Double.parseDouble(cords[0]) / SCALAR;
                double y = Double.parseDouble(cords[1]) / SCALAR;

                // Create a point and add it to the Blackboard
                Point point = new Point(x, y);
                Blackboard.getInstance().addPoint(point);
            }
        } catch (IOException e) {
            System.err.println("Error reading contours file: " + e.getMessage());
        }
    }
}