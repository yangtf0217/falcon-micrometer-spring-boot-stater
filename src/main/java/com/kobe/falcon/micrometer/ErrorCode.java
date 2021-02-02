package com.kobe.falcon.micrometer;

public enum ErrorCode {

    SUCCESS(0,"成功"),
    FAIL(1000,"失败");


    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
