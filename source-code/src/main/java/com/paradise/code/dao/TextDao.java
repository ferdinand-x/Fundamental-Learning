package com.paradise.code.dao;

import org.springframework.stereotype.Repository;

@Repository
public class TextDao {

    public String getTextFromDB(String textKey) {
        return textKey;
    }
}
