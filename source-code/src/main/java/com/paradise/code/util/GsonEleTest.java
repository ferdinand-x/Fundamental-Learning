package com.paradise.code.util;

import jodd.io.ZipUtil;
import org.apache.tika.mime.MimeTypeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class GsonEleTest {

    public static void main(String[] args) throws IOException, MimeTypeException {
        Path path = Path.of("C:\\Users\\GBA\\Desktop\\attachment\\TRF-TR-001_JF_20230531122400099\\TRF-TR-001_JF_20230531122400099\\71EA624EC20F4EA2AB521BE6B949182E.zip");
        File zipFile = path.toFile();
        String strPath = zipFile.getAbsolutePath().replaceAll("\\.[^.]+$", "");
        Path dirPath = Path.of(strPath);
        if (Files.notExists(dirPath)){
            Files.createDirectory(dirPath);
        }
        ZipUtil.unzip(zipFile, dirPath.toFile());
        Files.deleteIfExists(dirPath.resolve("result.json"));
        File replaceZip = ZipUtil.zip(dirPath.toFile());
        Files.copy(replaceZip.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
        Path fileTree = Files.walkFileTree(dirPath, new SimpleFileVisitor<>() {
            // 先去遍历删除文件
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            // 再去遍历删除目录
            @Override
            public FileVisitResult postVisitDirectory(Path dir,
                                                      IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        Files.deleteIfExists(dirPath);
    }
}
