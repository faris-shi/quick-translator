# Quick Translator
Quick Translator is a free and unlimited translation library, it mixed with Google Translation and YOU DAO Translation that implements their API unofficially. Therefore, this library is only for individual and study purposes. If you are for other purposes, please see the official version:

- [Google Translation](https://translate.google.com/)
- [YOU DAO Translation](http://dict.youdao.com/)



## Introduction

YOU DAO Translation is a popular translation tool for English learning beginner in China. it provides some useful functions such as `collins dictionary`, `usual phrases` and `related words`. However, I always need to open two tabs for both `Google Translation` and `YOU DAO Translation` to compare to them and get accurate and exact meanings of words. Meanwhile, it is why I do this job.



## Features

- Fast and Reliable
- Auto language detection
- HTTP/2 support
- Proxy support



## Note on library usage

**DISCLAIMER:** this is an unofficial library using [Google Translation](https://translate.google.com/) and [YOU DAO Translation](http://dict.youdao.com/) WebPage and WebAPI.

- The maximum character limit on a single text is 15k.
- This library can not guarantee it could work properly at all times due to the development of these APIs.
- If you want to use a stable API or be for the business purpose,  I recommend you to use the official API.
- If you get Http errors such 50x, 60x, maybe your IP is banned by Google or YOU DAO due to security policy.



## Usage

**Translator Type**

```java
public static enum TranslatorType{
	YOUDAO, GOOGLE, MIXED;
}
```

MIXED is default TranslatorType, it means you can use the following codes to get a mixed translator that will return a trnslation result coming from both Google and YOU DAO.

```sql
Translator translator = new Translator.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                .timeout(1).build();
```

And also, you can specify the TranslatorType you want.

```sql
Translator translator = new Translator.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0")
                .timeout(1)
                .translatorType(Translator.Builder.TranslatorType.GOOGLE).build();
```

**Basic Usage**

You can just provide destination language, the library can detect the source language in `MIXED` and `GOOGLE` TranslatorType.

```sql
TranslationResult result = translator.translate("word", Language.Chinese_Simplified);
```

Or, give both languages:

```sql
TranslationResult result = translator.translate("word", Language.English, Language.Chinese_Simplified);
```

YOUDAO options will be unavailable when you want to translate words with other languages except `English` and `Chinese_simplified`, Therefore, if you want to use other languages, please specify `GOOGLE` TranslatorType during building translator instance.



The library can provide some suggestions for the pieces of English single word.

```sql
List<String> suggestion = translator.suggest("cri");
```

**Result of Translation**

```javascript
{
    "definition":[
        {
            "definition":"A student is a person who is studying at an elementary school, secondary school, college, or university. 学生",
            "examples":[
                {
                    "key":"Warren's eldest son is an art student.",
                    "value":"沃伦的长子是一名学艺术的学生。"
                }
            ],
            "type":"N-COUNT"
        },
        {
            "definition":"Someone who is a student of a particular subject is interested in the subject and spends time learning about it. 学者",
            "examples":[
                {
                    "key":"...a passionate student of history and an expert on nineteenth century prime ministers.",
                    "value":"…一位热衷历史的学者和一个研究19世纪首相们的专家。"
                }
            ],
            "type":"N-COUNT"
        }
    ],
    "originalLanguage":"English",
    "phrases":[
        {
            "key":"college student",
            "value":"大学生"
        },
        {
            "key":"university student",
            "value":"大学生"
        },
        {
            "key":"graduate student",
            "value":"n. 研究生；毕业生"
        },
        {
            "key":"good student",
            "value":"好学生"
        },
        {
            "key":"foreign student",
            "value":"外国学生，外籍学生；留学生"
        },
        {
            "key":"high school student",
            "value":"高中生"
        },
        {
            "key":"international student",
            "value":"国际学生；留学生"
        },
        {
            "key":"middle school student",
            "value":"中学生"
        },
        {
            "key":"student loan",
            "value":"助学贷款"
        },
        {
            "key":"student visa",
            "value":"学生签证"
        },
        {
            "key":"student life",
            "value":"学生生活；学习生活"
        },
        {
            "key":"doctoral student",
            "value":"博士生"
        },
        {
            "key":"excellent student",
            "value":"优秀学生；优等生"
        },
        {
            "key":"girl student",
            "value":"女学生"
        },
        {
            "key":"student id",
            "value":"学生证；学号"
        },
        {
            "key":"outstanding student",
            "value":"优秀学生，杰出学生"
        },
        {
            "key":"postgraduate student",
            "value":"硕士研究生"
        },
        {
            "key":"poor student",
            "value":"贫困生；学困生"
        },
        {
            "key":"student body",
            "value":"全体学生；学生的总称"
        },
        {
            "key":"undergraduate student",
            "value":"本科生"
        }
    ],
    "pronounce":[
        {
            "phonetic":"[ˈstjuːdnt]",
            "type":"UK"
        },
        {
            "phonetic":"[ˈstuːdnt]",
            "type":"USA"
        }
    ],
    "relatedWords":[
        {
            "key":"student",
            "value":"词根："
        },
        {
            "key":"studentship",
            "value":"学生身分；（英）奖学金"
        }
    ],
    "synonyms":{
        "noun":[
            {
                "frequency":"COMMON",
                "synonyms":[
                    "student",
                    "pupil",
                    "learner",
                    "scholar",
                    "novice"
                ],
                "translation":"学生",
                "type":"noun"
            },
            {
                "frequency":"UNCOMMON",
                "synonyms":[
                    "student",
                    "trainee",
                    "learner",
                    "apprentice",
                    "novice",
                    "scholar"
                ],
                "translation":"学员",
                "type":"noun"
            },
            {
                "frequency":"UNCOMMON",
                "synonyms":[
                    "life",
                    "student",
                    "livelihood"
                ],
                "translation":"生",
                "type":"noun"
            }
        ]
    },
    "translation":"学生",
    "word":"student"
}
```



Language**

```txt
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
```



### License

```txt
MIT License

Copyright (c) 2020 faris-shi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```

