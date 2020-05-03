package com.github.farisshi.translator.model;

import lombok.Data;

import java.util.List;

@Data
public class Definition {

    private String type;

    private String definition;

    private List<Pair> examples;
}
