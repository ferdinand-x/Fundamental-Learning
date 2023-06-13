package com.paradise.code.util;

import com.paradise.code.pojo.dto.MsgDto;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author PARADISE
 */
public class ZipTest3 {

    public static void main(String[] args) throws IOException {
        Path srcZipPath = Path.of("C:\\Users\\PARADISE\\Desktop\\zip-test\\TRF-TR-001_JF_20230606181614668\\TRF-TR-001_JF_20230606181614668\\B4DB5D9F0E2443AB9ACF43F2441CF447.zip");
        String resultFile = "result.json";
//        String resultJson = removeZipEntry(srcZipPath, resultFile);
        MsgDto msgDto = removeZipEntry3(srcZipPath, resultFile);
        System.out.println(msgDto);
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
        Files.move(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        return resultJson;
    }

    /**
     * remove zipEntry from zip file.
     *
     * @param srcZipPath source zip file.
     * @param entryName  target filename eg:{@code result.json}
     * @return content from result.json.
     * @throws IOException file operation error.
     */
    private static String removeZipEntry2(Path srcZipPath, String entryName) throws IOException {
        Path tempZipPath = srcZipPath.getParent().resolve("temp.zip");
        String resultJson = null;

        try (ZipFile zipFile = new ZipFile(srcZipPath.toFile());
             InputStream entryIos = zipFile.getInputStream(zipFile.getEntry(entryName));
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempZipPath))) {
            // loop zipEntries
            Iterator<? extends ZipEntry> iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry entryIn = iterator.next();
                // read result.json
                if (entryName.equals(entryIn.getName())) {
                    resultJson = new String(entryIos.readAllBytes(), StandardCharsets.UTF_8);
                    continue;
                }
                // outEntry
                ZipEntry entryOut = new ZipEntry(entryIn.getName());
                zos.putNextEntry(entryOut);
                // write to tem.zip
                try (InputStream is = zipFile.getInputStream(entryIn)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }
                }
                // write end. close entry.
                zos.closeEntry();
            }
        }
        // overwrite source zip file.
        Files.move(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        return resultJson;
    }

    /**
     * remove zipEntry from zip file.
     *
     * @param srcZipPath source zip file.
     * @param entryName  target filename eg:{@code result.json}
     * @return content from result.json.
     * @throws IOException file operation error.
     */
    private static MsgDto removeZipEntry3(Path srcZipPath, String entryName) throws IOException {
        Path tempZipPath = srcZipPath.getParent().resolve("temp.zip");
        MsgDto msgDto;
        // deal with zipEntry
        try (ZipFile zipFile = new ZipFile(srcZipPath.toFile());
             InputStream entryOs = zipFile.getInputStream(zipFile.getEntry(entryName));
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempZipPath))) {
            // parse result.json
            String resultJson = new String(entryOs.readAllBytes(), StandardCharsets.UTF_8);
            msgDto = GsonUtil.GsonToBean(resultJson, MsgDto.class);
            Map<String, String> fileExtMap = msgDto.getSources().stream()
                    .filter(source -> StringUtils.hasLength(source.getSourceId()))
                    .collect(Collectors.toMap(MsgDto.Source::getSourceId, MsgDto.Source::fileExt, (v1, v2) -> v1));
            // loop zipEntries
            Iterator<? extends ZipEntry> iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry entryIn = iterator.next();
                String entryInName = entryIn.getName();
                if (entryName.equals(entryInName)) {
                    continue;
                }
                // copy to temp.zip
                // source_id
                String sourceId = entryInName.replace("documents/", "");
                // outEntry rename zipEntry name
                String outEntryName = entryIn.isDirectory() ? entryInName : entryInName + fileExtMap.get(sourceId);
                ZipEntry entryOut = new ZipEntry(outEntryName);
                zos.putNextEntry(entryOut);
                // write to tem.zip
                try (InputStream is = zipFile.getInputStream(entryIn)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }
                }
                // write end. close entry.
                zos.closeEntry();
            }
        }
        // overwrite source zip file.
        Files.move(tempZipPath, srcZipPath, StandardCopyOption.REPLACE_EXISTING);
        return msgDto;
    }

}
