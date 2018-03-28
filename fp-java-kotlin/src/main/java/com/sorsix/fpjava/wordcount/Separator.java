package com.sorsix.fpjava.wordcount;

public class Separator implements WordCount {
    private final String left;
    private final int words;
    private final String right;

    public Separator(String left, int words, String right) {
        this.left = left;
        this.words = words;
        this.right = right;
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
        return WordCount.unstub(left) + words + WordCount.unstub(right);
    }

    public String getLeft() {
        return left;
    }

    public int getWords() {
        return words;
    }

    public String getRight() {
        return right;
    }

    public WordCount combineWordSegment(WordSegment wordSegment) {
        return new Separator(left, words, right.concat(wordSegment.getChars()));
    }

    public WordCount combineSeparator(Separator separator) {
        return new Separator(left, words + separator.words + (right.concat(separator.left).isEmpty() ? 0 : 1), separator.right);
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %s", left, words, right);
    }
}
