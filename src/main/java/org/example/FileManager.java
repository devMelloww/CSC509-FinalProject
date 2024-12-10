package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * This class provides methods for managing file selection operations. It is primarily used to
 * allow users to select image files from the file system.
 * This class encapsulates the functionality of displaying a file chooser dialog for selecting
 * image files with specific extensions (jpg, jpeg, png, gif).
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class FileManager {
    /**
     * Opens a file chooser dialog to allow the user to select an image file.
     * The dialog filters files to show only image types with extensions
     * such as .jpg, .jpeg, .png, or .gif.
     * If the user selects a file, it returns the selected File object.
     * If the user cancels the file selection, null is returned.
     *
     * @param parent the parent component used to center the file chooser dialog
     * @return the selected File if a file was chosen, or null if the selection was canceled
     */
    public File selectImageFile(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
