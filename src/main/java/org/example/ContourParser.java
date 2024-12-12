package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is responsible for parsing contour data from a file and adding the parsed
 * points to the Blackboard. The input file is expected to contain a series of coordinates in the
 * format "x,y", separated by newlines. Each coordinate is scaled down by a predefined scalar value
 * before being converted into Point objects.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class ContourParser {
    private static final Logger logger = LoggerFactory.getLogger(ContourParser.class);
    public static final int SCALAR = 6000;

    /**
     * Parses contour data from a file named "contours.txt" located in the working directory.
     * Each line in the file should be in the format "x,y", where x and y are numeric values
     * representing a coordinate. The parsed coordinates are normalized using
     * the #SCALAR and added to the Blackboard for further processing.
     * If the file cannot be read or a parsing error occurs, an error message is printed to the console.
     */
    public void ParseContours() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./contours.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cords = line.split(",");

                double x = Double.parseDouble(cords[0]) / SCALAR;
                double y = Double.parseDouble(cords[1]) / SCALAR;

                Point point = new Point(x, y);
                Blackboard.getInstance().addPoint(point);
            }
        } catch (IOException e) {
            logger.error("Error reading contours file: " + e.getMessage());
        }
    }
}