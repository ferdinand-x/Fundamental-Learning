package com.paradise.code.util;

import jodd.io.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ZipTest2 {

    public static void main(String[] args) throws IOException {
        Path zipFilePath = Path.of("C:\\Users\\GBA\\Desktop\\attachment\\TRF-TR-001_JF_20230531122400099\\TRF-TR-001_JF_20230531122400099\\71EA624EC20F4EA2AB521BE6B949182E.zip");
        Path destinationDirectory = zipFilePath.getParent();

        // Extract the compressed file to the specified directory
        ZipUtil.unzip(zipFilePath.toFile(), destinationDirectory.toFile());

        // Delete the result.json file
        Path jsonFilePath = destinationDirectory.resolve("result.json");
        Files.deleteIfExists(jsonFilePath);

        // Re-compress the folder
        File newZipFile = ZipUtil.zip(destinationDirectory.toFile());
        Files.copy(newZipFile.toPath(), zipFilePath, StandardCopyOption.REPLACE_EXISTING);

        // Delete the extracted temporary directory
        Files.walkFileTree(destinationDirectory, new SimpleFileVisitor<>() {
            // Delete files first
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            // Delete directories afterward
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Operation completed!");
    }
}
