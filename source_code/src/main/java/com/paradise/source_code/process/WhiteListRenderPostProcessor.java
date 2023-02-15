package com.paradise.source_code.process;

import com.paradise.source_code.annotation.ProcessOrder;
import com.paradise.source_code.config.AuthHolder;
import com.paradise.source_code.pojo.bo.RenderBO;
import com.paradise.source_code.pojo.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ProcessOrder(after = Integer.MIN_VALUE + 1)
public class WhiteListRenderPostProcessor implements TextRenderPostProcessor {

    private final AuthHolder authHolder;

    @Override
    public void handleAfter(PostContext<RenderBO> postContext) {
        RenderBO renderBO = postContext.getBizData();
        UserInfo loginUser = renderBO.getLoginUser();
        String text = renderBO.getText();
        if (this.authHolder.authWhiteList(loginUser)) {
            renderBO.setText(text.substring(0, 10));
        }
    }

}
