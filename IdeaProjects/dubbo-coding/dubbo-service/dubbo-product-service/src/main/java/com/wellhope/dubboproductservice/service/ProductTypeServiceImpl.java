package com.wellhope.dubboproductservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wellhope.api.ProductTypeService;
import com.wellhope.base.BaseDao;
import com.wellhope.base.BaseServiceImpl;
import com.wellhope.entity.ProductType;
import com.wellhope.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author GaoJ
 * @create 2021-03-07 10:45
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Resource(name="myStringRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public BaseDao<ProductType> getBaseDao() {
        return productTypeMapper;
    }

    /**
     * 重写获取列表的方法，加入缓存的逻辑 硬件 内存读取速度>>磁盘读取速度
     * @return
     */
    @Override
    public List<ProductType> list() {
        //1查询当前缓存是否存在分类的信息
        List<ProductType> list= (List<ProductType>) redisTemplate.opsForValue().get("productType:list");
        if (list==null||list.size()==0){
            //2缓存不存在，则查询数据库
            list=super.list();
            //3将查询的结果保存到缓存中
            redisTemplate.opsForValue().set("productType:list",list);
        }
        return list;
    }
}
