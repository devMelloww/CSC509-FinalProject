package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main window of the application. It is responsible for setting
 * up the graphical user interface (GUI), initializing various components, and handling user interactions.
 * This class extends JFrame and contains the application's main menu, the view panel for displaying
 * content, and the necessary controllers for simulation.
 * It sets up a menu bar with options for uploading an image, starting and stopping the simulation,
 * and exiting the application.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class Main extends JFrame {

    /**
     * Initializes the main application window and its components.
     * This includes setting the window layout, creating and configuring menu items,
     * setting up action listeners for user interaction, and adding the view panel to the window.
     * The constructor also sets up several controllers, including:
     * - {@link ViewPanel}: Responsible for displaying the image and simulation output.
     * - {@link StatusUpdater}: Used for updating the status messages on the GUI.
     * - {@link NetworkManager}: Manages network communication with the robot controller.
     * - {@link MovementController}: Controls movement logic for the simulation.
     * - {@link CommandExecutor}: Executes commands for robot control.
     * - {@link SimulationController}: Manages starting and stopping the simulation.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu simulationMenu = new JMenu("Simulation");
        JMenu programMenu = new JMenu("Program");

        ViewPanel viewPanel = new ViewPanel();
        StatusUpdater statusUpdater = new StatusUpdater(viewPanel);
        NetworkManager networkManager = new NetworkManager(statusUpdater);
        MovementController movementController = new MovementController(networkManager);
        CommandExecutor commandExecutor = new CommandExecutor(networkManager, movementController, statusUpdater);
        SimulationController simulationController = new SimulationController(commandExecutor, networkManager, statusUpdater);

        JMenuItem uploadItem = new JMenuItem("Upload Image");
        JMenuItem simulateItem = new JMenuItem("Start Simulation");
        JMenuItem stopItem = new JMenuItem("Stop Simulation");
        JMenuItem exitItem = new JMenuItem("Exit");

        uploadItem.addActionListener(e -> viewPanel.uploadImage());
        simulateItem.addActionListener(e -> simulationController.startSimulation());
        stopItem.addActionListener(e -> simulationController.stopSimulation());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(uploadItem);
        simulationMenu.add(simulateItem);
        simulationMenu.add(stopItem);
        programMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(simulationMenu);
        menuBar.add(programMenu);
        setJMenuBar(menuBar);

        add(viewPanel, BorderLayout.CENTER);
    }

    /**
     * The main entry point for the application. This method creates an instance of the Main window
     * and makes it visible. It sets the title, size, and visibility of the window.
     *
     * @param args the command line arguments (unused)
     */
    public static void main(String[] args) {
        Main frame = new Main();
        frame.setTitle("Cobot Simulator");
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}
