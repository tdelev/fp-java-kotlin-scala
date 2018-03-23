package com.sorsix.fpjava;

import java.util.function.Function;

public class Some<A> implements Option<A> {
    private final A value;

    public Some(A value) {
        this.value = value;
    }

    public A getValue() {
        return value;
    }

    /*@Override
    public <B> Option<B> map(Function<A, B> f) {
        return new Some<>(f.apply(value));
    }*/

    @Override
    public <B> Option<B> flatMap(Function<A, Option<B>> f) {
        return f.apply(value);
    }

    @Override
    public String toString() {
        return String.format("Some(%s)", value);
    }
}