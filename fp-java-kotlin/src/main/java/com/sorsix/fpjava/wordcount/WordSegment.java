package com.sorsix.fpjava.wordcount;

public class WordSegment implements WordCount {
    private final String chars;

    public WordSegment(String chars) {
        this.chars = chars;
    }

    public String getChars() {
        return chars;
    }

    public WordCount combine(WordCount other) {
        if (other instanceof WordSegment) {
            return this.combineWordSegment((WordSegment) other);
        } else {
            return this.combineSeparator((Separator) other);
        }
    }

    @Override
    public int count() {
        return WordCount.unstub(chars);
    }

    public WordCount combineWordSegment(WordSegment wordSegment) {
        return new WordSegment(chars.concat(wordSegment.chars));
    }

    public WordCount combineSeparator(Separator separator) {
        return new Separator(chars.concat(separator.getLeft()), separator.getWords(), separator.getRight());
    }

    @Override
    public String toString() {
        return chars;
    }
}
