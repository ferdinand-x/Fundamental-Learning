package com.paradise.source_code.pojo.bo;

import com.paradise.source_code.pojo.entity.UserInfo;
import lombok.Data;

@Data
public class RenderBO {

    private UserInfo loginUser;

    private UserInfo publishUser;

    private String textKey;

    private String text;
}
