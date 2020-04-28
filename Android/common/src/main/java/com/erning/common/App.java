package com.erning.common;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

public class App {
    private static HttpProxyCacheServer proxy;

    public static void init(Context context){
        proxy = new HttpProxyCacheServer(context.getApplicationContext());
    }

    public static HttpProxyCacheServer getProxy() {
        return proxy;
    }
}
