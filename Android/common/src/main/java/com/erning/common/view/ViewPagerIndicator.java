package com.erning.common.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.erning.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager指示器
 * Created by 二宁 on 2018/4/16.
 */

public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private int size;
    private int img1 = R.mipmap.ic_choose, img2 = R.mipmap.ic_nochoose;
    private int imgSize = 15;
    private List<ImageView> dotViewLists = new ArrayList<>();

    public ViewPagerIndicator(Context context, LinearLayout dotLayout, int size) {
        this.context = context;
        this.dotLayout = dotLayout;
        this.size = size;

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //为小圆点左右添加间距
            params.leftMargin = 10;
            params.rightMargin = 10;
            //手动给小圆点一个大小
            params.height = imgSize;
            params.width = imgSize;
            if (i == 0) {
                imageView.setBackgroundResource(img1);
            } else {
                imageView.setBackgroundResource(img2);
            }
            //为LinearLayout添加ImageView
            dotLayout.addView(imageView, params);
            dotViewLists.add(imageView);
        }
    }

    public ViewPagerIndicator(Context context, ViewPager viewPager, LinearLayout dotLayout, int size) {
        this.context = context;
        this.viewPager = viewPager;
        this.dotLayout = dotLayout;
        this.size = size;

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //为小圆点左右添加间距
            params.leftMargin = 10;
            params.rightMargin = 10;
            //手动给小圆点一个大小
            params.height = imgSize;
            params.width = imgSize;
            if (i == 0) {
                imageView.setBackgroundResource(img1);
            } else {
                imageView.setBackgroundResource(img2);
            }
            //为LinearLayout添加ImageView
            dotLayout.addView(imageView, params);
            dotViewLists.add(imageView);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < size; i++) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if ((position % size) == i) {
                ((View) dotViewLists.get(i)).setBackgroundResource(img1);
            } else {
                ((View) dotViewLists.get(i)).setBackgroundResource(img2);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}