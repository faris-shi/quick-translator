package com.github.farisshi.translator.model;

import lombok.Data;

@Data
public class Pair {

    private String key;

    private String value;

    public Pair(){

    }

    public Pair(String key, String value){
        this.key = key;
        this.value = value;
    }
}
