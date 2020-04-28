package com.erning.campusorder.util;

import com.erning.common.net.Network;
import com.erning.common.net.RemoteService;

public abstract class FragmentPresenter<View extends PresenterFragment> {
    private View mView;
    protected RemoteService service = Network.remote();

    public abstract void init();

    public FragmentPresenter(View view) {
        setView(view);
    }

    /**
     * 设置一个View，子类可以复写
     */
    protected void setView(View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public String getUserId(){
        return mView.getUserId();
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     */
    public final View getView() {
        return mView;
    }

    public void destroy() {
        PresenterFragment view = mView;
        mView = null;
        if (view != null) {
            // 把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}