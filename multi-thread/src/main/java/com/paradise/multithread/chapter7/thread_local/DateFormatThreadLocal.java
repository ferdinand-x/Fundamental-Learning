package com.paradise.multithread.chapter7.thread_local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : PARADISE
 * @ClassName : DateFormatThreadLocal
 * @description :
 * @since : 2023/3/20 21:44
 */
public class DateFormatThreadLocal {
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return SIMPLE_DATE_FORMAT.get().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return SIMPLE_DATE_FORMAT.get().parse(dateStr);
    }

    public static void main(String[] args) {
        // Specify the parent directory
        File parentDir = new File("E:\\gvn\\sql\\verify");
        // Get all subdirectories
        File[] subDirs = parentDir.listFiles(File::isDirectory);
        // Loop through each subdirectory
        for (File subDir : subDirs) {
            String text = subDir.getName() +":\n";
            // Create a new file object with the name problem.txt
            File file = new File(subDir, "problem.txt");
            try {
                // Create the file if it does not exist
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(text);
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
