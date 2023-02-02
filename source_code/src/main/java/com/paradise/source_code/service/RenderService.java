package com.paradise.source_code.service;

import com.paradise.source_code.dao.TextDao;
import com.paradise.source_code.pojo.bo.RenderBO;
import com.paradise.source_code.process.PostContext;
import com.paradise.source_code.process.PostProcessorContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RenderService {

    private final TextDao textDao;
    private final PostProcessorContainer<RenderBO> processContainer;

    public String render(RenderBO renderBO) {

        PostContext<RenderBO> postContext = new PostContext<>(renderBO);

        boolean isContinue = this.processContainer.handleBefore(postContext);
        if (!isContinue) {
            return renderBO.getText();
        }
        String text = this.textDao.getTextFromDB(renderBO.getTextKey());
        renderBO.setText(text);
        this.processContainer.handleAfter(postContext);
        return renderBO.getText();
    }

}
