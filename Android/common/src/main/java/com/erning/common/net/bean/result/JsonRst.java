package com.erning.common.net.bean.result;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonRst extends BaseResultModel {
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public JSONObject getData() {
        return (JSONObject) data;
    }

    public JSONArray getArray() {
        return (JSONArray) data;
    }

    public int getInt(){
        return (int) data;
    }

    public String getStr(){
        return (String) data;
    }
}
