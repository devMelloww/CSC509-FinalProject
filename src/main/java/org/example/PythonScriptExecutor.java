package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class provides functionality to execute a Python script with a given argument,
 * capture the output, and handle errors.
 * It uses the ProcessBuilder to initiate the execution of the script and captures both
 * standard output and error output streams.
 *
 * @author Damian Dhesi
 * @author Reza Mousakhani
 * @author Shiv Panchal
 * @version 1.0
 */
public class PythonScriptExecutor {
    /**
     * Executes a Python script with the specified script path and argument.
     * This method runs the Python script using the python3 command, passes the
     * specified argument to the script, and prints the script's output and error streams
     * to the console.
     *
     * @param scriptPath the file path of the Python script to execute
     * @param argument the argument to pass to the Python script
     * @return true if the script executed successfully (exit code 0), false otherwise
     */
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
            e.printStackTrace();
            return false;
        }
    }
}
