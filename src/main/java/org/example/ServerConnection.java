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
        if(in != null) in.close();
        if(out != null) out.close();
        if(socket != null) socket.close();
        System.out.println("Disconnected from UR robot controller.");
    }

    public void sendCommand(String command) throws IOException {
        out.println(command);
        System.out.println("Sent command: " + command);
    }

    public static void main(String[] args) {
        ServerConnection controller = new ServerConnection();
        ContourParser parser = new ContourParser();
        parser.ParseContours();

        try {
            controller.connect("localhost", 30002);

            ContourParser.Point point;
            while ((point = parser.getPoint()) != null) {
                //String moveCommand = "movej([0.0, -1.57, 0.0, -1.57, 0.0, 0.0], a=1.2, v=0.25)";
                String moveCommand = String.format("movel(p[-%.4f,%.4f,0.600,0,0,3.14], a=1.2, v=0.25, t=0, r=0)",
                                                   point.getX(),
                                                   point.getY());
                controller.sendCommand(moveCommand);
                Thread.sleep(5000); //wait 5 seconds
            }

            controller.disconnect();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}