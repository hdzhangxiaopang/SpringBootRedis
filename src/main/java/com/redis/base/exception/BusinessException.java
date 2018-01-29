package com.redis.base.exception;

/**
 * Created by zhangguilin on 1/29/2018.
 */
public interface BusinessException {

    Integer getErrorCode();

    String getErrorMessage();
}
