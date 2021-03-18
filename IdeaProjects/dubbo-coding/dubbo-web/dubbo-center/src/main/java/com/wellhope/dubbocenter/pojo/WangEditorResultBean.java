package com.wellhope.dubbocenter.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 针对富文本框返回的数据做了封装
 * @author GaoJ
 * @create 2021-03-07 1:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WangEditorResultBean {

    private String errno;
    private String[] data;

}
