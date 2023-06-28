package com.paradise.code.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonEleTest {
    public static void main(String[] args) {
        JsonObject jsonObject = GsonUtil.GsonToBean(null, JsonObject.class);
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("aaa","aaa");
        String val = null;
        jsonObj.addProperty("acc",val);
        JsonElement aaa = jsonObj.get("aaa");
        JsonElement acc = jsonObj.get("acc");
        System.out.println(aaa);
        System.out.println(jsonObject);
    }
}
