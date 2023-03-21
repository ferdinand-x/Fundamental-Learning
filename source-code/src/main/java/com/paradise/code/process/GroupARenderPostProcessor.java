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
@ProcessOrder
public class GroupARenderPostProcessor implements TextRenderPostProcessor {

    String L_BR = "<br/>";
    String R_BR = "</br>";
    private final AuthHolder authHolder;

    @Override
    public void handleAfter(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserInfo loginUser = renderBO.getLoginUser();
        String text = renderBO.getText();
        if (this.authHolder.authGroupA(loginUser)) {
            String convertText = this.convertText(text, L_BR, R_BR);
            renderBO.setText(convertText);
        }
    }
}
