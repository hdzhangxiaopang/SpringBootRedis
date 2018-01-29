package com.redis.base.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by zhangguilin on 1/29/2018.
 */
public interface ExceptionCodeDef {

    /**
     * 服务器内部异常
     * */
    int SC_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 请求成功
     * */
    int SC_OK = HttpStatus.OK.value();
}
