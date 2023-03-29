package com.paradise.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupFile {
    public static void main(String[] args) {
        String fileName = "file.txt";
        String backupDirName = "backup";
        Path filePath = Paths.get(fileName);
        Path backupDirPath = Paths.get(filePath.getParent().toFile().getPath(),backupDirName);

        try {
            Files.createDirectory(backupDirPath);
            Files.move(filePath, backupDirPath.resolve(filePath.getFileName()));
            System.out.println("File backed up successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}