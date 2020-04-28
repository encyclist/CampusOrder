package com.erning.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.erning.common.R;

@SuppressWarnings("unused")
public abstract class LeftSlideRemoveAdapter extends BaseAdapter {
    protected Context mContext;
    private OnItemRemoveListener mListener;
    private int backgroundColor = Color.parseColor("#ffff4b30");
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemRemove((Integer) v.getTag());
                notifyDataSetChanged();
            }
        }
    };

    public LeftSlideRemoveAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.view_left_slide_remove, parent, false);

            holder = new ViewHolder();
            holder.viewContent = convertView.findViewById(R.id.view_content);
            holder.tvRmove = convertView.findViewById(R.id.tv_remove);
            convertView.setTag(holder);

            // viewChild是实际的界面
            holder.viewChild = getSubView(position, null, parent);
            holder.viewContent.addView(holder.viewChild);
            ViewGroup.LayoutParams removeParams = holder.tvRmove.getLayoutParams();
            // 设置大小最大
            holder.viewContent.measure(0,0);
            removeParams.height = holder.viewContent.getMeasuredHeight();
            holder.tvRmove.setLayoutParams(removeParams);
            // 设置背景色
            holder.tvRmove.setBackgroundColor(backgroundColor);
        } else {
            holder = (ViewHolder) convertView.getTag();
            getSubView(position, holder.viewChild, parent);
        }
        holder.tvRmove.setTag(position);
        holder.tvRmove.setOnClickListener(onClickListener);
        return convertView;
    }

    public OnItemRemoveListener getmListener() {
        return mListener;
    }

    public void setmListener(OnItemRemoveListener mListener) {
        this.mListener = mListener;
    }

    private class ViewHolder {
        RelativeLayout viewContent;
        View viewChild;
        View tvRmove;
    }
    public abstract View getSubView(int position, View convertView, ViewGroup parent);
    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

