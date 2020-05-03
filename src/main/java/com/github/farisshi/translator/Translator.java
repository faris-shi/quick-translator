package com.github.farisshi.translator;

import com.github.farisshi.translator.exception.LanguageNotSupportException;
import com.github.farisshi.translator.impl.TranslatorFactory;
import com.github.farisshi.translator.model.TranslationResult;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Translator {

    TranslationResult translate(String word, Language sourceLanguage, Language destLanguage) throws IOException, LanguageNotSupportException;

    default TranslationResult translate(String word, Language destLanguage) throws IOException, LanguageNotSupportException {
        return translate(word, Language.Auto, destLanguage);
    }

    default List<String> suggest(String word) throws IOException{
        return Collections.emptyList();
    }

    public static class Builder{

        private TranslationConfig config = new TranslationConfig();

        public Builder proxy(String hostname, int port){
            config.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port)));
            return this;
        }

        public Builder proxy(Proxy proxy){
            config.setProxy(proxy);
            return this;
        }

        public Builder header(String key, String value){
            config.getHeaders().put(key, value);
            return this;
        }

        public Builder header(Map<String, String> header){
            config.getHeaders().putAll(header);
            return this;
        }

        public Builder timeout(int timeout){
            config.setTimeOut(timeout);
            return this;
        }

        public Builder translatorType(TranslatorType type){
            config.setType(type);
            return this;
        }

        public Translator build(){
            return new TranslatorFactory(config);
        }

        public static enum TranslatorType{
            YOUDAO, GOOGLE, MIXED;
        }
    }
}
