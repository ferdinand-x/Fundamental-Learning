package com.paradise.code.util.enums;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author PARADISE
 */
public class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
    private final Class<T> enumClass;

    public EnumTypeAdapter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        out.value(value.name());
    }

    @Override
    public T read(JsonReader in) throws IOException {
        String json = in.nextString();
        if (json == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, json);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Invalid enum value: " + json, e);
        }
    }
}
