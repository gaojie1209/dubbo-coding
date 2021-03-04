package com.wellhope.base;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 抽象类
 * @author GaoJ
 * @create 2021-03-03 13:05
 */
public abstract class BaseServiceImpl<T> implements BaseService<T>{

    
    public abstract BaseDao<T> getBaseDao();
    
    @Override
    public int deleteByPrimaryKey(Long id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T t) {
        return getBaseDao().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getBaseDao().insertSelective(t);
    }

    @Override
    public T selectByPrimaryKey(Long id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getBaseDao().updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(T t) {
        return getBaseDao().updateByPrimaryKeyWithBLOBs(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getBaseDao().updateByPrimaryKey(t);
    }

    @Override
    public List<T> list() {
        return   getBaseDao().list();
    }
    @Override
    public PageInfo<T> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页信息
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取到集合信息,(受上面影响,PageHelper里面有拦截器，list查询已经变成limit,)
        List<T> list = this.list();
        //3.返回分页对象(3为每页显示条数)
        PageInfo<T> pageInfo = new PageInfo<T>(list,3);
        return pageInfo;
    }
}
