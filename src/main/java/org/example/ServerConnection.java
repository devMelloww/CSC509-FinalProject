package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to UR robot controller at " + ip + ":" + port);
    }

    public void disconnect() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        System.out.println("Disconnected from UR robot controller.");
    }

    public void sendCommand(String command) throws IOException {
        out.println(command);
        System.out.println("Sent command: " + command);
    }


    public void executeCommand() {
        ContourParser parser = new ContourParser();

        // Parse contours and populate the Blackboard
        parser.ParseContours();

        try {
            connect("localhost", 30002);

            double zTravel = 0.2; // Safe travel height
            double zDraw = 0.05;  // Drawing height
            double roll = 0.0;    // No rotation around X
            double pitch = 0.0;   // No tilt
            double yaw = Math.PI; // End-effector faces forward

            while (!Blackboard.getInstance().isEmpty()) {
                ContourParser.Point point = Blackboard.getInstance().getPoint();

                if (point != null) {
                    // Calculate planar distance to the point
                    double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());

                    // Validate reachability
                    if (r > (URKinematics.getL1() + URKinematics.getL2())) {
                        System.out.println("Point out of reach: " + point.getX() + ", " + point.getY());
                        continue; // Skip to the next point
                    }
                    // Compute joint angles for travel to safe height
                    double[] travelAngles = URKinematics.computeJointAngles(
                            point.getX(),
                            point.getY(),
                            zTravel,
                            roll,
                            pitch,
                            yaw
                    );

                    String travelCommand = String.format(
                            "movej([%.4f, %.4f, %.4f, %.4f, %.4f, %.4f], a=1.2, v=0.25)",
                            travelAngles[0],
                            travelAngles[1],
                            travelAngles[2],
                            travelAngles[3],
                            travelAngles[4],
                            travelAngles[5]
                    );
                    sendCommand(travelCommand);

                    Thread.sleep(2000); // Wait for travel to complete

                    // Compute joint angles for drawing height
                    double[] drawAngles = URKinematics.computeJointAngles(
                            point.getX(),
                            point.getY(),
                            zDraw,
                            roll,
                            pitch,
                            yaw
                    );

                    String drawCommand = String.format(
                            "movej([%.4f, %.4f, %.4f, %.4f, %.4f, %.4f], a=1.2, v=0.25)",
                            drawAngles[0],
                            drawAngles[1],
                            drawAngles[2],
                            drawAngles[3],
                            drawAngles[4],
                            drawAngles[5]
                    );
                    sendCommand(drawCommand);

                    Thread.sleep(2000); // Wait for drawing to complete
                }
            }

            disconnect();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


//    public void executeCommand() {
//        ContourParser parser = new ContourParser();
//
//        // Parse contours and populate the Blackboard
//        parser.ParseContours();
//
//        try {
//            connect("localhost", 30002);
//
//            while (!Blackboard.getInstance().isEmpty()) {
//                ContourParser.Point point = Blackboard.getInstance().getPoint();
//
//                if (point != null) {
//                    String moveCommand = String.format(
//                            "movel(p[%.4f, %.4f, 0.400, 0, 0, 3.14], a=1.2, v=0.25, t=0, r=0)",
//                            point.getX(),
//                            point.getY()
//                    );
//                    sendCommand(moveCommand);
//
//                    Thread.sleep(5000);
//                }
//            }
//
//            disconnect();
//        } catch (IOException | InterruptedException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//}