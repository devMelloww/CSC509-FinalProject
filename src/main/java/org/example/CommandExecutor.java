package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

/**
 * This class is responsible for managing the execution of commands for a cobot simulation.
 * It handles the parsing of movement points, connecting to the cobot's network controller, and executing
 * movement sequences based on parsed points. The class operates on a separate thread to ensure
 * non-blocking execution of the command sequence.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class CommandExecutor {
    private NetworkManager networkManager;
    private MovementController movementController;
    private StatusUpdater statusUpdater;
    private boolean running;
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    /**
     * Constructs a CommandExecutor with the specified dependencies.
     *
     * @param networkManager      Responsible for network communication
     * @param movementController  Handles the movement execution
     * @param statusUpdater      Updates the UI with status messages
     */
    public CommandExecutor(NetworkManager networkManager, MovementController movementController, StatusUpdater statusUpdater) {
        this.networkManager = networkManager;
        this.movementController = movementController;
        this.statusUpdater = statusUpdater;
    }

    /**
     * Starts the execution of commands for the cobot simulation.
     * This method runs on a separate thread, parsing movement points and sending them to the cobot for execution.
     * It connects to the cobot's network controller and processes points.
     * The method ensures safe transitions between a travel height and a drawing height, and skips invalid points.
     */
    public void executeCommand() {
        running = true;

        new Thread(() -> {
            ContourParser parser = new ContourParser();
            parser.ParseContours();

            try {
                logger.info("Connecting to cobot...");
                networkManager.connect("localhost", 30002);

                double zTravel = 0.2;
                double zDraw = 0.05;
                double roll = 0.0;
                double pitch = 0.0;
                double yaw = Math.PI;

                while (running && !Blackboard.getInstance().isEmpty()) {
                    Point point = Blackboard.getInstance().getPoint();
                    logger.debug("Processing point: {}", point);

                    try {
                        movementController.executeMovementSequence(point, zTravel, zDraw, roll, pitch, yaw);
                    } catch (IllegalArgumentException e) {
                        logger.warn("Skipping point: {}", e.getMessage());
                    }

                    if (!running) break;
                }

                networkManager.disconnect();
                logger.info("Disconnected from cobot.");
            } catch (IOException e) {
                logger.error("Connection error: {}", e.getMessage(), e);
                statusUpdater.setStatus("Error", Color.RED);
            } catch (InterruptedException e) {
                logger.error("Command execution interrupted: {}", e.getMessage(), e);
            }
        }).start();
    }

    /**
     * Stops the execution of commands. This method sets the running flag to false,
     * which terminates the command execution loop.
     */
    public void stop() {
        running = false;
        logger.info("Command execution stopped.");
    }
}
