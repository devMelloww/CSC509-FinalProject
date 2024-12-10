package org.example;

import java.io.IOException;

/**
 * This class is responsible for controlling the movement of the robot's arm.
 * It interacts with the NetworkManager to send movement commands to the robot and uses kinematics
 * to calculate the necessary joint angles for the robot to reach specified positions.
 * This class provides methods for verifying if a point is reachable and for moving the robot's arm
 * to specific heights while considering joint angles and other parameters such as roll, pitch, and yaw.
 * It also handles executing a sequence of movements, ensuring the robot first moves to a safe height
 * before moving to the desired drawing height.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class MovementController {
    private NetworkManager networkManager;

    /**
     * Constructs a MovementController with the specified NetworkManager.
     *
     * @param networkManager used for sending movement commands to the robot
     */
    public MovementController(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Determines if the specified point is reachable by the robot based on its kinematic limits.
     * The point is considered reachable if its distance from the origin is within the robot's
     * maximum reachable radius, calculated using the lengths of the robot's arm segments.
     *
     * @param point the point to check for reachability
     * @return true if the point is reachable, false otherwise
     */
    private boolean isPointReachable(Point point) {
        double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
        return r <= (URKinematics.getL1() + URKinematics.getL2());
    }

    /**
     * Moves the robot's arm to a specified height, considering the provided roll, pitch, and yaw.
     * The movement is performed by calculating the required joint angles using kinematics and sending
     * the corresponding movement command to the robot through the NetworkManager.
     * The robot moves first to a safe height and then to the desired drawing height.
     *
     * @param point the point to move to, in the XY-plane
     * @param zHeight the Z-coordinate (height) to move to
     * @param roll the roll angle for the movement
     * @param pitch the pitch angle for the movement
     * @param yaw the yaw angle for the movement
     * @throws IOException if an I/O error occurs while sending the command
     * @throws InterruptedException if the thread is interrupted during sleep
     * @throws IllegalArgumentException if the point is out of reach for the robot
     */
    public void moveToHeight(Point point, double zHeight, double roll, double pitch, double yaw) throws IOException, InterruptedException {
        if (!isPointReachable(point)) {
            throw new IllegalArgumentException("Point out of reach: " + point);
        }

        double[] jointAngles = URKinematics.computeJointAngles(
                point.getX(), point.getY(), zHeight, roll, pitch, yaw
        );

        String command = String.format(
                "movej([%.4f, %.4f, %.4f, %.4f, %.4f, %.4f], a=5, v=5)",
                jointAngles[0], jointAngles[1] - Math.PI / 2,
                jointAngles[2] - Math.PI * 1.5, jointAngles[3],
                jointAngles[4], jointAngles[5]
        );

        networkManager.sendCommand(command);
        Thread.sleep(2000);
    }

    /**
     * Executes a sequence of movements to the specified point. The sequence includes:
     * 1. Moving to a safe height for travel (zTravel)
     * 2. Moving to a drawing height (zDraw)
     * The method uses the {@link #moveToHeight(Point, double, double, double, double)} method to perform
     * each movement step in the sequence.
     *
     * @param point the point to move to, in the XY-plane
     * @param zTravel the height to move to during travel
     * @param zDraw the height to move to for drawing
     * @param roll the roll angle for the movement
     * @param pitch the pitch angle for the movement
     * @param yaw the yaw angle for the movement
     * @throws IOException if an I/O error occurs while sending the command
     * @throws InterruptedException if the thread is interrupted during sleep
     */
    public void executeMovementSequence(Point point, double zTravel, double zDraw, double roll, double pitch, double yaw)
            throws IOException, InterruptedException {
        moveToHeight(point, zTravel, roll, pitch, yaw);
        moveToHeight(point, zDraw, roll, pitch, yaw);
    }
}
