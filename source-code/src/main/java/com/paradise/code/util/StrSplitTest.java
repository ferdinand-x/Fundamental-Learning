package com.paradise.code.util;

public class StrSplitTest {
    public static void main(String[] args) {
        String str = "abc-bcd-efg";
        String[] split = str.split("-", 2);
        System.out.println(split);
    }
}
