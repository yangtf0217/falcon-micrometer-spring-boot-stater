package com.kobe.falcon.micrometer;

public class Response<T> {

    private int code;
    private String msg;
    private T body;
    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.body = data;
    }


    public static final Response<Boolean> OK = new Response<>(ErrorCode.SUCCESS.getCode(),ErrorCode.SUCCESS.getDesc());
    public static final Response<Boolean> FAIL = new Response<>(ErrorCode.FAIL.getCode(),ErrorCode.FAIL.getDesc());

    public static <T> Response<T> makeResponse(int code, String msg, T obj) {
        Response<T> result = new Response<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj);
        return result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return body;
    }

    public void setData(T data) {
        this.body = data;
    }
}
