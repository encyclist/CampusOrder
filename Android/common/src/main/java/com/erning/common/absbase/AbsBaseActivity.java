package com.erning.common.absbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.erning.common.R;
import com.erning.common.dialog.MyViewDialog;
import com.erning.common.dialog.MyprogressDialog;
import com.erning.common.net.Network;
import com.erning.common.net.RemoteService;
import com.erning.common.sharedperference.SharedPreferencesUtil;
import com.erning.common.utils.KeyBoardUtil;

/**
 * 集成了 网络请求 toast SharedPreferences 键盘显隐 startActivity
 * Created by 二宁 on 2017/11/23.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    private String TAG = "AbsBaseActivity";
    public Context mContext;
    protected SharedPreferences sp;
    public MyprogressDialog progressDialog;
    protected abstract int getLayoutResId();
    protected abstract void initControls();
    private Toast toast;
    private RemoteService service = Network.remote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutResId());
        mContext = getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);

        initControls();
    }

    @Override
    public Resources getResources() {//还原字体大小
        Resources res = super.getResources();
        Configuration configuration = res.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 联网请求，使用示例（先在{@link Network}和{@link RemoteService}中配置url）：
     *  Kotlin代码：
     * service.login("email","password").enqueue(KtCallBack<JsonRst>{
     *    it 就是返回的数据
     * })
     */
    public RemoteService getService(){
        if (Network.Authorization.isEmpty())
            Network.Authorization = getSpString("token");
        return service;
    }

    /**
     * 关闭软键盘
     */
    public void closeInputMethod(EditText editText) {
        KeyBoardUtil.hideSoftKeyboard(editText,this);
    }
    /**
     * 显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText){
        KeyBoardUtil.showSoftKeyboard(editText,this);
    }

    protected void initWindow() {
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getApplicationContext(), cls));
    }
    public void startActivity(Class<?> cls, String[] key, String[] values) {
        Intent intent = new Intent(getApplicationContext(), cls);
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                intent.putExtra(key[i], values[i]);
            }
        }
        startActivity(intent);
    }
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), cls);
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
            progressDialog = new MyprogressDialog(this);
            progressDialog.show();
        }
    }
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showNotifyDialog(String content, @Nullable View.OnClickListener OKClickListener, @Nullable View.OnClickListener cancelClickListener){
        View view = getLayoutInflater().inflate(R.layout.notisdialog,null,false);
        ((TextView)view.findViewById(R.id.text_notis)).setText(content);
        view.findViewById(R.id.text_ds_ok).setOnClickListener(OKClickListener);
        view.findViewById(R.id.text_notis).setOnClickListener(cancelClickListener);
        new MyViewDialog(mContext,view).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
