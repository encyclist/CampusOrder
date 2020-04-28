package com.erning.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by 二宁 on 2017/11/23.
 */

public abstract class BaseJsonArrayAdapter extends BaseAdapter {
    protected JSONArray dataList;
    protected Context mContext;

    public BaseJsonArrayAdapter(Context ctx, JSONArray list) {
        this.mContext = ctx;
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public JSONObject getItem(int position) {
        return (JSONObject) dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View inflate(int layout) {
        return View.inflate(mContext, layout, null);
    }

    public void addData(JSONArray array){
        dataList.addAll(array);
        notifyDataSetChanged();
    }
    public void addData(JSONObject obj){
        dataList.add(obj);
        notifyDataSetChanged();
    }
    public void setData(JSONArray array){
        dataList = new JSONArray();
        dataList.addAll(array);
        notifyDataSetChanged();
    }
    public void setData(JSONObject obj){
        dataList = new JSONArray();
        dataList.add(obj);
        notifyDataSetChanged();
    }
    public void clear(){
        dataList = new JSONArray();
        notifyDataSetChanged();
    }
}