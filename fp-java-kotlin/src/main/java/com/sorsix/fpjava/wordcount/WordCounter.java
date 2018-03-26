package com.sorsix.fpjava.wordcount;

import java.util.function.Function;

public class WordCounter {

    private static final Function<Character, WordCount> mapper = c -> {
        if (c == ' ') return new Part("", 0, "");
        else return new Stub(c.toString());
    };

    public static WordCount count(String sentence) {
        WordCountCombiner combiner = new WordCountCombiner();
        return sentence.chars().mapToObj(c -> (char) c)
                .map(mapper)
                .reduce(combiner.unit(), combiner::combine);
    }

    public static void main(String[] args) {
        String sentence = "this is a very long and complicated sentence of many words";
        WordCount result = count(sentence);
        System.out.println(result);
        System.out.println(result.count());

        result = count("other");
        System.out.println(result);
        System.out.println(result.count());

        result = count("two words");
        System.out.println(result);
        System.out.println(result.count());
    }

}
