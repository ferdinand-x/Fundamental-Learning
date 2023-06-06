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
        Path srcZipPath = Path.of("C:\\Users\\GBA\\Desktop\\attachment\\TRF-TR-001_JF_20230605101822084\\TRF-TR-001_JF_20230605101822084\\D3B9C9AA03D544ACA7E4D1FD052D3E5D.zip");
        String resultFile = "result.json";
        String resultJson = removeZipEntry(srcZipPath, resultFile);
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
                while (is.read(buffer) > 0) {
                    zos.write(buffer);
                }
                is.close();
            }
            zos.closeEntry();
        }
        // close io stream
        zipFile.close();
        zos.close();
        entryIos.close();

        // overwrite source zip file
        Files.copy(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        // delete temp zip file
        Files.deleteIfExists(tempZipPath);
        return resultJson;
    }
}
