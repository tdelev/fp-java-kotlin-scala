package com.sorsix.fpjava;

import java.util.function.Function;

public class None<A> implements Option<A> {

    public None() {
    }

    /*@Override
    public <B> Option<B> map(Function<A, B> f) {
        final None<B> none = (None<B>) None.INSTANCE;
        return none;
    }*/

    @Override
    public <B> Option<B> flatMap(Function<A, Option<B>> f) {
        final None<B> none = (None<B>) None.INSTANCE;
        return none;
    }

    public static final None<Object> INSTANCE = new None<>();

    @Override
    public String toString() {
        return "None";
    }
}