package com.github.farisshi.translator.impl;

import com.github.farisshi.translator.AbstractTranslator;
import com.github.farisshi.translator.Constant;
import com.github.farisshi.translator.Language;
import com.github.farisshi.translator.TranslationConfig;
import com.github.farisshi.translator.exception.LanguageNotSupportException;
import com.github.farisshi.translator.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;


class YouDaoTranslator extends AbstractTranslator {

    private static final Map<String, String> HEADERS = new HashMap<>();

    private static final String PHRASE_ELEMENT_ID = "wordGroup";

    private static final String RELATED_WORDS_ELEMENT_ID = "relWordTab";

    private static final List<Language> SUPPORTED_LANGUAGES = new ArrayList<>(Arrays.asList(Language.Chinese_Simplified, Language.English, Language.Auto));

    static {
        HEADERS.put("Connection", "keep-alive");
        HEADERS.put("Sec-Fetch-Site", "cross-site");
        HEADERS.put("Sec-Fetch-Mode", "cors");
        HEADERS.put("Sec-Fetch-Dest", "empty");
        HEADERS.put("Origin", "http://dict.youdao.com");
        HEADERS.put("Referer", "http://dict.youdao.com/?keyfrom=dict2.top");
    }

    public YouDaoTranslator(TranslationConfig config) {
        super(config);
    }

    @Override
    public TranslationResult translate(String word, Language sourceLanguage, Language destLanguage) throws IOException, LanguageNotSupportException {
        if (!SUPPORTED_LANGUAGES.contains(sourceLanguage) || !SUPPORTED_LANGUAGES.contains(destLanguage) || destLanguage.equals(Language.Auto)) {
            throw new LanguageNotSupportException();
        }
        TranslationResult result = new TranslationResult();
        String url = String.format(Constant.YOU_DAO_TRANSLATION_URL_FORMAT, word.toLowerCase());
        Document doc = Jsoup.parse(getAsString(url));

        result.setWord(word);
        result.setOriginalLanguage(Language.Unknown);
        result.setDefinition(extractDefinition(doc));
        result.setPronounce(extractPronounce(doc, word));
        result.setPhrases(extractOthers(doc, PHRASE_ELEMENT_ID));
        result.setRelatedWords(extractOthers(doc, RELATED_WORDS_ELEMENT_ID));
        return result;
    }

    @Override
    public Map<String, String> extraHeaders() {
        return HEADERS;
    }

    private List<Pronounce> extractPronounce(Document document, String word) {
        Elements proElements = document.select(".pronounce");
        return proElements.stream().map(element -> {
            String type = element.textNodes().get(0).text().trim();
            String phonetic = element.select(".phonetic").text().trim();
            return new Pronounce(type, phonetic, word);
        }).collect(toList());
    }

    private List<Definition> extractDefinition(Document document) {
        Elements eDefinitions = document.select(".collinsToggle > .wt-container > ul > li");
        List<Definition> definitions = new ArrayList<>(eDefinitions.size());

        for (Element element : eDefinitions) {
            Definition definition = new Definition();
            Elements p = element.select(".collinsMajorTrans > p");

            definition.setType(p.select(".additional").text().trim());
            definition.setDefinition(p.text().replace(definition.getType(), "").trim());
            List<Pair> examples = new ArrayList<>();
            for (Element sub : element.select(".exampleLists > .examples")) {
                Elements subP = sub.select("p");
                Pair pair = new Pair();
                pair.setKey(subP.get(0).text());
                pair.setValue(subP.get(1).text());
                examples.add(pair);
            }
            definition.setExamples(examples);
            definitions.add(definition);
        }
        return definitions;
    }

    private List<Pair> extractOthers(Document document, String elementId) {
        Elements p = document.select("#" + elementId + " > p.wordGroup");
        return p.stream().map(element -> {
            Pair pair = new Pair();
            pair.setKey(element.select("a.search-js").text().trim());
            pair.setValue(element.text().replace(pair.getKey(), "").trim());
            return pair;
        }).collect(toList());
    }
}
