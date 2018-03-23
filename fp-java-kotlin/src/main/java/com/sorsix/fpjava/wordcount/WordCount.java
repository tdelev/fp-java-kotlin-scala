package com.sorsix.fpjava.wordcount;

public interface WordCount {
    WordCount combine(WordCount other);

    int count();
}
