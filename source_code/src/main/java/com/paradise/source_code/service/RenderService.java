package com.paradise.source_code.service;

import com.paradise.source_code.dao.TextDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class RenderService {

    private final TextDao textDao;

    private static final Pattern REGEX_PATTERN = Pattern.compile("(\\d+)");

    private static final String L_BR = "<br>";
    private static final String R_BR = "</br>";

    public String render(String textKey) {
        String text = this.textDao.getTextFromDB(textKey);
        return this.convertText(text,L_BR,R_BR);
    }

    private String convertText(String text, String pre, String suf) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        Matcher matcher = REGEX_PATTERN.matcher(text);
        while (matcher.find()) {
            String result = matcher.group(1);
            text = text.replace(result, pre + result.strip() + suf);
        }
        return text;
    }
}
