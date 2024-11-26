package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;


public class FileManager {
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
