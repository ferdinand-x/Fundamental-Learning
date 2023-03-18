package com.paradise.code.service;

import com.paradise.code.dao.TextDao;
import com.paradise.code.pojo.bo.HitRateBO;
import com.paradise.code.pojo.bo.RenderBO;
import com.paradise.code.process.PostContext;
import com.paradise.code.process.PostProcessorContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RenderService {

    private final TextDao textDao;
    private final PostProcessorContainer<RenderBO> processContainer;

    private final HitRateService hitRateService;

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

    public HitRateBO hitRate() {
        return hitRateService.getHitRate();
    }
}
