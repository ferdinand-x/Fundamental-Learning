package com.paradise.source_code.process;

import com.paradise.source_code.config.AuthHolder;
import com.paradise.source_code.pojo.bo.RenderBO;
import com.paradise.source_code.pojo.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthValidatePostPostProcessor implements TextRenderPostProcessor {

    private final AuthHolder authHolder;

    @Override
    public boolean handleBefore(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserInfo loginUser = renderBO.getLoginUser();
        if (authHolder.authGroupA(loginUser)) {
            return true;
        }
        if (authHolder.authGroupB(loginUser)) {
            return true;
        }
        if (authHolder.authWhiteList(loginUser)) {
            return true;
        }
        throw new RuntimeException("权限校验失败!");
    }

    @Override
    public int getPriority() {
        return Integer.MIN_VALUE;
    }
}
