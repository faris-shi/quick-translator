package com.github.farisshi.translator.impl;

import com.github.farisshi.translator.AbstractTranslator;
import com.github.farisshi.translator.Language;
import com.github.farisshi.translator.TranslationConfig;
import com.github.farisshi.translator.Translator;
import com.github.farisshi.translator.exception.LanguageNotSupportException;
import com.github.farisshi.translator.impl.GoogleTranslator;
import com.github.farisshi.translator.impl.YouDaoTranslator;
import com.github.farisshi.translator.model.TranslationResult;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TranslatorFactory extends AbstractTranslator{

    private GoogleTranslator googleTranslator;

    private YouDaoTranslator youDaoTranslator;

    public TranslatorFactory(TranslationConfig config) {
        super(config);
        googleTranslator = new GoogleTranslator(config);
        youDaoTranslator = new YouDaoTranslator(config);
    }

    @Override
    public TranslationResult translate(String word, Language sourceLanguage, Language destLanguage) throws IOException, LanguageNotSupportException {
        switch (config.getType()){
            case GOOGLE:
                return googleTranslator.translate(word, sourceLanguage, destLanguage);
            case YOUDAO:
                return youDaoTranslator.translate(word, sourceLanguage, destLanguage);
            default:
                TranslationResult googleResult = googleTranslator.translate(word, sourceLanguage, destLanguage);
                TranslationResult youdaoResult = youDaoTranslator.translate(word, sourceLanguage, destLanguage);
                mergeResult(googleResult, youdaoResult);
                return youdaoResult;
        }
    }

    @Override
    public List<String> suggest(String word) throws IOException {
        if(word.contains(" ")){
            return Collections.emptyList();
        }
        return googleTranslator.suggest(word);
    }

    private void mergeResult(TranslationResult googleResult, TranslationResult youdaoResult){
        youdaoResult.setOriginalLanguage(googleResult.getOriginalLanguage());
        youdaoResult.setSynonyms(googleResult.getSynonyms());
        youdaoResult.setTranslation(googleResult.getTranslation());
        youdaoResult.setPossibleMistake(googleResult.getPossibleMistake());
    }
}
