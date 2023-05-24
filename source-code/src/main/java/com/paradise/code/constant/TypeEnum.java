package com.paradise.code.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : PARADISE
 * @EnumName : TypeEnum
 * @description :
 * @since : 2023/4/8 18:40
 */
@AllArgsConstructor
@Getter
public enum TypeEnum {

    type1 {
        private static final String BASICS = "MEMBER";

        @Override
        public boolean judge(String parameter) {
            return BASICS.equalsIgnoreCase(parameter);
        }
    },
    type2 {

        private final List<String> BASICS = List.of("MEM_ACCT", "MEM ACCT");

        @Override
        public boolean judge(String parameter) {
            return BASICS.contains(parameter);
        }
    };

    public abstract boolean judge(String parameter);

    public static TypeEnum judgeType(String parameter) {
        return Arrays.stream(TypeEnum.values())
                .filter(v -> v.judge(parameter))
                .findAny().orElse(null);
    }

    public static void main(String[] args) {
        try {
//            List<String> lines = Files.readAllLines(Paths.get("abc.txt"));
            Random random = new Random();
            Path path = Paths.get("abc.txt");
            byte[] header = "title\tname\tage\tdetails\n".getBytes(StandardCharsets.UTF_8);
            Files.write(path, header, StandardOpenOption.CREATE_NEW);
            List<String> lines = IntStream.range(0, 1_000).boxed()
                    .map(i -> {
                        String title = "title" + i;
                        String name = "name" + i;
                        int age = random.nextInt(120);
                        String details = "details" + i;
                        return String.join("\t", title, name, String.valueOf(age), details);
                    }).collect(Collectors.toList());
            Files.write(path,lines,StandardCharsets.UTF_8,StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("error");
        }
    }

}
