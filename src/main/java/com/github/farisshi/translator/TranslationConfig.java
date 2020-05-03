package com.github.farisshi.translator;

import lombok.Data;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

@Data
public class TranslationConfig {

    private int timeOut;

    private Map<String, String> headers = new HashMap<>();

    private Proxy proxy;

    private Translator.Builder.TranslatorType type = Translator.Builder.TranslatorType.MIXED;
}
