package com.paradise.code.util;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileExtensionUtils {

    /**
     * Retrieves the extension of an unknown file based on its content type using Apache Tika.
     *
     * @param inputFile The input file with an unknown extension.
     * @return The extension of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static String getFileExtension(File inputFile) throws IOException, MimeTypeException {
        // Use Tika to detect the file type
        Detector detector = new DefaultDetector(MimeTypes.getDefaultMimeTypes());
        Parser parser = new AutoDetectParser(detector);
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            parser.parse(inputStream, handler, metadata, new ParseContext());
        } catch (Exception e) {
            // ignore.
        }

        // Get the MIME type of the file
        MediaType mediaType = detector.detect(null, metadata);
        String extension = MimeTypes.getDefaultMimeTypes().forName(mediaType.toString()).getExtension();

        // Use the default extension if the file type cannot be determined
        if (extension.isEmpty()) {
            extension = ""; // Default extension
        }

        return extension;
    }

    /**
     * Modifies the extension of a file based on its content type using Apache Tika.
     *
     * @param inputFile The input file with the original extension.
     * @param newExtension The new extension to be set for the file.
     * @throws IOException If an I/O error occurs while renaming the file.
     */
    public static void modifyFileExtension(File inputFile, String newExtension) throws IOException {
        // Rename the file with the new extension
        String outputFilePath = inputFile.getAbsolutePath().replaceFirst("\\.[^.]+$", newExtension);
        File outputFile = new File(outputFilePath);

        // Rename the file by moving it
        if (!inputFile.renameTo(outputFile)) {
            throw new IOException("Failed to rename the file.");
        }
    }
}
