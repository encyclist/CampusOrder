package com.erning.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 二宁 on 2017/11/23.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected List<T> dataList;

    public BaseListAdapter(List<T> list) {
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData(){
        return dataList;
    }

    public void addData(List<T> array){
        if (array != null){
            dataList.addAll(array);
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> array){
        if(array == null)
            return;
        dataList.clear();
        dataList.addAll(array);
        notifyDataSetChanged();
    }

    public void addData(int index,List<T> array){
        if (array != null){
            dataList.addAll(index,array);
            notifyDataSetChanged();
        }
    }

    public void addData(T item){
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void addData(int index,T item){
        dataList.add(index,item);
        notifyDataSetChanged();
    }

    public void cleraAll(){
        dataList.clear();
        notifyDataSetChanged();
    }

    public void remove(int index){
        dataList.remove(index);
        notifyDataSetChanged();
    }

    public void remove(T item){
        dataList.remove(item);
        notifyDataSetChanged();
    }
}
