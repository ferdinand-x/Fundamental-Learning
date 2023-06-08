package com.paradise.code.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipTest3 {

    public static void main(String[] args) throws IOException {
        Path srcZipPath = Path.of("C:\\Users\\PARADISE\\Desktop\\zip-test\\TRF-TR-001_JF_20230602131910339\\TRF-TR-001_JF_20230602131910339\\3C08E66A7E1947A69F6C2D52C0FEFEA8.zip");
        String resultFile = "result.json";
        String resultJson = removeZipEntry2(srcZipPath, resultFile);
        System.out.println(resultJson);
    }

    private static String removeZipEntry(Path srcZipPath, String entryName) throws IOException {
        Path tempZipPath = srcZipPath.getParent().resolve("temp.zip");
        String resultJson = null;
        ZipFile zipFile = new ZipFile(srcZipPath.toFile());
        InputStream entryIos = zipFile.getInputStream(zipFile.getEntry(entryName));
        ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempZipPath));
        // loop zip entries
        for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); ) {
            ZipEntry entryIn = e.nextElement();
            if (entryName.equals(entryIn.getName())) {
                resultJson = new String(entryIos.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                ZipEntry entryOut = new ZipEntry(entryIn.getName());
                zos.putNextEntry(entryOut);
                InputStream is = zipFile.getInputStream(entryIn);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = (is.read(buffer))) > 0) {
                    zos.write(buffer);
                }
            }
            zos.closeEntry();
        }
        // close
        zipFile.close();
        zos.close();
        entryIos.close();

        // overwrite source zip file
        Files.move(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        return resultJson;
    }

    private static String removeZipEntry2(Path srcZipPath, String entryName) throws IOException {
        Path tempZipPath = srcZipPath.getParent().resolve("temp.zip");
        String resultJson = null;

        try (ZipFile zipFile = new ZipFile(srcZipPath.toFile());
             InputStream entryIos = zipFile.getInputStream(zipFile.getEntry(entryName));
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempZipPath))) {

            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); ) {
                ZipEntry entryIn = e.nextElement();

                if (entryName.equals(entryIn.getName())) {
                    resultJson = new String(entryIos.readAllBytes(), StandardCharsets.UTF_8);
                    continue;
                }

                ZipEntry entryOut = new ZipEntry(entryIn.getName());
                zos.putNextEntry(entryOut);

                try (InputStream is = zipFile.getInputStream(entryIn)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }
                }

                zos.closeEntry();
            }
        }

        Files.move(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        return resultJson;
    }

}
