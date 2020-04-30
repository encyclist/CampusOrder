package com.erning.campusorder.util;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.CallSuper;

import com.erning.common.absbase.AbsBaseFragment;
import com.erning.common.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class PresenterFragment<Presenter extends FragmentPresenter> extends AbsBaseFragment {
    protected Presenter presenter;
    private Unbinder unbinder;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        // 初始化Presenter
        presenter = initPresenter();
    }

    @Override
    @CallSuper
    protected void onFirstInit() {
        LogUtils.d("那啥");
        unbinder = ButterKnife.bind(this,mRoot);
    }

    public String getUserId(){
        return getSpString("id");
    }

    /**
     * 初始化Presenter
     */
    protected abstract Presenter initPresenter();

    @Override
    public void onDestroy() {
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
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}
