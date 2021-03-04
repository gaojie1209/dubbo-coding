package com.wellhope.dubboproductservice;

import com.github.pagehelper.PageInfo;
import com.wellhope.api.ProductService;
import com.wellhope.entity.Product;
import com.wellhope.vo.ProductVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class DubboProductServiceApplicationTests {
    @Autowired
    private ProductService productService;

    /**
     * 测试流程是否跑通
     */
    @Test
    void contextLoads() {
        Product product = productService.selectByPrimaryKey(1L);
        System.out.println(product.getName());
    }

    /**
     *商品列表
     */
    @Test
    void list(){
        List<Product> products = productService.list();

        products.forEach(System.out::println);
        Assert.assertNotNull(products);
    }


    /**
     * 分页测试
     */
    @Test
    public void pageTest(){
        PageInfo<Product> page = productService.page(1, 1);
        System.out.println(page.getList().size());
        Assert.assertEquals(page.getList().size(),1);
    }

    /**
     *测试添加
     */
    @Test
    public void addVoTest(){
        Product product = new Product();
        product.setName("格力手机");
        product.setPrice(3999L);
        product.setSalePoint("好用");
        product.setSalePrice(3666L);
        product.setImages("123");
        product.setTypeId(1);
        product.setTypeName("电子数码");
        ProductVO vo = new ProductVO();
        vo.setProduct(product);
        vo.setProductDesc("怎么摔都没问题");

        System.out.println(productService.add(vo));
    }
}
