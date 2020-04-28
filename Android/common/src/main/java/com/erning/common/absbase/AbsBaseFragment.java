package com.erning.common.absbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.erning.common.R;
import com.erning.common.dialog.MyViewDialog;
import com.erning.common.dialog.MyprogressDialog;
import com.erning.common.net.Network;
import com.erning.common.net.RemoteService;
import com.erning.common.sharedperference.SharedPreferencesUtil;
import com.erning.common.utils.KeyBoardUtil;

/**
 * 集成了 网络请求 toast SharedPreferences 键盘显隐 startActivity
 * 详见{@link AbsBaseActivity}
 * Created by 二宁 on 2017/11/23.
 */
public abstract class AbsBaseFragment extends Fragment {
    private static final String TAG = "AbsBaseFragment";

    public View mRoot;
    public Context mContext;
    protected SharedPreferences sp;
    public MyprogressDialog progressDialog;
    private Toast toast;

    private boolean mIsFirstInitData = true;
    protected boolean haveView = true;
    private RemoteService service = Network.remote();

    protected abstract int getLayoutResId();
    protected abstract void initData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public RemoteService getService(){
        if (Network.Authorization.isEmpty())
            Network.Authorization = getSpString("token");
        return service;
    }

    public void closeInputMethod(EditText editText) {
        KeyBoardUtil.hideSoftKeyboard(editText,getActivity());
    }

    public void showSoftInputFromWindow(EditText editText){
        KeyBoardUtil.showSoftKeyboard(editText,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot == null) {
            mContext = this.getActivity().getApplicationContext();
            sp = PreferenceManager.getDefaultSharedPreferences(mContext);

            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(getLayoutResId(), container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        haveView = true;
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false;
            // 触发
            onFirstInit();
        }
        if("".equals(Network.Authorization)){
            Network.Authorization = getSpString("token");
        }
        // 当View创建完成后初始化数据
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        haveView = false;
    }

    /**
     * 初始化控件
     */
    protected void initWidget(View root) {
    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit() {

    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(mContext, cls));
    }
    public void startActivity(Class<?> cls, String[] key, String[] values) {
        Intent intent = new Intent(mContext, cls);
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], values[i]);
            }
        }
        startActivity(intent);
    }
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toast(int res) {
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(mContext, getResources().getString(res), Toast.LENGTH_SHORT);

        if(toast != null)
            toast.show();
    }
    public void toast(CharSequence content) {
        if(toast != null)
            toast.cancel();

        toast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);

        if(toast != null)
            toast.show();
    }

    public String getSpString(String key) {
        return SharedPreferencesUtil.getSpString(mContext,key);
    }
    public void putSpString(String key, String value) {
        SharedPreferencesUtil.putSpString(mContext,key,value);
    }
    public long getSpLong(String key) {
        return sp.getLong(key, 0);
    }
    protected void putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public void showProgressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new MyprogressDialog(mContext);
            progressDialog.show();
        }
    }
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void showNotifyDialog(String content, View.OnClickListener OKClickListener, View.OnClickListener cancelClickListener){
        View view = getLayoutInflater().inflate(R.layout.notisdialog,null,false);
        ((TextView)view.findViewById(R.id.text_notis)).setText(content);
        view.findViewById(R.id.text_ds_ok).setOnClickListener(OKClickListener);
        view.findViewById(R.id.text_notis).setOnClickListener(cancelClickListener);
        new MyViewDialog(mContext,view).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}