package com.example.demo.seckill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haitao.chen
 * @date 2020/7/1
 */

@RestController

public class SecKillController {


    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/{goodsId}/getUrl")
    public Exposer exposer(@PathVariable("goodsId") Long seckillGoodsId)
    {
        //goodsId表示是什么商品，然后根据该商品的数据库依次获得尚未被秒杀的每个商品的唯一ID，然后根据商品的唯一ID来生成唯一的秒杀URL
//        seckillGoodsId为某个商品的唯一id
//        其中这一步可以省，之间将goodsId表示的传递给exportSeckillUrl也可以完成
        //异常判断省掉，返回上述的model对象。即包含md5的对象
        Exposer result =secKillService.exportSeckillUrl(seckillGoodsId);
        return result;
    }


    @RequestMapping(value = "/{goodsId}/{md5}/execution")
    public Boolean execution(@PathVariable("goodsId") Long seckillGoodsId,@PathVariable("md5") String md5){
        Boolean result = secKillService.executionSeckillId(seckillGoodsId,md5);
        //executionSeckillId的操作是强事务,操作为减库存+增加购买明细，最终返回是否秒杀成功，秒杀成功的商品消息等。这里省，只返回是否接口合理的信息。
        return  result;
    }


}

