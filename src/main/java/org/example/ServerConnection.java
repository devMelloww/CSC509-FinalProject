package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = true;
    private ViewPanel viewPanel;

    public ServerConnection(ViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to UR robot controller at " + ip + ":" + port);
        JOptionPane.showMessageDialog(null, "Connected to UR robot controller at " + ip + ":" + port);
        viewPanel.setStatus("Running", Color.decode("#008000"));
    }

    public void disconnect() throws IOException {
        running = false; // Stop the simulation thread
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        System.out.println("Disconnected from UR robot controller.");
        JOptionPane.showMessageDialog(null, "Disconnected from UR robot controller.");
        viewPanel.setStatus("Idle", Color.decode("#FFA500"));
    }

    public void sendCommand(String command) throws IOException {
        out.println(command);
        System.out.println("Sent command: " + command);
    }

    public void executeCommand() {
        running = true;
        new Thread(() -> {
            ContourParser parser = new ContourParser();
            parser.ParseContours();

            try {
                connect("localhost", 30002);

                double zTravel = 0.2; // Safe travel height
                double zDraw = 0.05;  // Drawing height
                double roll = 0.0;    // No rotation around X
                double pitch = 0.0;   // No tilt
                double yaw = Math.PI; // End-effector faces forward

                while (running && !Blackboard.getInstance().isEmpty()) { // Check the flag
                    Point point = Blackboard.getInstance().getPoint();

                    double r = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
                    if (r > (URKinematics.getL1() + URKinematics.getL2())) {
                        System.out.println("Point out of reach: " + point.getX() + ", " + point.getY());
                        continue;
                    }

                    double[] travelAngles = URKinematics.computeJointAngles(
                            point.getX(), point.getY(), zTravel, roll, pitch, yaw
                    );

                    String travelCommand = String.format(
                            "movej([%.4f, %.4f, %.4f, %.4f, %.4f, %.4f], a=5, v=5)",
                            travelAngles[0], travelAngles[1] - Math.PI / 2,
                            travelAngles[2] - Math.PI * 1.5, travelAngles[3],
                            travelAngles[4], travelAngles[5]
                    );
                    sendCommand(travelCommand);

                    if (!running) break; // Check the flag again before sleeping

                    Thread.sleep(2000);

                    double[] drawAngles = URKinematics.computeJointAngles(
                            point.getX(), point.getY(), zDraw, roll, pitch, yaw
                    );

                    String drawCommand = String.format(
                            "movej([%.4f, %.4f, %.4f, %.4f, %.4f, %.4f], a=5, v=5)",
                            drawAngles[0], drawAngles[1] - Math.PI / 2,
                            drawAngles[2] - Math.PI * 1.5, drawAngles[3],
                            drawAngles[4], drawAngles[5]
                    );
                    sendCommand(drawCommand);

                    if (!running) break; // Check the flag again before sleeping

                    Thread.sleep(2000);
                }

                disconnect();
            } catch (IOException | InterruptedException e) {
                viewPanel.setStatus("Error", Color.RED);
                System.out.println("Error: " + e.getMessage());
            }
        }).start();
    }
}
