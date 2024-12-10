package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for updating the status and displaying messages related to the simulation
 * or application. It interacts with the ViewPanel to update the displayed status and uses JOptionPane
 * to show messages to the user.
 * This class provides methods to update the status with a specific color and show simple message dialogs.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class StatusUpdater {
    private ViewPanel viewPanel;

    /**
     * Constructs a StatusUpdater with the specified ViewPanel.
     * This constructor initializes the status updater with the ViewPanel that will be used to update
     * the status displayed in the application.
     *
     * @param viewPanel used to update and display the status
     */
    public StatusUpdater(ViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    /**
     * Updates the status displayed in the ViewPanel with the specified status message and color.
     * This method updates the status in the ViewPanel to reflect the current state of the application,
     * such as running or idle, by changing the status message and its associated color.
     *
     * @param status the status message to display (e.g., "Running", "Idle")
     * @param color the color associated with the status message
     */
    public void setStatus(String status, Color color) {
        viewPanel.setStatus(status, color);
    }

    /**
     * Displays a message to the user in a pop-up dialog.
     * This method shows a simple message dialog with the provided message to inform the user about
     * important information or updates.
     *
     * @param message the message to be displayed in the dialog
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
