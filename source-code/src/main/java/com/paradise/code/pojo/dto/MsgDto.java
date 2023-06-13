package com.paradise.code.pojo.dto;

import com.paradise.code.util.GsonUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author : PARADISE
 * @ClassName : MsgDto
 * @description :
 * @since : 2023/6/13 20:57
 */
@Data
public class MsgDto {

    private String requestId;
    private String sftpDestAppId;
    private String zipFileName;
    private List<Source> sources;

    @Data
    public static class Source {
        private String sourceId;
        private String fileNameInZipEntry;
        private boolean success;
        private Error error;
        private List<EntryAttribute> fieldValues;

        public String fileExt() {
            if (CollectionUtils.isEmpty(this.fieldValues)) {
                return "";
            }
            UnaryOperator<String> operator = mimiType -> mimiType.split("/", 2)[1];
            String extKey = "EXTN";
            return this.fieldValues.stream()
                    .filter(attribute -> extKey.equals(attribute.getName()))
                    .findAny().map(attribute -> operator.apply(attribute.getValue())).orElse("");
        }

        @Data
        public static class Error {
            private String en;
            private String zhHK;
            private String zhCN;
        }

        @Data
        public static class EntryAttribute {
            private String name;
            private String value;
        }

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\PARADISE\\Desktop\\zip-test\\result.json");
        byte[] bytes = Files.readAllBytes(path);
        String resultJson = new String(bytes, StandardCharsets.UTF_8);
        MsgDto msgDto = GsonUtil.GsonToBean(resultJson, MsgDto.class);
        System.out.println(msgDto);
    }
}
