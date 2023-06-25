package com.synpulse.portal.exception;

public enum ErrorCode {

    CODE_SUCCESS("success",1000),
    BUSY("system error",1001),
    USER_NOT_FOUND("user not found",1002),
    BANLANCE_NOT_ENOUGH("banlacne not enough",1003),
    TRANSACTION_FAIL("transaction fail",1004),
    INVALID_TOKEN("invalid token",1005),
    USER_NOT_EXIST("user not exist",1006),
    SAME_ACCOUNT("same account", 1007);

    ErrorCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    private String msg;
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
