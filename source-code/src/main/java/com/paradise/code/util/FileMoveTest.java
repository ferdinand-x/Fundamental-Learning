package com.paradise.code.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMoveTest {
    public static void main(String[] args) throws IOException {
        // zip文件所在目录
        Path zipsPath = Path.of("C:\\Users\\GBA\\Desktop\\generate\\zip");
        // code文件列表所在目录
        Path codePath = Path.of("C:\\Users\\GBA\\Desktop\\generate\\code");
        // zipPath路径转letterCode
        Function<Path,String> function = path -> path.toFile().getName().replaceAll("_.*$","");
        try(Stream<Path> subStream = Files.list(zipsPath)) {
            // 过滤所有zip后缀文件
            Predicate<Path> predicate = path -> path.toFile().getName().endsWith(".zip");
            Map<String, Path> codeZipMap = subStream.filter(predicate).collect(Collectors.toMap(function, v -> v, (v1, v2) -> v1));
            // 遍历生成文件夹
            for (Map.Entry<String, Path> entry : codeZipMap.entrySet()) {
                String letterCode = entry.getKey();
                Path zipPath = entry.getValue();
                String zipFileName = zipPath.toFile().getName();
                Path subCodeDir = codePath.resolve(letterCode);
                // 创建code子目录并移动zip文件
                Files.createDirectory(subCodeDir);
                Files.move(zipPath,subCodeDir.resolve(zipFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    

}
