package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private ViewPanel viewPanel;

    public Main() {
        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        // Create the upload menu item
        JMenuItem uploadItem = new JMenuItem("Upload Image");
        uploadItem.addActionListener(e -> viewPanel.uploadImage());  // Trigger upload image in ViewPanel
        fileMenu.add(uploadItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);  // Add the menu bar to the frame

        // Create the ViewPanel and add it to the frame
        viewPanel = new ViewPanel();
        add(viewPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setTitle("Cobot Simulator");
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}
