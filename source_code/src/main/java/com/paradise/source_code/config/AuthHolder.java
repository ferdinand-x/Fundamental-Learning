package com.paradise.source_code.config;

import com.paradise.source_code.pojo.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthHolder {

    public boolean authGroupA(UserInfo loginUser){
        return loginUser.isAuthMark();
    }

    public boolean authGroupB(UserInfo loginUser){
        return loginUser.isAuthMark();
    }

    public boolean authWhiteList(UserInfo loginUser){
        return loginUser.isAuthMark();
    }
}
