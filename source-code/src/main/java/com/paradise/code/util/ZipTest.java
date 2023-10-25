package com.paradise.code.util;

import jodd.io.ZipUtil;
import org.apache.tika.mime.MimeTypeException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @author PARADISE
 */
public class ZipTest {

    public static void main(String[] args) throws IOException, MimeTypeException {
        Path path = Path.of("C:\\Users\\GBA\\Desktop\\attachment\\TRF-TR-001_JF_20230606181614668\\TRF-TR-001_JF_20230606181614668.zip");
        String resultFile = "result.json";
//        String resultJson = removeZipFile(path, resultFile);
        List<String> subFiles = ZipUtil.listZip(path.toFile());
        System.out.println(subFiles);
    }

    private static String removeZipFile(Path path, String resultFile) throws IOException {
        File zipFile = path.toFile();
        String strPath = zipFile.getAbsolutePath().replaceAll("\\.[^.]+$", "");
        Path dirPath = Path.of(strPath);
        // creat temp dir
        if (Files.notExists(dirPath)){
            Files.createDirectory(dirPath);
        }
        // unzip zip file
        ZipUtil.unzip(zipFile, dirPath.toFile());
        // read target file
        String jsonResult = Files.readString(dirPath.resolve(resultFile), StandardCharsets.UTF_8);
        // delete target file: result.json
        Files.deleteIfExists(dirPath.resolve(resultFile));
        // zip dir to zip file
        File replaceZip = ZipUtil.zip(dirPath.toFile());
        Files.copy(replaceZip.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
        Files.walkFileTree(dirPath, new SimpleFileVisitor<>() {
            // delete file ele
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            // delete dir ele
            @Override
            public FileVisitResult postVisitDirectory(Path dir,
                                                      IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        // remove dir
        Files.deleteIfExists(dirPath);
        // return json result
        return jsonResult;
    }
}
