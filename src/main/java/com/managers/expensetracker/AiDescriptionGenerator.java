package com.managers.expensetracker;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class AiDescriptionGenerator {

    public String generateDescription(String category, String transactionType) {
        try {
            String python = "python3";
            String scriptPath = "python/description_generator.py";

            ProcessBuilder processBuilder = new ProcessBuilder(python, scriptPath, category, transactionType);

            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorBuilder = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorBuilder.append(line).append("\n");
            }
            int exitCode = process.waitFor();

            System.out.println("Script Exit Code: " + exitCode);
            System.out.println("Output:");
            System.out.println(outputBuilder.toString());
            System.out.println("Error:");
            System.out.println(errorBuilder.toString());
            return outputBuilder.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing Python script";
        }
    }
}
