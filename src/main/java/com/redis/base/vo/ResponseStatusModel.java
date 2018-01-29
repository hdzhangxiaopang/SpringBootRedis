package com.redis.base.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zhangguilin on 1/29/2018.
 */
@AllArgsConstructor
@Getter
public class ResponseStatusModel {

    private int statusCode;

    private String errorMessage;

}
