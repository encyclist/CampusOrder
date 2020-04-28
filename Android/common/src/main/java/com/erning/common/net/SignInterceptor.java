package com.erning.common.net;

import com.erning.common.utils.LogUtils;
import com.erning.common.utils.MD5_Calc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class SignInterceptor implements Interceptor {
    private String key = "";
    private Map<String, String> queryParamsMap = new HashMap<>();
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, String> headerParamsMap = new HashMap<>();
    private List<String> headerLinesList = new ArrayList<>();

    private SignInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }

        if (headerLinesList.size() > 0) {
            for (String line: headerLinesList) {
                headerBuilder.add(line);
            }
        }

        requestBuilder.headers(headerBuilder.build());
        // process header params end




        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size() > 0) {
            injectParamsIntoUrl(request, requestBuilder, queryParamsMap);
        }
        // process header params end




        // process post body inject
        if (request.method().equals("POST") && request.body().contentType().subtype().equals("x-www-form-urlencoded")) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }
            }
            RequestBody formBody = formBodyBuilder.build();

            String postBodyString = bodyToString(request.body());
            String bodyStr = bodyToString(formBody);
            postBodyString += ((postBodyString.length() > 0 && bodyStr.length()>0) ? "&" : "") + bodyToString(formBody);
            String signStr = postBodyString + "&key="+key;
//            LogUtils.d("signStr",signStr);
            String sign = MD5_Calc.Md5(signStr).toUpperCase();


            String newPostBodyString = bodyToStringUtf8(request.body());
            bodyStr = bodyToStringUtf8(formBody);
            newPostBodyString += ((newPostBodyString.length() > 0 && bodyStr.length()>0) ? "&" : "") + bodyToStringUtf8(formBody);
            newPostBodyString += ((newPostBodyString.length() > 0) ? "&" : "") + "sign=" + sign;
//            LogUtils.d("newPostBodyString",newPostBodyString);
            requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), newPostBodyString));
        } else {    // can't inject into body, then inject into url
            injectParamsIntoUrl(request, requestBuilder, paramsMap);
        }

        request = requestBuilder.build();
        return chain.proceed(request);
    }

    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }

        requestBuilder.url(httpUrlBuilder.build());
    }

    private static String bodyToStringUtf8(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
    private static String bodyToString(final RequestBody request){
        try {
            return URLDecoder.decode(bodyToStringUtf8(request), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class Builder {

        SignInterceptor interceptor;

        public Builder() {
            interceptor = new SignInterceptor();
        }

        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }

        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.paramsMap.putAll(paramsMap);
            return this;
        }

        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }

        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.headerLinesList.add(headerLine);
            return this;
        }

        public Builder setKey(String key){
            interceptor.key = key;
            return this;
        }

        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine: headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
            }
            return this;
        }

        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }

        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.queryParamsMap.putAll(queryParamsMap);
            return this;
        }

        public SignInterceptor build() {
            return interceptor;
        }

    }
}