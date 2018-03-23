package com.sorsix.fpjava.wordcount;

public class WordCountCombiner implements Combiner<WordCount> {
    @Override
    public WordCount combine(WordCount left, WordCount right) {
        return left.combine(right);
    }

    @Override
    public WordCount unit() {
        return new Stub("");
    }
}
