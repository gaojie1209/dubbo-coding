package com.wellhope.sms;

/**
 * @author GaoJ
 * @create 2021-03-18 21:16
 */
public class SMSResponse {
    //{"Message":"OK","RequestId":"4356F613-4A2F-4E92-A196-70862470C3CD","BizId":"123506073525482939^0","Code":"OK"}
    private String message;
    private String requestId;
    private String bizId;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
