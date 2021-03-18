package com.wellhope.dubboproductservice;

import com.github.pagehelper.PageInfo;
import com.wellhope.api.ProductService;
import com.wellhope.api.ProductTypeService;
import com.wellhope.entity.Product;
import com.wellhope.entity.ProductType;
import com.wellhope.vo.ProductVO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class DubboProductServiceApplicationTests {

    private static final Logger LOGGER  = LoggerFactory.getLogger(DubboProductServiceApplicationTests.class);
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private DataSource dataSource;

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
    /**
     *商品分类列表
     */
    @Test
    void listTypeTest(){
        List<ProductType> productTypes = productTypeService.list();
        productTypes.forEach(System.out::println);
        Assert.assertNotNull(productTypes);
    }

    /**
     * 测试连接池类型
     * @throws SQLException
     */
    @Test
    public void poolTest() throws SQLException {
//        LOGGER.info(String.valueOf(dataSource.getConnection()));
        System.out.println(dataSource.getConnection());
    }
}
