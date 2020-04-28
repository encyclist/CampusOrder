package com.erning.common.net;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class FastJsonConverterFactory extends Converter.Factory {

    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter<>(type);
    }

    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换发送给服务器的数据
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter<>();
    }

    static class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Type type;

        public FastJsonResponseBodyConverter(Type type) {
            this.type = type;
        }

        /*
         * 转换方法
         */
        @Override
        public T convert(ResponseBody value) throws IOException {
            String tempStr = "";
            try{
                BufferedSource bufferedSource = Okio.buffer(value.source());
                tempStr = bufferedSource.readUtf8();
                bufferedSource.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            T res;
            try {
                res = JSON.parseObject(tempStr, type);
            }catch (Exception e){
                e.printStackTrace();
                res = JSON.parseObject("{\"code\":0,\"msg\":\"解析数据错误时候你能看到我，我在FastJsonConverterFactory\"}",type);
            }
            return res;
        }
    }

    static class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        @Override
        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
        }


    }
}
