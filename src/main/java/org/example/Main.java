package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        ViewPanel viewPanel = new ViewPanel();
        JMenuItem uploadItem = new JMenuItem("Upload Image");
        uploadItem.addActionListener(e -> viewPanel.uploadImage());
        fileMenu.add(uploadItem);

        ServerConnection serverConnection = new ServerConnection();
        JMenuItem cobotItem = new JMenuItem("Run Cobot");
        cobotItem.addActionListener(e -> serverConnection.runCobot());
        fileMenu.add(cobotItem);

        menuBar.add(fileMenu);
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
