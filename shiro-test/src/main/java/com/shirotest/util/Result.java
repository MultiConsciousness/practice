package com.shirotest.util;

public class Result {
    private final static int UNNORMAL_STATUS = 400;
    private final static int NORMAL_STATUS = 200;
    private int status;
    private String msg;
    private Object data;

    private Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result normal(String msg, Object data) {
        return new Result(NORMAL_STATUS, msg, data);
    }

    public static Result normal(String msg){
        return new Result(NORMAL_STATUS, msg, null);
    }

    public static Result unnormal(String msg, Object data) {
        return new Result(UNNORMAL_STATUS, msg, data);
    }

    public static Result unnormal(String msg){
        return new Result(UNNORMAL_STATUS, msg, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
