package com.wellhope.vo;

import com.wellhope.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author GaoJ
 * @create 2021-03-03 23:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO implements Serializable {

    private Product product;
    private String productDesc;
}
