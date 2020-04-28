package com.erning.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.erning.common.utils.DisplayUtil;
import com.erning.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 有个BUG ：当未设置title时，无法测量行高
 */
@SuppressWarnings({"unused"})
public class FlexView extends View {
    private static final String TAG = "FlexView";
    //屏幕大小
    private int width = 0;
    //内边距
    private float paddingLeft = 8;
    private float paddingRight = 8;
    private float paddingTop = 6;
    private float paddingBottom = 6;
    //画笔颜色
    private int tagColor = Color.parseColor("#333333");
    private int nameColor = Color.parseColor("#985D3E");
    private int authColor = Color.parseColor("#999999");
    //文字大小
    private float tagSize = 16;
    private float nameSize = 16;
    private float authSize = 12;
    //画笔
    private Paint tagPaint = new Paint();
    private Paint namePaint = new Paint();
    private Paint authPaint = new Paint();
    //文字内容
    private String title;
    private List<String> name;
    private List<String> auth;
    //存放位置的东西
    private ArrayList<HotZone> hzs = new ArrayList<>();

    public FlexView(Context context) {
        super(context);
        init();
    }
    public FlexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public FlexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public FlexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setData(String title,List<String> name,List<String> auth){
        this.title = title==null ? "" : title;
        this.name = name==null ? new ArrayList<>() : name;
        this.auth = auth==null ? new ArrayList<>() : auth;

        if (this.name.size() != this.auth.size()){
            throw new IllegalArgumentException("书名和作者数量不一致");
        }

        invalidate();
    }

    private String getTitle(){
        return TextUtils.isEmpty(title) ? "汉" : title;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
        initColor();
    }

    public void setNameColor(int nameColor) {
        this.nameColor = nameColor;
        initColor();
    }

    public void setAuthColor(int authColor) {
        this.authColor = authColor;
        initColor();
    }

    public void setColors(int tagColor,int nameColor,int authColor){
        this.tagColor = tagColor;
        this.nameColor = nameColor;
        this.authColor = authColor;
        initColor();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
    }

    private void init(){
        tagSize = DisplayUtil.sp2px(getContext(),tagSize);
        nameSize = DisplayUtil.sp2px(getContext(),nameSize);
        authSize = DisplayUtil.sp2px(getContext(),authSize);
        paddingLeft = DisplayUtil.sp2px(getContext(),paddingLeft);
        paddingRight = DisplayUtil.sp2px(getContext(),paddingRight);
        paddingTop = DisplayUtil.sp2px(getContext(),paddingTop);
        paddingBottom = DisplayUtil.sp2px(getContext(),paddingBottom);

        initColor();
    }
    private void initColor(){
        tagPaint.setColor(tagColor);
        tagPaint.setTextSize(tagSize);
        tagPaint.setAntiAlias(true);
        namePaint.setColor(nameColor);
        namePaint.setTextSize(nameSize);
        namePaint.setAntiAlias(true);
        authPaint.setColor(authColor);
        authPaint.setTextSize(authSize);
        authPaint.setAntiAlias(true);
    }

    private Rect rect1 = new Rect();
    private Rect rect2 = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"开始绘制");
        hzs.clear();
        if (title == null || name == null || auth == null)
            return;
        //当前绘制位置
        int y = (int) paddingTop;
        int x = 0;

        tagPaint.getTextBounds(getTitle(),0,getTitle().length(),rect1);
        float w;
        if (title.isEmpty())
            w = paddingLeft;
        else
            w = rect1.width() + paddingLeft;
        hzs.add(new HotZone(x,x+w,y-paddingTop,y+rect1.height()+paddingBottom));
        y += rect1.height();
        canvas.drawText(title,x,y,tagPaint);
        x += w;

        for (int i=0;i<name.size();i++) {
            String str1 = name.get(i);
            String str2 = auth.get(i);

            namePaint.getTextBounds(str1,0,str1.length(),rect1);
            authPaint.getTextBounds(str2,0,str2.length(),rect2);

            w = paddingLeft + rect1.width() + rect2.width() + paddingRight;
            float h = paddingTop + rect1.height() + paddingBottom;

            if (x + w <= width){
                canvas.drawText(str1,x,y,namePaint);
                canvas.drawText(str2,x+rect1.width()+8,y,authPaint);
                hzs.add(new HotZone(x,x+w,y-rect1.height()-paddingTop,y+paddingBottom));
                x += w;
            }else{
                x = 0;
                y += h;
                canvas.drawText(str1,x,y,namePaint);
                canvas.drawText(str2,x+rect1.width()+8,y,authPaint);
                hzs.add(new HotZone(x,x+w,y-rect1.height()-paddingTop,y+paddingBottom));
                x += w;
            }
        }
    }

    private float touchX = 0;
    private float touchY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                if (Math.abs(touchX - event.getX()) <= 10 && Math.abs(touchY - event.getY()) <= 10){
                    Log.d(TAG,"点击了");
                    for (int i=0;i<hzs.size();i++){
                        if (i == 0)
                            continue;
                        if (hzs.get(i).include(touchX,touchY)){
                            if (listener != null)
                                listener.onTextClick(this,i-1);
                            break;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //避免在 scrollView 里获取不到高度
        if (heightMeasureSpec == 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.AT_MOST);
        }

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 0;
        int mHeight = (int) paddingTop;

        if (title != null && name != null && auth != null){
            tagPaint.getTextBounds(getTitle(),0,getTitle().length(),rect1);
            float w;
            if (title.isEmpty())
                w = paddingLeft;
            else
                w = rect1.width() + paddingLeft;
            mHeight += rect1.height();
            mWidth += w;

            for (int i=0;i<name.size();i++) {
                String str1 = name.get(i);
                String str2 = auth.get(i);

                namePaint.getTextBounds(str1,0,str1.length(),rect1);
                authPaint.getTextBounds(str2,0,str2.length(),rect2);

                w = paddingLeft + rect1.width() + rect2.width() + paddingRight;
                float h = paddingTop + rect1.height() + paddingBottom;

                if (mWidth + w <= widthSize){
                    mWidth += w;
                }else{
                    mWidth = 0;
                    mHeight += h;
                    mWidth += w;
                }
            }
            mHeight += paddingBottom;
        }

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(width, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(width, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    public interface OnTextClickListener{
        void onTextClick(FlexView view, int index);
    }
    private OnTextClickListener listener;
    public void setOnTextClickListener(OnTextClickListener listener) {
        this.listener = listener;
    }
    public boolean haveListener() {
        return listener!=null;
    }
}