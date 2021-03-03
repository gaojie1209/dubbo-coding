package com.wellhope.dubboproductservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wellhope.api.ProductService;
import com.wellhope.entity.Product;
import com.wellhope.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author GaoJ
 * @create 2021-03-02 23:56
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getById(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
