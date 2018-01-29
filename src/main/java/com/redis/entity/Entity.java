package com.redis.entity;

import lombok.Data;

/**
 * Created by zhangguilin on 1/29/2018.
 */
@Data
public class Entity {

    private String key;

    private String value;

    private long time;

}
