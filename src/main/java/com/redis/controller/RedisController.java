package com.redis.controller;

import com.redis.Result.ResponseModel;
import com.redis.entity.Entity;
import com.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangguilin on 1/26/2018.
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/hi")
    public String HelloController(){
        return "redis Hi";
    }

    @PostMapping("/set/value")
    public ResponseModel<Boolean> setValue(@RequestBody Entity entity) {
        boolean flag = redisService.cacheValue(entity.getKey(), entity.getValue());
        return ResponseModel.ok(flag);
    }

    @GetMapping("/get/{key}/value")
    public ResponseModel<String> getValue(@PathVariable String key) {
        return ResponseModel.ok(redisService.getValue(key));
    }

    @DeleteMapping("/delete/{key}/value")
    public ResponseModel<Boolean> deleteValue(@PathVariable String key){
        return ResponseModel.ok(redisService.removeValue(key));
    }



}
