package com.paradise.code.process;

import com.paradise.code.annotation.ProcessOrder;
import com.paradise.code.config.AuthHolder;
import com.paradise.code.pojo.bo.RenderBO;
import com.paradise.code.pojo.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ProcessOrder(before = Integer.MIN_VALUE)
public class AuthValidatePostProcessor implements TextRenderPostProcessor {

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

}
