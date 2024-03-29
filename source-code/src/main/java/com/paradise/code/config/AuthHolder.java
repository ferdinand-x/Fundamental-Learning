package com.paradise.code.config;

import com.paradise.code.pojo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthHolder {

    public boolean authGroupA(UserInfo loginUser) {
        return loginUser.isGroupA();
    }

    public boolean authGroupB(UserInfo loginUser) {
        return loginUser.isGroupB();
    }

    public boolean authWhiteList(UserInfo loginUser) {
        return loginUser.isWhiteList();
    }
}
