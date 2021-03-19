package com.wellhope.api;

import com.wellhope.api.pojo.SMSResponse;

/**
 * 发送短信
 * @author GaoJ
 * @create 2021-03-18 21:38
 */
public interface SMS {
    //也支持同步调用
    public SMSResponse sendCodeMessage(String phone, String code);

    public SMSResponse sendBirthdayGreetingMessage(String phone,String username);
}
