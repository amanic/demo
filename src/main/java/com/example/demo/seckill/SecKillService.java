package com.example.demo.seckill;

/**
 * @author haitao.chen
 * @date 2020/7/1
 */
public interface SecKillService {

    Exposer exportSeckillUrl(long seckillGoodsId);

    Boolean executionSeckillId(long seckillID,String md5);
}
