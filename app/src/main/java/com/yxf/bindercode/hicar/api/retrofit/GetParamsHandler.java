package com.yxf.bindercode.hicar.api.retrofit;

public class GetParamsHandler {
    private String key;

    public GetParamsHandler(Builder builder) {
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

        public GetParamsHandler build() {
            return new GetParamsHandler(this);
        }
    }
}
