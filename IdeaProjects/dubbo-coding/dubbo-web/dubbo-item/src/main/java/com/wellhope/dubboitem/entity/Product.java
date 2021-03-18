package com.wellhope.dubboitem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * freemarker 测试实体类
 * @author GaoJ
 * @create 2021-03-10 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private Long price;
    private Date createTime;
}
