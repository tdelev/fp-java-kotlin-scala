package com.sorsix.fpjava.wordcount;

public class Part implements WordCount {
    private final String left;
    private final int words;
    private final String right;

    public Part(String left, int words, String right) {
        this.left = left;
        this.words = words;
        this.right = right;
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

}
