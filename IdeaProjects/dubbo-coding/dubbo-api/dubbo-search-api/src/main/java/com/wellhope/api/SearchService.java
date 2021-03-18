package com.wellhope.api;

import com.wellhope.pojo.ResultBean;

/**
 * 搜索
 * @author GaoJ
 * @create 2021-03-07 19:38
 */
public interface SearchService {

    /**
     * 做全量同步，在数据初始化时候用
     * @return
     */
    public ResultBean synAllDate();

    /**
     * 增量同步
     * @param id
     * @return
     */
    public ResultBean synById(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    public ResultBean delById(Long id);

    /**
     * 查询操作
     * @param keywords
     * @return
     */
    public ResultBean queryByKeywords(String keywords);

    /**
     * 分页搜索
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize);
}
