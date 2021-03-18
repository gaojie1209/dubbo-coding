package com.wellhope.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一定义了返回json的接口描述类型
 * @author GaoJ
 * @create 2021-03-06 23:53
 */
@Data
@AllArgsConstructor
public class ResultBean <T> implements Serializable {
    private String statusCode;
    private T data;
}
