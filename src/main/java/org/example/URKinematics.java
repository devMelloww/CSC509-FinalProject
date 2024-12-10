package org.example;

/**
 * This class provides methods to compute the joint angles for a robotic arm based on its
 * end-effector's position and orientation in 3D space.
 * This class uses inverse kinematics to calculate the joint angles required for the robotic arm
 * to reach a specific point in space (x, y, z) with a given orientation (roll, pitch, yaw).
 * The parameters are based on a 2-link robotic arm with link lengths L1 and L2.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class URKinematics {

    private static final double L1 = 0.4;
    private static final double L2 = 0.3;

    /**
     * Computes the joint angles for a robotic arm to reach a specified end-effector position
     * and orientation.
     * The method uses inverse kinematics to calculate the joint angles for a 2-link robotic arm.
     * It assumes that the arm operates in a 2D plane for the positioning and 3D space for the orientation.
     * The calculated joint angles correspond to the required angles at the arm's joints to reach
     * the target position (x, y, z) with the desired orientation (roll, pitch, yaw).
     *
     * @param x the x-coordinate of the target position (in meters)
     * @param y the y-coordinate of the target position (in meters)
     * @param z the z-coordinate of the target position (in meters)
     * @param roll the roll orientation of the end-effector (in radians)
     * @param pitch the pitch orientation of the end-effector (in radians)
     * @param yaw the yaw orientation of the end-effector (in radians)
     * @return an array of joint angles [theta1, theta2, theta3, roll, pitch, yaw] where theta1, theta2, and
     *         theta3 are the joint angles for the robotic arm, and roll, pitch, yaw are the orientation angles
     *         for the end-effector.
     * @throws ArithmeticException if the computed denominator for the inverse kinematics is too small
     *                             to ensure stable calculations.
     */
    public static double[] computeJointAngles(double x, double y, double z, double roll, double pitch, double yaw) {
        double[] jointAngles = new double[6];

        jointAngles[0] = Math.atan2(y, x);

        double r = Math.sqrt(x * x + y * y);

        double cosTheta2 = (r * r - L1 * L1 - L2 * L2) / (2 * L1 * L2);
        cosTheta2 = Math.max(-1, Math.min(1, cosTheta2));
        double sinTheta2 = Math.sqrt(1 - cosTheta2 * cosTheta2);

        jointAngles[2] = Math.atan2(sinTheta2, cosTheta2);

        double denominator = (L1 + L2 * cosTheta2);
        if (Math.abs(denominator) < 1e-6) {
            throw new ArithmeticException("Denominator too small for stable computation.");
        }

        double cosTheta1 = r / denominator;
        double sinTheta1 = (y - L2 * sinTheta2) / denominator;

        jointAngles[1] = Math.atan2(sinTheta1, cosTheta1);
        jointAngles[3] = roll;
        jointAngles[4] = pitch;
        jointAngles[5] = yaw;

        return jointAngles;
    }

    /**
     * Returns the length of the first link in the robotic arm.
     *
     * @return the length of the first link (L1) in meters
     */
    public static double getL1(){
        return L1;
    }

    /**
     * Returns the length of the second link in the robotic arm.
     *
     * @return the length of the second link (L2) in meters
     */
    public static double getL2(){
        return L2;
    }
}