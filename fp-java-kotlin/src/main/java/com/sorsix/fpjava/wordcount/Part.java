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

    public WordCount combine(WordCount other) {
        if (other instanceof Stub) {
            return this.combineStab((Stub) other);
        } else {
            return this.combinePart((Part) other);
        }
    }

    @Override
    public int count() {
        return words + (left.isEmpty() ? 0 : 1) + (right.isEmpty() ? 0 : 1);
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

    public WordCount combineStab(Stub stub) {
        return new Part(left, words, right.concat(stub.getChars()));
    }

    public WordCount combinePart(Part part) {
        return new Part(left, words + part.words + (right.concat(part.left).isEmpty() ? 0 : 1), part.right);
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %s", left, words, right);
    }
}
