package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContourParser {

    public class Point {
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

    private final List<Point> Points = new ArrayList<>();
    //Used to reduce scale of contours to within the 0-500mm range of the robot
    public static final int SCALOR = 10;

    public void ParseContours() {
        try(BufferedReader reader = new BufferedReader(new FileReader("./contours.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cords = line.split(",");

                //Adjust x and y coordinates to fit within robot movement space
                double x = Double.parseDouble(cords[0]);
                double y = Double.parseDouble(cords[1]);
                while (x > 0.5 || y > 0.5) {
                    x /= SCALOR;
                    y /= SCALOR;
                }

                Point point = new Point(x, y);
                Points.add(point);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Point getPoint() {
        if (!Points.isEmpty()) {
            return Points.removeFirst();
        }

        return null;
    }

}
