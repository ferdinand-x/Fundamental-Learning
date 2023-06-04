package com.paradise.code.util.enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : PARADISE
 * @ClassName : TestEnum
 * @description :
 * @since : 2023/6/4 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEnum {

    private String name;

    private String details;

    private MyEnum myEnum;

    public static void main(String[] args) {
        // 创建Gson实例并注册枚举类型的适配器
        Gson gson = new GsonBuilder()
//        gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory())
//                .registerTypeAdapter(MyEnum.class, MyEnum.getAdapter())
                .create();

        // 示例：枚举序列化和反序列化
        TestEnum testEnum = new TestEnum();
        testEnum.setName("ferdinand");
        testEnum.setDetails("i am practice something");
        testEnum.setMyEnum(MyEnum.VALUE3);

        // 序列化为字符串
        String json = gson.toJson(testEnum);
        System.out.println("Serialized JSON: " + json);

        // 反序列化为枚举
        TestEnum deserializedObj = gson.fromJson(json, TestEnum.class);
        System.out.println("Deserialized obj: " + deserializedObj);
    }
}
