package com.cloneZjrt.result;

public class ReturnResult {
    private Object data;
    private String code;
    private String msg;

    private ReturnResult( Object data, String code, String msg) {

        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    private ReturnResult(Object data) {
        this.data = data;
        this.code = ReturnMsg.SUCCESS.getCode();
        this.msg = ReturnMsg.SUCCESS.getMsg();
    }

    private ReturnResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnResult() {
        this.code = ReturnMsg.ERROR.getCode();
        this.msg = ReturnMsg.ERROR.getMsg();
    }

    public static ReturnResult success( Object data, String code, String msg) {
        return new ReturnResult( data, code, msg);
    }


    public static ReturnResult success(Object data) {
        return new ReturnResult(data);
    }


    public static ReturnResult success() {
        return new ReturnResult("");
    }


    public static ReturnResult error() {
        return new ReturnResult();
    }

    public static ReturnResult error(String code, String msg) {
        return new ReturnResult(code, msg);
    }
    //getter和setter

}

