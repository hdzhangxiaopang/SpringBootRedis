package com.redis.base.handler;

import com.redis.base.exception.ExceptionCodeDef;
import com.redis.base.exception.ExceptionResolve;
import com.redis.base.vo.ResponseStatusModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by zhangguilin on 1/29/2018.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionResolve exceptionResolve;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception e, HttpServletResponse response, Locale locale){
        log.debug("exception info: {}", ExceptionUtils.getRootCauseMessage(e));
        ResponseStatusModel statusModel = exceptionResolve.resolveException(e,locale);
        try {
            response.setStatus(ExceptionCodeDef.SC_OK);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        } catch (Exception ex) {
           log.error("error: {}", ExceptionUtils.getRootCauseMessage(ex));
        }
        return new ModelAndView();
    }

}
