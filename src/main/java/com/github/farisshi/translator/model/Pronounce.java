package com.github.farisshi.translator.model;

import com.github.farisshi.translator.Constant;
import lombok.Data;

@Data
public class Pronounce {

    private PronounceType type;

    private String phonetic;

    private String voiceUrl;


    public Pronounce(String type, String phonetic, String word){
        this(PronounceType.getInstance(type), phonetic, word);
    }

    public Pronounce(PronounceType type, String phonetic, String word){
        this.type = type;
        this.phonetic= phonetic;
        voiceUrl = String.format(Constant.YOUDAO_VOICE_URL_FORMAT, word, type.getId());
    }

    public static enum PronounceType{
        NON("", ""), UK("1", "英"), USA("2", "美");

        private String id;

        private String type;

        private PronounceType(String id, String type){
            this.id = id;
            this.type = type;
        }

        public String getId(){
            return id;
        }

        public String getType(){
            return type;
        }

        public static PronounceType getInstance(String type){
            for(PronounceType p: PronounceType.values()){
                if(p.getType().equals(type)){
                    return p;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
