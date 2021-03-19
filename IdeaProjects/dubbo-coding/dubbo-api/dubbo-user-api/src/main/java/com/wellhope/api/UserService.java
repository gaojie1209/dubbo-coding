package com.wellhope.api;

import com.wellhope.base.BaseService;
import com.wellhope.entity.ProductType;
import com.wellhope.entity.User;
import com.wellhope.pojo.ResultBean;

/**
 * 用户
 * @author GaoJ
 * @create 2021-03-18 16:47
 */
public interface UserService extends BaseService<User> {
    public ResultBean checkUserNameIsExists(String username);
    public ResultBean checkPhoneIsExists(String phone);
    public ResultBean checkEmailIsExists(String email);
    public ResultBean generateCode(String identification);
    //目前分析得到的结论
    //添加用户，是否可以用默认的实现？
    //可以。

    //激活用户，改变用户的状态值，也不需要写
}
