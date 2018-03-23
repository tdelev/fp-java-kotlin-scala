package com.sorsix.fpjava.wordcount;

public class Stub implements WordCount {
    private final String chars;

    public Stub(String chars) {
        this.chars = chars;
    }

    public String getChars() {
        return chars;
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
        return WordCount.unstub(chars);
    }

    public WordCount combineStab(Stub stub) {
        return new Stub(chars.concat(stub.chars));
    }

    public WordCount combinePart(Part part) {
        return new Part(chars.concat(part.getLeft()), part.getWords(), part.getRight());
    }

    @Override
    public String toString() {
        return chars;
    }
}
