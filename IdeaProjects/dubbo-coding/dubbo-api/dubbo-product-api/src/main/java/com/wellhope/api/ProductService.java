package com.wellhope.api;

import com.github.pagehelper.PageInfo;
import com.wellhope.base.BaseDao;
import com.wellhope.base.BaseService;
import com.wellhope.entity.Product;
import com.wellhope.vo.ProductVO;

import java.util.List;

/**
 * 商品接口
 * @author GaoJ
 * @create 2021-03-02 23:49
 */
public interface ProductService extends BaseService<Product> {

//    public PageInfo<Product> page(Integer pageIndex, Integer pageSize);

    public Long add(ProductVO productVO);
}
