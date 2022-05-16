package com.springSecurity.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {


    private int code;

    private String msg;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result succ(Object obj){

        return succ(200,"操作成功",obj);

    }

    public static Result succ(int code,String msg,Object obj){

        Result result=new Result();

        result.setCode(code);

        result.setMsg(msg);

        result.setData(obj);

        return result;
    }


    public static Result fail(String msg){

        return fail(400,msg,null);

    }

    public static Result fail(int code,String msg,Object obj){

        Result result=new Result();

        result.setCode(code);

        result.setData(obj);

        result.setMsg(msg);

        return result;
    }





}
