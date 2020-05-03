package com.github.farisshi.translator.model;

import lombok.Data;

import java.util.List;

@Data
public class Synonym {

    private String type;

    private String translation;

    private List<String> synonyms;

    private Frequency frequency;

    public static enum Frequency {
        COMMON, UNCOMMON, RARE;
    }
}
