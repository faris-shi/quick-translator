package com.github.farisshi.translator.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.farisshi.translator.*;
import com.github.farisshi.translator.model.Definition;
import com.github.farisshi.translator.model.Pair;
import com.github.farisshi.translator.model.Synonym;
import com.github.farisshi.translator.model.TranslationResult;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.farisshi.translator.util.TTKUtils.getTK;

class GoogleTranslator extends AbstractTranslator {


    public GoogleTranslator(TranslationConfig config) {
        super(config);
    }

    @Override
    public TranslationResult translate(String word, Language sourceLanguage, Language destLanguage) throws IOException {
        JSONArray json = JSON.parseArray(getAsString(buildTranslationUrl(word, sourceLanguage, destLanguage)));

        TranslationResult result = new TranslationResult();
        result.setPossibleMistake(extractPossibleMistake(json));
        result.setTranslation(extractTranslation(json));
        result.setSynonyms(extractSynonyms(json));
        result.setOriginalLanguage(extractLanguage(json));
        result.setDefinition(extractDefinition(json));

        return result;
    }

    @Override
    public List<String> suggest(String word) throws IOException {
        String result = getAsString(buildSuggestionUrl(word));
        JSONArray array = JSON.parseArray(result.substring(result.indexOf("(") + 1, result.indexOf(")"))).getJSONArray(1);
        List<String> list = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            list.add(array.getJSONArray(i).getString(0));
        }
        return list;
    }

    private Language extractLanguage(JSONArray json) {
        if (json.size() < 3) {
            return Language.Unknown;
        }
        return Language.getInstance(json.getString(2));
    }

    private String extractPossibleMistake(JSONArray json) {
        if (json.size() < 8 || json.getJSONArray(7) == null) {
            return null;
        }
        JSONArray sub = json.getJSONArray(7);
        if (sub.size() < 2) {
            return null;
        }
        return sub.getString(1);
    }

    private String extractTranslation(JSONArray json) {
        if (json.size() < 1 || json.getJSONArray(0) == null) {
            return null;
        }
        JSONArray sub = json.getJSONArray(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sub.size() - 1; i++) {
            sb.append(sub.getJSONArray(i).getString(0));
        }
        return sb.toString();
    }

    private List<Definition> extractDefinition(JSONArray json) {
        if (json.size() < 13 || json.getJSONArray(12) == null) {
            return Collections.emptyList();
        }
        JSONArray sub = json.getJSONArray(12);
        List<Definition> list = new ArrayList<>();

        for (int i = 0; i < sub.size(); i++) {
            JSONArray item = sub.getJSONArray(i);
            String type = item.getString(0);

            JSONArray array = item.getJSONArray(1);
            for (int j = 0; j < array.size(); j++) {
                Definition definition = new Definition();
                definition.setType(type);
                definition.setDefinition(array.getJSONArray(j).getString(0));

                List<Pair> example = new ArrayList<>();
                example.add(new Pair(array.getJSONArray(j).getString(2), ""));
                definition.setExamples(example);
                list.add(definition);
            }
        }
        return list;
    }

    private Map<String, List<Synonym>> extractSynonyms(JSONArray json) {
        if (json.size() < 2 || json.getJSONArray(1) == null) {
            return null;
        }
        Map<String, List<Synonym>> map = new HashMap<>();
        JSONArray array = json.getJSONArray(1);
        for (int i = 0; i < array.size(); i++) {
            JSONArray sub = array.getJSONArray(i);
            String wordType = sub.getString(0);

            JSONArray synonymJSON = sub.getJSONArray(2);
            int length = synonymJSON.size();
            List<Synonym> list = new ArrayList<>(length);
            for (int j = 0; j < length; j++) {
                JSONArray item = synonymJSON.getJSONArray(j);

                Synonym synonym = new Synonym();
                synonym.setType(wordType);
                synonym.setTranslation(item.getString(0));
                synonym.setSynonyms(item.getJSONArray(1).toJavaList(String.class));

                if(item.size() >= 4) {
                    double frequency = item.getDouble(3);
                    if (frequency > 0.1) {
                        synonym.setFrequency(Synonym.Frequency.COMMON);
                    } else if (frequency > 0.001) {
                        synonym.setFrequency(Synonym.Frequency.UNCOMMON);
                    } else {
                        synonym.setFrequency(Synonym.Frequency.RARE);
                    }
                }
                if(synonym.getFrequency() != null && !synonym.getFrequency().equals(Synonym.Frequency.RARE)) {
                    list.add(synonym);
                }
            }
            map.put(wordType, list);
        }
        return map;
    }

    private String buildTranslationUrl(String word, Language sourceLanguage, Language destLanguage) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.GOOGLE_TRANSLATION_URL);
        sb.append("&tl=").append(destLanguage.getName());
        sb.append("&sl=").append(sourceLanguage.getName());
        sb.append("&hl=").append(Language.English.getName());
        sb.append("&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t");
        sb.append("&ie=UTF-8&oe=UTF-8");
        sb.append("&otf=1&ssel=0&tsel=0");
        sb.append("&tk=").append(getTK(word));
        sb.append("&q=").append(URLEncoder.encode(word, "UTF-8"));
        return sb.toString();
    }

    private String buildSuggestionUrl(String word) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.GOOGLE_SUGGESTION_URL);
        sb.append("&ds=").append("translate");
        sb.append("&hl=").append(Language.English.getName());
        sb.append("&requiredfields=").append(URLEncoder.encode("tl:" + Language.Chinese_Simplified.getName(), "UTF-8"));
        sb.append("&q=").append(URLEncoder.encode(word, "UTF-8"));
        return sb.toString();
    }
}
