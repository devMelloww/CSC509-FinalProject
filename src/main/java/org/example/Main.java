package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame {

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu simulationMenu = new JMenu("Simulation");
        JMenu programMenu = new JMenu("Program");

        ViewPanel viewPanel = new ViewPanel();
        ServerConnection serverConnection = new ServerConnection(viewPanel);

        JMenuItem uploadItem = new JMenuItem("Upload Image");
        JMenuItem simulateItem = new JMenuItem("Start Simulation");
        JMenuItem stopItem = new JMenuItem("Stop Simulation");
        JMenuItem exitItem = new JMenuItem("Exit");
        uploadItem.addActionListener(e -> viewPanel.uploadImage());
        simulateItem.addActionListener(e -> serverConnection.executeCommand());
        stopItem.addActionListener(e -> {
            try {
                serverConnection.disconnect();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setTitle("Cobot Simulator");
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}
