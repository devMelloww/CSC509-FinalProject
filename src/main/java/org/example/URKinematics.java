package org.example;

public class URKinematics {

    private static final double L1 = 0.4; // Length of the first link
    private static final double L2 = 0.3; // Length of the second link
    private static final double Z_SAFE = 0.2; // Safe height above the paper
    private static final double Z_DRAW = 0.05; // Drawing height on the paper

    public static double[] computeJointAngles(double x, double y, double z, double roll, double pitch, double yaw) {
        double[] jointAngles = new double[6];

        // Base rotation (Joint 1)
        jointAngles[0] = Math.atan2(y, x);

        // Calculate planar distance to the target
        double r = Math.sqrt(x * x + y * y);

        // Calculate the angles for the first and second joints using the law of cosines
        double cosTheta2 = (r * r - L1 * L1 - L2 * L2) / (2 * L1 * L2);
        cosTheta2 = Math.max(-1, Math.min(1, cosTheta2)); // Clamp to valid range
        double sinTheta2 = Math.sqrt(1 - cosTheta2 * cosTheta2); // Elbow up configuration

        jointAngles[2] = Math.atan2(sinTheta2, cosTheta2); // Joint 3 (elbow)

        double denominator = (L1 + L2 * cosTheta2);
        if (Math.abs(denominator) < 1e-6) {
            throw new ArithmeticException("Denominator too small for stable computation.");
        }

        double cosTheta1 = r / denominator;
        double sinTheta1 = (y - L2 * sinTheta2) / denominator;

        jointAngles[1] = Math.atan2(sinTheta1, cosTheta1); // Joint 2 (shoulder)

        // Use z and orientation to calculate wrist joint angles (simplified here)
        jointAngles[3] = roll;   // Wrist 1
        jointAngles[4] = pitch;  // Wrist 2
        jointAngles[5] = yaw;    // Wrist 3

        return jointAngles;
    }

    public static double getL1(){
        return L1;
    }

    public static double getL2(){
        return L2;
    }
}