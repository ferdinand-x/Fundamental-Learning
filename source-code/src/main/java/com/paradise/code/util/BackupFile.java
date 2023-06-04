package com.paradise.code.util;

import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BackupFile {
    public static void main(String[] args) {
        // 获取指定目录下zip文件
        Path zipPath = Paths.get("E:\\downloads");
        Path destinationZipPath = Paths.get("E:\\gvn\\fnt-data-zip-4");

        moveZips(zipPath, destinationZipPath);
    }

    private static void log(){
        Path destinationZipPath = Paths.get("E:\\gvn\\fnt-data-zip-4");
        Path destinationZipPath2 = Paths.get("E:\\gvn\\fnt-data-zip");
        Path generatedCodePath = Paths.get("generated_code.txt");
        Path dataCodePath = Paths.get("data_code.txt");
        Path compareCodePath = Paths.get("compare_code.txt");
        Charset charset = StandardCharsets.UTF_8;
        String pattern = "_.*$";
        try (Stream<Path> pathStream = Stream.concat(Files.list(destinationZipPath),Files.list(destinationZipPath2))) {
            Set<String> generatedLines = pathStream.map(v -> v.toFile().getName().replaceAll(pattern,""))
                    .collect(Collectors.toSet());
            Files.write(generatedCodePath, generatedLines, StandardOpenOption.CREATE_NEW);
            Set<String> dataLines = new HashSet<>(Files.readAllLines(dataCodePath, charset));

            Sets.SetView<String> compareLines = Sets.difference(dataLines, generatedLines);
            Files.write(compareCodePath,compareLines,charset,StandardOpenOption.CREATE_NEW);
        } catch (IOException io) {
            System.err.println("write generated_code.txt error:" + io);
        }
    }

    private static void moveZips(Path zipPath, Path destinationZipPath) {
        try (Stream<Path> zipStream = Files.list(zipPath)) {
            List<Path> paths = zipStream.filter(v -> v.toFile().getName().endsWith(".zip"))
                    .filter(BackupFile::fileSizeQualified)
                    .collect(Collectors.toList());
            for (Path path : paths) {
                File file = path.toFile();
                String fileName = file.getName();
                Files.move(path, destinationZipPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("move zip files error:" + e.getMessage());
        }
    }


    private static boolean fileSizeQualified(Path path) {
        boolean result = false;
        try {
            long fileSize = Files.size(path);
            if (fileSize / 1024 > 2) {
                result = true;
            }
        } catch (IOException e) {
            System.err.println("get file size error:" + e);
        }
        return result;
    }
}