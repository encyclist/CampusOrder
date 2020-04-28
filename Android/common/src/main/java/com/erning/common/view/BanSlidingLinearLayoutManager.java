package com.erning.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * 禁止recyclerView滑动的LinearLayoutManager
 */
public class BanSlidingLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public BanSlidingLinearLayoutManager(Context context) {
        super(context);
    }

    public BanSlidingLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public BanSlidingLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    /**
     * 禁止滑动
     * canScrollHorizontally（禁止横向滑动）
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollVertically();
    }
    /**
     * 禁止滑动
     * canScrollVertically（禁止竖向滑动）
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
