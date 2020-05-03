package com.github.farisshi.translator.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTKUtils {

    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    private static String tkk;

    private static Date tkkDate;

    private static final Object LOCK = new Object();

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    static{
        Reader scriptReader = null;
        try {
            scriptReader = new InputStreamReader(TTKUtils.class.getResourceAsStream("/ttk.js"), StandardCharsets.UTF_8);
            engine.eval(scriptReader);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(scriptReader != null){
                try {
                    scriptReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final Pattern PATTERN = Pattern.compile("tkk:.*?',");


    public static String download(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static boolean isTkkAvailable(){
        return tkk != null && (new Date().getTime() - tkkDate.getTime() <= 5 * 60 * 1000);
    }

    public static String getTKK() throws IOException {
        if(isTkkAvailable()){
            return tkk;
        }
        synchronized(LOCK){
            if (isTkkAvailable()){
                return tkk;
            }
            String result = download("https://translate.google.cn/");
            if (result.contains("tkk")) {
                Matcher matcher = PATTERN.matcher(result);
                if(!matcher.find()){
                    return null;
                }
                String sub = matcher.group(0);
                tkk = sub.substring(5, sub.length() - 2);
                tkkDate = new Date();
            }
        }
        return tkk;
    }

    public static String getTK(String word) {
        String result = null;
        try {
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                result = (String) invocable.invokeFunction("tk", new Object[]{word, getTKK()});
            }
        } catch (Exception e) {
            throw new RuntimeException("获取 tk 出错");
        }

        return result;
    }
}
