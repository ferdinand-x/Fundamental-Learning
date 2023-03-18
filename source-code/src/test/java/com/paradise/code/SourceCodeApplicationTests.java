package com.paradise.code;

import com.paradise.code.pojo.bo.RenderBO;
import com.paradise.code.pojo.entity.UserInfo;
import com.paradise.code.service.RenderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SourceCodeApplicationTests {

    @Resource
    private RenderService renderService;

    private static RenderBO renderBO;

    @BeforeAll
    public static void init() {
        renderBO = new RenderBO();
        renderBO.setLoginUser(new UserInfo(false, false, true));
        renderBO.setTextKey("123234BBB2141414214");
    }

    @Test
    void testRender() {
        String rs = this.renderService.render(renderBO);
        System.out.println(rs);
    }

}
