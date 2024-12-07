package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonScriptExecutor {
    public boolean executeScript(String scriptPath, String argument) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath, argument);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
