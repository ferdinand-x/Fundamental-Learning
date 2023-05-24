package com.paradise.code.aware;

import com.paradise.code.pojo.bo.CallInfo;
import org.springframework.beans.factory.Aware;

/**
 * @author : PARADISE
 * @InterfaceName : CallInfoAware
 * @description : the interface to aware callInfo
 * @since : 2023/3/26 17:17
 */
public interface CallInfoAware extends Aware {

    /**
     * aware callInfo
     *
     * @param callInfo api callInfo
     */
    void setCallInfo(CallInfo callInfo);
}
