package com.github.farisshi.translator;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTranslator implements Translator {

    protected TranslationConfig config;

    private OkHttpClient client;

    public AbstractTranslator(TranslationConfig config) {
        this.config = config;
    }

    protected String getAsString(String url) throws IOException {
        buildHttpClient();
        Request request = new Request.Builder().url(url).headers(mergeHeaders()).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    protected byte[] getAsBytes(String url) throws IOException {
        buildHttpClient();
        Request request = new Request.Builder().url(url).headers(mergeHeaders()).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().bytes();
        }
    }

    protected Map<String, String> extraHeaders() {
        return null;
    }

    private final Object LOCK = new Object();

    private void buildHttpClient() {
        if(client != null){
            return;
        }
        synchronized (LOCK) {
            if(client != null){
                return;
            }
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (config.getTimeOut() > 0) {
                builder.connectTimeout(config.getTimeOut(), TimeUnit.SECONDS)
                        .readTimeout(config.getTimeOut(), TimeUnit.SECONDS)
                        .writeTimeout(config.getTimeOut(), TimeUnit.SECONDS);
            } else if (config.getProxy() != null) {
                builder.proxy(config.getProxy());
            }
            client = builder.build();
        }
    }

    private Headers mergeHeaders() {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry<String, String> entry : config.getHeaders().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        Map<String, String> extraHeader = extraHeaders();
        if(extraHeader != null && extraHeader.size() != 0) {
            for (Map.Entry<String, String> entry : extraHeader.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }
}
