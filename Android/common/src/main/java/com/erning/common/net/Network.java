package com.erning.common.net;

import com.erning.common.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 网络请求的封装
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class Network {
    public static final String ROOT = "http://192.168.199.90:10208/";
//    public static final String ROOT = "http://47.101.44.42:10208/";
    public static String Authorization = "";

    private static Network instance = new Network();
    private Retrofit retrofit;
    private OkHttpClient client;

    private Network() { }

    private static OkHttpClient getClient() {
        if (instance.client != null) {
            return instance.client;
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60,TimeUnit.SECONDS);
        builder.connectTimeout(60,TimeUnit.SECONDS);
        /*builder.addNetworkInterceptor(new Interceptor(){
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Headers.Builder builder = request.headers().newBuilder();
                //统一追加Header参数
                Headers newBuilder = builder.add("Authorization", Authorization).build();
                Request newRequest = request.newBuilder().headers(newBuilder).build();
                return chain.proceed(newRequest);
            }
        });*/
        /*SignInterceptor signInterceptor = new SignInterceptor.Builder()
                .setKey("qwertyuiopASDFGHJKLzxcvbnm")
                .build();
        builder.addInterceptor(signInterceptor);*/
        if(BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        instance.client = builder.build();
        return instance.client;
    }

    // 构建一个Retrofit
    private static Retrofit getRetrofit() {
        if (instance.retrofit != null) {
            return instance.retrofit;
        }

        // 得到一个OK Client
        OkHttpClient client = getClient();

        // Retrofit
        Retrofit.Builder builder = new Retrofit.Builder();

        // 设置电脑链接
        instance.retrofit = builder.baseUrl(ROOT)
                // 设置client
                .client(client)
                // 设置Json解析器
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        return instance.retrofit;

    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remote() {
        return Network.getRetrofit().create(RemoteService.class);
    }
}
