package com.yxf.bindercode.hicar.api.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MethodService {
    private String baseUrl;
    private String relativePath;
    private GetParamsHandler[] getParamsHandler;
    private PostParamsHandler[] postParamsHandler;
    private boolean isHasBody;
    private HttpUrl httpUrl;
    private FormBody.Builder formBody;

    public MethodService(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.relativePath = builder.relativePath;
        this.getParamsHandler = builder.getParamsHandler;
        this.postParamsHandler = builder.postParamsHandler;
        this.isHasBody = builder.isHasBody;
        httpUrl = HttpUrl.get(baseUrl);
        httpUrl = httpUrl.newBuilder().addPathSegment(relativePath).build();
    }

    public Call invoke(Object[] args) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Request.Builder builder = new Request.Builder();
        if (isHasBody) {
            formBody = new FormBody.Builder();
            for (int i = 0; i < postParamsHandler.length; i++) {
                String key = postParamsHandler[i].getKey();
                String value = (String) args[i];
                formBody.add(key, value);
            }
            builder.post(formBody.build());
        } else {
            for (int i = 0; i < getParamsHandler.length; i++) {
                String key = getParamsHandler[i].getKey();
                String value = (String) args[i];
                httpUrl = httpUrl.newBuilder().addQueryParameter(key, value).build();
            }
            builder.get();
        }
        builder.url(httpUrl);
        Call call = okHttpClient.newCall(builder.build());
        return call;
    }

    public static class Builder {
        private String baseUrl;
        private String relativePath;
        private GetParamsHandler[] getParamsHandler;
        private PostParamsHandler[] postParamsHandler;
        private boolean isHasBody;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setRelativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public Builder setGetParamsHandler(GetParamsHandler[] getParamsHandler) {
            this.getParamsHandler = getParamsHandler;
            return this;
        }

        public Builder setPostParamsHandler(PostParamsHandler[] postParamsHandler) {
            this.postParamsHandler = postParamsHandler;
            return this;
        }

        public Builder setHasBody(boolean hasBody) {
            isHasBody = hasBody;
            return this;
        }

        public MethodService build() {
            return new MethodService(this);
        }
    }
}
