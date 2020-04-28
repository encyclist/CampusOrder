package com.erning.campusorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erning.campusorder.activity.LoginActivity;
import com.erning.common.sharedperference.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application {
    public static Application instance;
    private List<Activity> activities = new ArrayList<>();

    public static void showToast(String msg) {
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }

    public static void logout() {
        SharedPreferencesUtil.putSpString(instance,"id","");

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
