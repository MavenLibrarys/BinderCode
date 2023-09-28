package com.yxf.bindercode.hicar.api.retrofit;

public class PostParamsHandler {
    private String key;

    public PostParamsHandler(Builder builder) {
        this.key = builder.key;
    }

    public String getKey() {
        return key;
    }

    public void addParams(){

    }

    public static class Builder {
        private String key;

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public PostParamsHandler build() {
            return new PostParamsHandler(this);
        }
    }
}
