package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ViewPanel extends JPanel {
    private JLabel imageLabel;
    private Timer processingTimer;
    private JLabel DYNAMIC_STATUS_LABEL;

    public ViewPanel() {
        setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel staticStatusLabel = new JLabel("Status: ");
        staticStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        staticStatusLabel.setForeground(Color.BLACK);

        DYNAMIC_STATUS_LABEL = new JLabel("Idle");
        DYNAMIC_STATUS_LABEL.setFont(new Font("Arial", Font.BOLD, 20));
        DYNAMIC_STATUS_LABEL.setForeground(Color.RED);

        statusPanel.add(staticStatusLabel);
        statusPanel.add(DYNAMIC_STATUS_LABEL);

        imageLabel = new JLabel("No image uploaded", SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(statusPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    public void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imageLabel.setIcon(null);
            imageLabel.setText("Processing the image...");

            try {
                // Run the Python script with the file path as an argument
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "python3", "/Users/shivp/Desktop/FinalProjectPython/imageTracing/main/main.py", selectedFile.getAbsolutePath());
                processBuilder.redirectErrorStream(true); // Redirect error stream to standard output
                Process process = processBuilder.start();

                // Read the output from the Python script
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line); // Optionally display in the console
                    }
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    setRunningStatus(); // Update GUI status on success
                    imageLabel.setText("Image processing complete!");
                } else {
                    setIdleStatus();
                    imageLabel.setText("Error in processing the image.");
                }
            } catch (Exception e) {
                setIdleStatus();
                imageLabel.setText("Error running Python script.");
                e.printStackTrace();
            }
        }
    }


    /**
     * Sets the dynamic status label to display "Running" and changes its color to green.
     */
    public void setRunningStatus() {
        DYNAMIC_STATUS_LABEL.setText("Running");
        DYNAMIC_STATUS_LABEL.setForeground(Color.decode("#008000"));
    }

    /**
     * Sets the dynamic status label to display "Idle" and changes its color to red.
     */
    public void setIdleStatus() {
        DYNAMIC_STATUS_LABEL.setText("Idle");
        DYNAMIC_STATUS_LABEL.setForeground(Color.RED);
    }

    private void onProcessingComplete() {
        imageLabel.setText(null);
        //setRunningStatus();
    }
}
