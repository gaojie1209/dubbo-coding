package com.wellhope.dubboproductservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wellhope.api.ProductService;
import com.wellhope.base.BaseDao;
import com.wellhope.base.BaseServiceImpl;
import com.wellhope.entity.Product;
import com.wellhope.entity.ProductDesc;
import com.wellhope.mapper.ProductDescMapper;
import com.wellhope.mapper.ProductMapper;
import com.wellhope.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GaoJ
 * @create 2021-03-02 23:56
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDescMapper productDescMapper;

    @Override
    public BaseDao<Product> getBaseDao() {
        return productMapper;
    }

//    @Override
//    public PageInfo<Product> page(Integer pageIndex, Integer pageSize) {
//        //1.设置分页信息
//        PageHelper.startPage(pageIndex,pageSize);
//        //2.获取到集合信息,(受上面影响list查询已经变成limit)
//        List<Product> list = this.list();
//        //3.返回分页对象(3为每页显示条数)
//        PageInfo<Product> pageInfo = new PageInfo<Product>(list,3);
//        return pageInfo;
//    }

    @Override
    @Transactional
    public Long add(ProductVO productVO) {
        //1添加商品的基本信息
         productMapper.insertSelective(productVO.getProduct());
       // ProductDesc productDesc=new ProductDesc (null,productVO.getProduct().getId(), productVO.getProductDesc());
        productDescMapper.insertSelective(new ProductDesc (null,productVO.getProduct().getId(), productVO.getProductDesc()));
        return productVO.getProduct().getId();
    }
}
