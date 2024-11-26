package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private ViewPanel viewPanel;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem uploadItem = new JMenuItem("Upload Image");
        uploadItem.addActionListener(e -> viewPanel.uploadImage());
        fileMenu.add(uploadItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

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
