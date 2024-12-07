package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ViewPanel extends JPanel {
    private JLabel imageLabel;
    private JLabel DYNAMIC_STATUS_LABEL;
    private FileManager fileManager;
    private PythonScriptExecutor scriptExecutor;

    public ViewPanel() {
        fileManager = new FileManager();
        scriptExecutor = new PythonScriptExecutor();

        setLayout(new BorderLayout());
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

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
        File selectedFile = fileManager.selectImageFile(this);
        if (selectedFile != null) {
            imageLabel.setIcon(null);
            imageLabel.setText("Processing...");

            boolean success = scriptExecutor.executeScript(
                    "./ImageAnalyzer.py",
                    selectedFile.getAbsolutePath()
            );

            if (success) {
                onProcessingComplete();
            } else {
                imageLabel.setText("Error running Python script.");
            }
        }
    }

    private void setRunningStatus() {
        DYNAMIC_STATUS_LABEL.setText("Running");
        DYNAMIC_STATUS_LABEL.setForeground(Color.decode("#008000"));
    }

    private void setIdleStatus() {
        DYNAMIC_STATUS_LABEL.setText("Idle");
        DYNAMIC_STATUS_LABEL.setForeground(Color.RED);
    }

    private void onProcessingComplete() {
        String processedImagePath = "./traced_image.png";

        File processedImageFile = new File(processedImagePath);
        if (processedImageFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(processedImagePath);

            Image scaledImage = imageIcon.getImage().getScaledInstance(
                    imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH
            );
            imageIcon = new ImageIcon(scaledImage);

            imageLabel.setIcon(imageIcon);
            imageLabel.setText(null);
        } else {
            imageLabel.setText("Processed image not found.");
        }
    }
}