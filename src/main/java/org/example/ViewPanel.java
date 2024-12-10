package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class represents the user interface panel for displaying and interacting with images in a
 * graphical application. It allows users to upload images, process them using a Python script, and
 * display the results on the panel.
 * The class includes a status label to indicate the current state of the application (e.g., idle,
 * processing, error) and a central area to display the uploaded or processed image.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class ViewPanel extends JPanel {
    private JLabel imageLabel;
    private JLabel DYNAMIC_STATUS_LABEL;
    private FileManager fileManager;
    private PythonScriptExecutor scriptExecutor;

    /**
     * Constructs a new ViewPanel with a status label and an image display area.
     * The panel is initialized with a FileManager to handle file operations and a
     * PythonScriptExecutor to execute the Python script that processes the uploaded image.
     */
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
        setStatus("Idle", Color.decode("#FFA500"));

        statusPanel.add(staticStatusLabel);
        statusPanel.add(DYNAMIC_STATUS_LABEL);

        imageLabel = new JLabel("No image uploaded", SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(statusPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    /**
     * Prompts the user to upload an image, processes it using the Python script, and updates
     * the display with the processed image or an error message.
     * The method first opens a file selection dialog to choose an image file. If a file is selected,
     * the image is processed by executing the Python script ImageAnalyzer.py. Once the script
     * completes, the panel is updated with the processed image or an error message if the process failed.
     */
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

    /**
     * Sets the status message and the associated color on the dynamic status label.
     *
     * @param status the status message to display
     * @param color the color to apply to the status label
     */
    public void setStatus(String status, Color color) {
        DYNAMIC_STATUS_LABEL.setText(status);
        DYNAMIC_STATUS_LABEL.setForeground(color);
    }

    /**
     * Called when the image processing is complete. If the processed image exists, it is displayed
     * on the panel. Otherwise, an error message is shown.
     */
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