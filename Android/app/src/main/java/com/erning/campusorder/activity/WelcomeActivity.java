package com.erning.campusorder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

import com.erning.campusorder.BuildConfig;
import com.erning.campusorder.R;
import com.erning.common.sharedperference.SharedPreferencesUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    private ColorDrawable mBgDrawable;

    @BindView(R.id.text_welcome_version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        View root = findViewById(R.id.activity_welcome);
        int color = Color.parseColor("#31373d");
        ColorDrawable drawable = new ColorDrawable(color);
        root.setBackground(drawable);
        mBgDrawable = drawable;

        startAnim(0.5f, this::reallySkip);

        version.setText("V"+ BuildConfig.VERSION_NAME);
    }

    /**
     * 给背景设置一个动画
     *
     * @param endProgress 动画的结束进度
     * @param endCallback 动画结束时触发
     */
    private void startAnim(float endProgress, final Runnable endCallback) {
        // 获取一个最终的颜色
        int finalColor = Color.WHITE; // UiCompat.getColor(getResources(), R.color.white);
        // 运算当前进度的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);
        // 构建一个属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor);
        valueAnimator.setDuration(3000); // 时间
        valueAnimator.setIntValues(mBgDrawable.getColor(), endColor); // 开始结束值
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 结束时触发
                endCallback.run();
            }
        });
        valueAnimator.start();
    }

    private void reallySkip() {
        if (SharedPreferencesUtil.getSpString(this,"id").isEmpty()){
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
        this.finish();
    }

    private final Property<WelcomeActivity, Object> property = new Property<WelcomeActivity, Object>(Object.class, "color") {
        @Override
        public void set(WelcomeActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }

        @Override
        public Object get(WelcomeActivity object) {
            return object.mBgDrawable.getColor();
        }
    };
}
