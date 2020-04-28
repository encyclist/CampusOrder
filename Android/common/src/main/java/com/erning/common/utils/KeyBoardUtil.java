package com.erning.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

/**
 * 显示或隐藏软键盘
 * Created by abs on 2018/1/9.
 */

public class KeyBoardUtil {
    // 用来记录键盘隐藏时的高度
    private static int hideHeight;

    public static void hideSoftKeyboard(EditText editText, Activity activity) {
        if (editText != null && activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();
            if(isOpen) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
            }
        }
    }
    public static void showSoftKeyboard(EditText editText, Activity activity) {
        if (editText != null && activity != null) {
            editText.post(() -> {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            });
        }
    }


    public static void showSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 监听键盘变化
     * 千万不能忘记移除监听
     */
    public static void observeSoftKeyboard(View rootView,  final OnSoftKeyboardChangeListener listener) {
        // rootView = activity.getWindow().getDecorView();
        if(rootView==null){
            throw new IllegalArgumentException("rootView不能为null");
        }
        final View decorView = rootView;
        final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    if (hide){
                        hideHeight = keyboardHeight;
                    }
                    listener.onSoftKeyBoardChange(this,keyboardHeight-hideHeight, !hide);
                }
                previousKeyboardHeight = height;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    public static void finishObserveSoftKeyBoard(View rootView,ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener){
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener,int softKeyboardHeight, boolean visible);
    }
}
