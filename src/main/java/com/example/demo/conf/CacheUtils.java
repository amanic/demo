package com.example.demo.conf;

import lombok.Data;

/**
 * @author haitao.chen
 * @date 2020/7/8
 */
@Data
public class CacheUtils {

    public int expandLockTime(String field, String key, String value, int lockTime){
        return 1;
    }

    public void delLock(String lockField, String lockKey, String randomValue){

    }
}
