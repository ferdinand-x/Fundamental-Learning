package com.paradise.code.util.enums;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @author PARADISE
 */
@SuppressWarnings("all")
public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> rawType = (Class<T>) typeToken.getRawType();
        if (rawType.isEnum()) {
            return new EnumTypeAdapter(rawType);
        }
        return null;
    }
}
