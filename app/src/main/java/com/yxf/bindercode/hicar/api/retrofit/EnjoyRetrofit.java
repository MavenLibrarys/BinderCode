package com.yxf.bindercode.hicar.api.retrofit;

import androidx.annotation.Nullable;

import com.yxf.bindercode.hicar.api.retrofit.annotation.Field;
import com.yxf.bindercode.hicar.api.retrofit.annotation.GET;
import com.yxf.bindercode.hicar.api.retrofit.annotation.POST;
import com.yxf.bindercode.hicar.api.retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.HttpUrl;

public class EnjoyRetrofit {
    HttpUrl baseUrl;

    public EnjoyRetrofit(Builder builder) {
        baseUrl = builder.baseUrl;
    }

    public <T> T create(Class<T> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                boolean isHasBody = false;
                String relativeUrl = null;
                GetParamsHandler[] getParamsHandlers = new GetParamsHandler[args.length];
                PostParamsHandler[] postParamsHandler = new PostParamsHandler[args.length];
                //解析方法上的注解
                Annotation[] annotations = method.getAnnotations();
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];
                    if (annotation instanceof GET) {
                        relativeUrl = ((GET) annotation).value();
                    } else if (annotation instanceof POST) {
                        isHasBody = true;
                        relativeUrl = ((POST) annotation).value();
                    }
                }
                //解析方法参数注解
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] parameterAnnotation = parameterAnnotations[i];
                    for (int j = 0; j < parameterAnnotation.length; j++) {
                        Annotation annotation = parameterAnnotation[j];
                        if (annotation instanceof Query) {
                            String key = ((Query) annotation).value();
                            getParamsHandlers[i] = new GetParamsHandler.Builder().setKey(key).build();
                        } else if (annotation instanceof Field) {
                            String key = ((Field) annotation).value();
                            postParamsHandler[i] = new PostParamsHandler.Builder().setKey(key).build();
                        }
                    }
                }
                MethodService methodService = new MethodService.Builder()
                        .setBaseUrl(baseUrl.toString())
                        .setRelativePath(relativeUrl)
                        .setHasBody(isHasBody)
                        .setGetParamsHandler(getParamsHandlers)
                        .setPostParamsHandler(postParamsHandler)
                        .build();
                return methodService.invoke(args);
            }
        });
    }

    public static class Builder {
        private @Nullable
        HttpUrl baseUrl;

        public Builder baseUrl(@Nullable String baseUrl) {
            this.baseUrl = HttpUrl.get(baseUrl);
            return this;
        }

        public EnjoyRetrofit build() {
            return new EnjoyRetrofit(this);
        }
    }
}
