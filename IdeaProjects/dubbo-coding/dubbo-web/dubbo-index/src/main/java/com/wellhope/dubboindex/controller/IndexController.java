package com.wellhope.dubboindex.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.api.ProductTypeService;
import com.wellhope.entity.ProductType;
import com.wellhope.pojo.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页
 * @author GaoJ
 * @create 2021-03-07 11:07
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private ProductTypeService productTypeService;

    /**
     * 首页商品分类
     * @param model
     * @return
     */
    @RequestMapping("show")
    public String showIndex(Model model){
        //1获取数据
        List<ProductType> productTypes = productTypeService.list();
        //传递到前端进行展示
        model.addAttribute("productTypes",productTypes);
        return "index";
    }

    /**
     * 首页ajax商品分类
     * @return
     */
    @RequestMapping("listType")
    @ResponseBody
    public ResultBean listType() {
        //1.获取到数据
        List<ProductType> list = productTypeService.list();
        //2.封装返回
        return new ResultBean("200", list);
    }
}
