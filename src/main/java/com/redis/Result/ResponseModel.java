package com.redis.Result;

import lombok.Data;

/**
 * Created by zhangguilin on 1/26/2018.
 */
@Data
public class ResponseModel<T> {

    private int resultCode = 200;

    private String message = "SUCCESS";

    private T data;

    /**
     * 只返回错误码
     * */
    public ResponseModel(int resultCode) {
        this.resultCode = resultCode;
    }
    /**
     * 只返回数据
     * */
    public ResponseModel(T data) {
        this.data = data;
    }
    /**
     * 返回数据与错误码
     * */
    public ResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }
    /**
     * 返回全部数据
     * */
    public ResponseModel(int resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

}
