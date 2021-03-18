package com.wellhope.dubbosearch.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.api.SearchService;
import com.wellhope.entity.Product;
import com.wellhope.pojo.PageResultBean;
import com.wellhope.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * @author GaoJ
 * @create 2021-03-07 22:41
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private SearchService searchService;
//    http://www.gj.com:9092/search/synAllDate
    /**
     * 做全量同步，在数据初始化时候用
     * @return
     */
    @RequestMapping("synAllDate")
    @ResponseBody
    public ResultBean<String> synAllDate(){
        return searchService.synAllDate();
    }

    /**
     * 此接口适合于APP端
     * 或者Ajax异步加载数据的方式
     * @param keywords
     * @return
     */
    @RequestMapping("queryByKeyWords")
    @ResponseBody
    public ResultBean<List<Product>> queryByKeyWords(String keywords){

        return searchService.queryByKeywords(keywords);
    }
//    http://localhost:9092/search/queryByKeyWords4PC?keywords=%E8%8B%B9%E6%9E%9C
    /**
     * 搜索列表
     * @param keywords
     * @param model
     * @return
     */
    @RequestMapping("queryByKeyWords4PC")
    public String queryByKeyWords4PC(String keywords, Model model){
        ResultBean<List<Product>> resultBean = searchService.queryByKeywords(keywords);
        if("200".equals(resultBean.getStatusCode())){
            List<Product> products = (List<Product>) resultBean.getData();
            model.addAttribute("products",products);
        }
        return "list";
    }
//    http://www.gj.com:9092/search/queryByKeyWords4PC/1/2?keywords=%E8%8B%B9%E6%9E%9C
    /**
     * 分页搜索列表
     * @param keywords
     * @param model
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("queryByKeyWords4PC/{pageIndex}/{pageSize}")
    public String queryByKeyWords4PC(String keywords, Model model, @PathVariable("pageIndex") Integer pageIndex,
                                     @PathVariable("pageSize") Integer pageSize){
        ResultBean<PageResultBean<Product>> resultBean = searchService.queryByKeywords(keywords,pageIndex,pageSize);
        if("200".equals(resultBean.getStatusCode())){
            PageResultBean<Product> pageResultBean = (PageResultBean<Product>) resultBean.getData();
            model.addAttribute("page",pageResultBean);
        }
        return "list";
    }
}
