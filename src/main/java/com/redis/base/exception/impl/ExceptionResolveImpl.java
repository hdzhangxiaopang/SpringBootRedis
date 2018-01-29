package com.redis.base.exception.impl;

import com.redis.base.constants.UtilConstants;
import com.redis.base.exception.BusinessException;
import com.redis.base.exception.ExceptionCodeDef;
import com.redis.base.exception.ExceptionResolve;
import com.redis.base.vo.ResponseStatusModel;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by zhangguilin on 1/29/2018.
 */
@Service
public class ExceptionResolveImpl implements ExceptionResolve {
    @Override
    public ResponseStatusModel resolveException(Exception e, Locale locale) {
       if(e instanceof BusinessException){
           BusinessException exception = (BusinessException) e;
           return new ResponseStatusModel(exception.getErrorCode(),exception.getErrorMessage());
       }else{
            return getErrorMessage(ExceptionCodeDef.SC_INTERNAL_SERVER_ERROR, UtilConstants.GLOBAL_SYSTEM_EXCEPTION);
       }
    }

    private static ResponseStatusModel getErrorMessage(int code,String mes){
        return new ResponseStatusModel(code,mes);
    }
}
