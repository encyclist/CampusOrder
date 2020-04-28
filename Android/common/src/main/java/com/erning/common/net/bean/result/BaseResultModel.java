package com.erning.common.net.bean.result;

/**
 * Created by abs on 2018/4/19.
 */

public class BaseResultModel {
    public int code;
    public String msg;

    @Override
    public String toString() {
        return "BaseResultModel{" +
                "code=" + code +
                ", message='" + msg + '\'' +
                '}';
    }
}
