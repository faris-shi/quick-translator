package com.github.farisshi.translator.model;

import com.github.farisshi.translator.Language;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TranslationResult {

    private String word;

    private Language originalLanguage;

    private String translation;

    private String possibleMistake;

    private Map<String, List<Synonym>> synonyms = new HashMap<>();

    private List<Pronounce> pronounce = new ArrayList<>();

    private List<Definition> definition = new ArrayList<>();

    private List<Pair> relatedWords = new ArrayList<>();

    private List<Pair> phrases = new ArrayList<>();

    public boolean hasMistake(){
        return possibleMistake != null && possibleMistake.length() != 0;
    }
}
