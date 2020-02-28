package com.cloneZjrt.result;

import lombok.Data;

/**
 * Created by Apple on 2020/2/21.
 */
@Data
public class InfoResult<T> {
    private static final  String SUCCESS_CODE = "200";
    private static final  String FAIL = "500";

    private boolean success;
    private String state;
    private String msg;
    private T datas;

    public static String getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static String getFAIL() {
        return FAIL;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public InfoResult() {
        this.success = true;
        this.state = SUCCESS_CODE;
    }

    public void fail(){
        this.success = false;
        this.state = FAIL;
    }

}
