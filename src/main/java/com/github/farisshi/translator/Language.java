package com.github.farisshi.translator;

import java.util.Arrays;

public enum Language {
    Unknown("unknown"),

    Auto("auto"),

    Afrikaans("af"),

    Albanian("sq"),

    Amharic("am"),

    Arabic("ar"),

    Armenian("hy"),

    Azerbaijani("az"),

    Basque("eu"),

    Belarusian("be"),

    Bengali("bn"),

    Bosnian("bs"),

    Bulgarian("bg"),

    Catalan("ca"),

    Cebuano("ceb"),

    Chichewa("ny"),

    Chinese_Simplified("zh-CN"),

    Chinese_Traditional("zh-TW"),

    Corsican("co"),

    Croatian("hr"),

    Czech("cs"),

    Danish("da"),

    Dutch("nl"),

    English("en"),

    Esperanto("eo"),

    Estonian("et"),

    Filipino("tl"),

    Finnish("fi"),

    French("fr"),

    Frisian("fy"),

    Galician("gl"),

    Georgian("ka"),

    German("de"),

    Greek("el"),

    Gujarati("gu"),

    Haitian_Creole("ht"),

    Hausa("ha"),

    Hawaiian("haw"),

    Hebrew("iw"),

    Hindi("hi"),

    Hmong("hmn"),

    Hungarian("hu"),

    Icelandic("is"),

    Igbo("ig"),

    Indonesian("id"),

    Irish("ga"),

    Italian("it"),

    Japanese("ja"),

    Javanese("jw"),

    Kannada("kn"),

    Kazakh("kk"),

    Khmer("km"),

    Kinyarwanda("rw"),

    Korean("ko"),

    Kurdish_Kurmanji("ku"),

    Kyrgyz("ky"),

    Lao("lo"),

    Latin("la"),

    Latvian("lv"),

    Lithuanian("lt"),

    Luxembourgish("lb"),

    Macedonian("mk"),

    Malagasy("mg"),

    Malay("ms"),

    Malayalam("ml"),

    Maltese("mt"),

    Maori("mi"),

    Marathi("mr"),

    Mongolian("mn"),

    Myanmar_Burmese("my"),

    Nepali("ne"),

    Norwegian("no"),

    Odia_Oriya("or"),

    Pashto("ps"),

    Persian("fa"),

    Polish("pl"),

    Portuguese("pt"),

    Punjabi("pa"),

    Romanian("ro"),

    Russian("ru"),

    Samoan("sm"),

    Scots_Gaelic("gd"),

    Serbian("sr"),

    Sesotho("st"),

    Shona("sn"),

    Sindhi("sd"),

    Sinhala("si"),

    Slovak("sk"),

    Slovenian("sl"),

    Somali("so"),

    Spanish("es"),

    Sundanese("su"),

    Swahili("sw"),

    Swedish("sv"),

    Tajik("tg"),

    Tamil("ta"),

    Tatar("tt"),

    Telugu("te"),

    Thai("th"),

    Turkish("tr"),

    Turkmen("tk"),

    Ukrainian("uk"),

    Urdu("ur"),

    Uyghur("ug"),

    Uzbek("uz"),

    Vietnamese("vi"),

    Welsh("cy"),

    Xhosa("xh"),

    Yiddish("yi"),

    Yoruba("yo"),

    Zulu("zu");

    private String name;

    private Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Language getInstance(String name) {
        return Arrays.stream(Language.values()).filter(language -> {
            return language.getName().equals(name.toLowerCase());
        }).findAny().get();
    }
}
