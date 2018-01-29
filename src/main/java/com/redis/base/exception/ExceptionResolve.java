package com.redis.base.exception;

import com.redis.base.vo.ResponseStatusModel;

import java.util.Locale;

/**
 * Created by zhangguilin on 1/29/2018.
 */
public interface ExceptionResolve {

    ResponseStatusModel resolveException(Exception e,Locale locale);
}
