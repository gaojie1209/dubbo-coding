package com.wellhope.mapper;

import com.wellhope.entity.ProductDesc;

public interface ProductDescMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductDesc record);

    int insertSelective(ProductDesc record);

    ProductDesc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductDesc record);

    int updateByPrimaryKeyWithBLOBs(ProductDesc record);

    int updateByPrimaryKey(ProductDesc record);
}