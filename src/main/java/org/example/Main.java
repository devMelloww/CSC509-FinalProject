package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu simulationMenu = new JMenu("Simulation");

        ViewPanel viewPanel = new ViewPanel();
        ServerConnection serverConnection = new ServerConnection();

        JMenuItem uploadItem = new JMenuItem("Upload Image");
        JMenuItem simulateItem = new JMenuItem("Start Simulation");
        uploadItem.addActionListener(e -> viewPanel.uploadImage());
        simulateItem.addActionListener(e -> serverConnection.executeCommand());
        fileMenu.add(uploadItem);
        simulationMenu.add(simulateItem);

        menuBar.add(fileMenu);
        menuBar.add(simulationMenu);
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
