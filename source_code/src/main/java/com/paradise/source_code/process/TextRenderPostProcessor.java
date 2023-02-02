package com.paradise.source_code.process;

import com.paradise.source_code.pojo.bo.RenderBO;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TextRenderPostProcessor extends BasePostProcessor<RenderBO> {

    Pattern REGEX_PATTERN = Pattern.compile("(\\d+)");

    default String convertText(String text, String pre, String suf) {
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
