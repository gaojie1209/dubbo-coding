package com.wellhope.dubbocenter.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wellhope.api.ProductService;
import com.wellhope.contant.MQConstant;
import com.wellhope.entity.Product;
import com.wellhope.vo.ProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品页面
 * @author GaoJ
 * @create 2021-03-03 11:47
 */
@Controller
@RequestMapping("product")
public class ProductController {

    /**
     * 启动时检查机制 check = false
     */
    @Reference(check = false)
    private ProductService productService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("get/{id}")
    @ResponseBody
    public Product getById(@PathVariable("id") Long id){
        return productService.selectByPrimaryKey(id);
    }

    /**
     * 不分页列表
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String list(Model model){
        //1获取数据
        List<Product> products = productService.list();
        model.addAttribute("products",products);
        //2.展示页面
        return "product/list";
    }
   // http://www.gj.com:9090/product/page/1/3
    /**
     * 分页列表
     * @param model
     * @return
     */
    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(Model model,@PathVariable("pageIndex") Integer pageIndex,@PathVariable("pageSize") Integer pageSize){
        //1获取数据
        PageInfo<Product> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page",page);
        //2.展示页面
        return "product/list";
    }

    /**
     * 添加商品
     * @param productVO
     * @return
     */
    @PostMapping("add")
    public String add(ProductVO productVO){
        Long newId = productService.add(productVO);
        //发送一个消息到消息中间件
      //  rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE,"product.add",newId);
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE,"product.add",newId);
        //跳回第一页 展示的时候 按照添加时间排序
        return "redirect:/product/page/1/3";
    }
    
}
