package com.adyen.reportMerger.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Created by andrew on 9/27/16.
 */
public final class LogFileHandler {

    public static FileHandler fileHandler(String path){

        try {
            FileHandler fh = new FileHandler(path +"/logFile.txt", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            return fh;
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteEmptyLogFile(String path) {
        File logFile = new File(path + "/logFile.txt");
        if (logFile.length() == 0) {
            logFile.delete();
        }
    }
}
