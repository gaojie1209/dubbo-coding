package com.wellhope.base;


import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 抽取共性的方法，普通的增删改查
 * 接口：定义规范
 * @author GaoJ
 * @create 2021-03-03 13:04
 */
public interface BaseService<T> {
    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKeyWithBLOBs(T t);

    int updateByPrimaryKey(T t);

    public List<T> list();

    public PageInfo<T> page(Integer pageIndex, Integer pageSize);
}
