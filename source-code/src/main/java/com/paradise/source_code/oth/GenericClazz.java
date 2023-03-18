package com.paradise.source_code.oth;

import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class GenericClazz {

    private HashMap<String, List<Integer>> param;

    public static void main(String[] args) throws NoSuchFieldException, IOException {

        List<String> req_ids = List.of("a", "b", "c");
        String id_str = String.join(",", req_ids);

        String[] ids = id_str.split(",");

        System.out.println(req_ids);
        Path path = Paths.get("C:\\Users\\GBA\\Downloads\\GVN_MODULE_SQL_CFG_DATA_TABLE.xlsx");
        Path path1 = Paths.get("C:\\Users\\GBA\\Downloads\\GVN_MODULE_SQL_CFG_DATA_TABLE2.xlsx");
        Path copy = Files.copy(path, path1, StandardCopyOption.REPLACE_EXISTING);

    }

    private static void printParmaBySpring() throws NoSuchFieldException {
        ResolvableType param = ResolvableType.forField(GenericClazz.class.getDeclaredField("param"));
        System.out.println("从 HashMap<String, List<Integer>> 中获取 String:" + param.getGeneric(0).resolve());
        System.out.println("从 HashMap<String, List<Integer>> 中获取 List<Integer> :" + param.getGeneric(1));
        System.out.println(
            "从 HashMap<String, List<Integer>> 中获取 List :" + param.getGeneric(1).resolve());
        System.out.println("从 HashMap<String, List<Integer>> 中获取 Integer:" + param.getGeneric(1,0));
        System.out.println("从 HashMap<String, List<Integer>> 中获取父类型:" +param.getSuperType());
    }
}
