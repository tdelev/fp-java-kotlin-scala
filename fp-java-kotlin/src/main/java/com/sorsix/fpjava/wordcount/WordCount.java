package com.sorsix.fpjava.wordcount;

public interface WordCount {
    WordCount combine(WordCount other);

    int count();

    static int unstub(String chars) {
        return chars.isEmpty() ? 0 : 1;
    }
}
