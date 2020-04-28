package com.erning.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * 用于放置高德地图的viewpager
 */
public class AMapViewPager extends ViewPager {
    public AMapViewPager(Context context) {
        super(context);
    }
    public AMapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v.getClass().getName().equals("com.amap.api.maps.MapView")) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}