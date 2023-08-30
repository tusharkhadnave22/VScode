package com.geektrust.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.appConfig.applicatoinConfig;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.exceptions.NoSuchCommandException;

public class Geektrust {

    public static void main(String[] args) {
    //    String filePath=args[0];
    //    run(filePath);
    run("input.txt");
    
    }

    public static void run(String inputFile) {
        applicatoinConfig applicationConfig = new applicatoinConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;


        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0), tokens);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException | NoSuchCommandException e) {
            e.printStackTrace();
        }

    }

}


