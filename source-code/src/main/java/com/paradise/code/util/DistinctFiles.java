package com.paradise.code.util;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DistinctFiles {

    public static void main(String[] args) {
        Path beforePath1 = Paths.get("E:\\gvn\\fnt-data-zip");
//        Path beforePath2 = Paths.get("E:\\gvn\\fnt-data-zip-3");
        Path thisPath = Paths.get("E:\\gvn\\fnt-data-zip-4");
        Path target = Paths.get("E:\\gvn\\fnt-data-zip-5");
        Charset charset = StandardCharsets.UTF_8;
        String pattern = "_.*$";

        Set<String> beforeCodes = new HashSet<>();
        try (Stream<Path> beforeStream = Files.list(beforePath1)) {
            beforeCodes = beforeStream.map(v -> v.toFile().getName().replaceAll(pattern, ""))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            //  ignore
        }

        Set<String> thisCodes = new HashSet<>();
        try (Stream<Path> thisStream = Files.list(thisPath)) {
            thisCodes = thisStream.map(v -> v.toFile().getName().replaceAll(pattern, ""))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            //  ignore
        }

        Set<String> difference = Sets.difference(thisCodes, beforeCodes);
        Sets.SetView<String> intersection1 = Sets.intersection(thisCodes, beforeCodes);
        Sets.SetView<String> intersection = Sets.intersection(thisCodes, beforeCodes);

        // move
        try (Stream<Path> thisStream = Files.list(thisPath)) {
            thisStream.filter(v -> (difference).contains(v.toFile().getName().replaceAll(pattern, "")))
                    .forEach(v -> {
                        try {
                            Files.copy(v, target.resolve(v.toFile().getName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            //  ignore
        }

    }
}
