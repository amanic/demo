package com.example.demo.seckill;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author haitao.chen
 * @date 2020/7/1
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    //加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
    private static final String salt = "12sadasadsafafsafs。/。，";

    @Override
    public Exposer exportSeckillUrl(long seckillGoodsId) {
        //首页根据该seckillGoodsId判断商品是否还存在。
        //如果不存在则表示已经被秒杀
        String md5 = getMD5(seckillGoodsId);
        return new Exposer(md5);
    }

    @Override
    public Boolean executionSeckillId(long seckillID,String md5){
        if(md5==null||!md5.equals(getMD5(seckillID))){
            //表示接口错误，不会执行秒杀操作
            return false;
        }
        //接口正确，排队执行秒杀操作。减库存+增加购买明细等信息，这里只返回false
        return  true;
    }


    public static void main(String[] args) {
        System.out.println(getMD5(998685890175L));
    }


    private static String getMD5(long seckillGoodsId) {
        //结合秒杀的商品id与混淆字符串生成通过md5加密
        String base = seckillGoodsId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
