package com.paradise.code.util.enums;

import com.google.gson.TypeAdapter;

/**
 * @author PARADISE
 */
@SuppressWarnings("all")
public enum MyEnum implements EnumJsonSerializable<MyEnum>{
    VALUE1,
    VALUE2,
    VALUE3;

    @Override
    public String toJson() {
        return "test123";
    }

    @Override
    public MyEnum fromJson(String json) {
        return VALUE3;
    }

    public static TypeAdapter<MyEnum> getAdapter(){
        return new EnumTypeAdapter<>(MyEnum.class);
    }
}
