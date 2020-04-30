package com.erning.campusorder.util;

import android.widget.Toast;

import androidx.annotation.CallSuper;

import com.erning.common.absbase.AbsBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class PresenterActivity<Presenter extends ActivityPresenter> extends AbsBaseActivity {

    private Unbinder unbinder;
    protected Presenter presenter;

    @Override
    protected void initWindow() {
        super.initWindow();
        // 初始化Presenter
        presenter = initPresenter();
    }

    @Override
    @CallSuper
    protected void initControls() {
        unbinder = ButterKnife.bind(this);
    }

    public String getUserId(){
        return getSpString("id");
    }

    /**
     * 初始化Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 界面关闭时进行销毁的操作
        if (presenter != null) {
            presenter.destroy();
        }
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    public void setPresenter(Presenter basePresenter) {
        presenter = basePresenter;
    }

    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
