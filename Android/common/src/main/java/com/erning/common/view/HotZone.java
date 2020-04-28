package com.erning.common.view;

/**
 * 供 FlexView 使用
 */
public class HotZone {
    public float left = 0;
    public float right = 0;
    public float top = 0;
    public float bottom = 0;

    public HotZone() {
    }

    public HotZone(float left, float right, float top, float bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public boolean include(float x,float y){
        return x>=left && x<=right && y>=top && y<=bottom;
    }

    @Override
    public String toString() {
        return "HotZone{" +
                "left=" + left +
                ", right=" + right +
                ", top=" + top +
                ", bottom=" + bottom +
                '}';
    }
}
