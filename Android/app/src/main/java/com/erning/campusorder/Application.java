package com.erning.campusorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erning.campusorder.activity.LoginActivity;
import com.erning.common.sharedperference.SharedPreferencesUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application {
    public static Application instance;
    private List<Activity> activities = new ArrayList<>();

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.colorPrimary);//全局设置主题颜色
                return new ClassicsHeader(context).setFinishDuration(0);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20).setFinishDuration(0);
            }
        });
    }

    public static void showToast(String msg) {
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }

    public static void logout() {
        SharedPreferencesUtil.putSpString(instance,"id","");
        SharedPreferencesUtil.putSpString(instance,"tel","");

        Intent intent = new Intent(instance,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        for (Activity activity:instance.activities){
            if (activity.getClass() != LoginActivity.class){
                activity.finish();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Fresco.initialize(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                activities.add(activity);
            }
            @Override
            public void onActivityStarted(@NonNull Activity activity) { }
            @Override
            public void onActivityResumed(@NonNull Activity activity) { }
            @Override
            public void onActivityPaused(@NonNull Activity activity) { }
            @Override
            public void onActivityStopped(@NonNull Activity activity) { }
            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) { }
            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                activities.remove(activity);
            }
        });
    }
}
