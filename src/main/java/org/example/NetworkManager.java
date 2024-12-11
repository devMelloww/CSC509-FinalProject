package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is responsible for managing the communication between the client and the UR robot
 * controller. It handles establishing a connection to the robot, sending commands, and disconnecting.
 * Additionally, it uses the {@link StatusUpdater} to update the status and display messages related to
 * the network connection.
 * This class supports connecting to and disconnecting from the robot, as well as sending commands
 * to control the robot's movements. It also updates the status of the robot connection using the StatusUpdater.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private StatusUpdater statusUpdater;

    /**
     * Constructs a NetworkManager with the specified StatusUpdater.
     * This is used for updating the robot's connection status and displaying messages.
     *
     * @param statusUpdater used to update the connection status and display messages
     */
    public NetworkManager(StatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    /**
     * Establishes a connection to the UR robot controller at the specified IP address and port.
     * The connection is used to send commands to the robot and receive responses. Upon successful
     * connection, the status is updated to "Running" and a message is shown to the user.
     *
     * @param ip the IP address of the UR robot controller
     * @param port the port number of the UR robot controller
     * @throws IOException if an I/O error occurs while establishing the connection
     */
    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger.info("Connected to UR robot controller at " + ip + ":" + port);
        statusUpdater.showMessage("Connected to UR robot controller at " + ip + ":" + port);
        statusUpdater.setStatus("Running", Color.decode("#008000"));
    }

    /**
     * Disconnects from the UR robot controller and closes the network resources.
     * The status is updated to "Idle" and a message is shown to the user.
     *
     * @throws IOException if an I/O error occurs while closing the connection
     */
    public void disconnect() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        logger.info("Disconnected from UR robot controller at " + socket);
        statusUpdater.showMessage("Disconnected from UR robot controller.");
        statusUpdater.setStatus("Idle", Color.decode("#FFA500"));
    }

    /**
     * Sends a command to the UR robot controller over the established connection.
     * The command is sent via the output stream to the robot controller, and the command is
     * printed to the console for debugging purposes.
     *
     * @param command the command to send to the robot
     * @throws IOException if an I/O error occurs while sending the command
     */
    public void sendCommand(String command) throws IOException {
        if (out != null) {
            out.println(command);
            logger.info("Sent command: " + command);
        }
    }
}
