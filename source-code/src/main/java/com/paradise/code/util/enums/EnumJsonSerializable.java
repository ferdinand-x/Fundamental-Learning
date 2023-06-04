package com.paradise.code.util.enums;

/**
 * @author PARADISE
 */
public interface EnumJsonSerializable<T extends Enum<T>> {

    /**
     * serialize enum type to json
     *
     * @return json str
     */
    String toJson();

    /**
     * deserialize jsonStr to enum type
     *
     * @param json jsonStr
     * @return enumType
     */
    T fromJson(String json);
}
