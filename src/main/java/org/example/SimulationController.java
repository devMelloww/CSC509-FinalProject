package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;

/**
 * This class manages the execution and stopping of the simulation. It interacts with the CommandExecutor,
 * NetworkManager, and StatusUpdater to control the simulation and update the status accordingly.
 * The class provides methods to start and stop the simulation, ensuring that the appropriate commands
 * are executed and that the network connection is properly managed during the simulation's lifecycle.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class SimulationController {
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    private final CommandExecutor commandExecutor;
    private final NetworkManager networkManager;
    private final StatusUpdater statusUpdater;

    /**
     * Constructs a SimulationController with the specified CommandExecutor, NetworkManager, and StatusUpdater.
     * This constructor is used to initialize the controller with the necessary components for managing the simulation.
     *
     * @param commandExecutor used to execute commands for the simulation
     * @param networkManager used to manage network connections
     * @param statusUpdater used to update the simulation status
     */
    public SimulationController(CommandExecutor commandExecutor, NetworkManager networkManager, StatusUpdater statusUpdater) {
        this.commandExecutor = commandExecutor;
        this.networkManager = networkManager;
        this.statusUpdater = statusUpdater;
    }

    /**
     * Starts the simulation by executing the appropriate command via the CommandExecutor.
     * This method triggers the execution of the simulation command, allowing the simulation to start.
     */
    public void startSimulation() {
        commandExecutor.executeCommand();
        logger.info("Simulation started");

    }

    /**
     * Stops the simulation by stopping the command execution and disconnecting from the network.
     * This method stops the running simulation, updates the status, and ensures that the network
     * connection is properly closed. If an error occurs while stopping the simulation, the status
     * is updated to indicate an error.
     *
     * @throws RuntimeException if there is an error while disconnecting from the network
     */
    public void stopSimulation() {
        logger.info("Stopping simulation...");
        commandExecutor.stop();
        logger.info("Simulation stopped successfully.");
        try {
            networkManager.disconnect();
        } catch (IOException e) {
            logger.error("Error while disconnecting network: {}", e.getMessage(), e);
            statusUpdater.setStatus("Error", Color.RED);
            throw new RuntimeException("Error stopping simulation: " + e.getMessage(), e);
        }
    }
}
