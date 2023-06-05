package com.paradise.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FntDocs {

    public static void main(String[] args) {
        Path origFile = Path.of("C:\\Users\\GBA\\Desktop\\gnd_FOR-BAL-ER-002.docx");
        String fileName = origFile.toFile().getName();
        try (Stream<Path> pathStream = Files.list(Path.of("C:\\Users\\GBA\\Desktop\\fnt-code-docs"))) {
            pathStream.forEach(v->{
               if ( Files.isDirectory(v)){
                   String codeName = v.toFile().getName();
                   String targetName = fileName.replace("FOR-BAL-ER-002", codeName);
                   try {
                       Files.copy(origFile,v.resolve(targetName), StandardCopyOption.REPLACE_EXISTING);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
            });
        } catch (IOException e) {

        }
    }

}

