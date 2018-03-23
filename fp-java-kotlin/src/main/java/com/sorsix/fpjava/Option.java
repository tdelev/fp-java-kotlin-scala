package com.sorsix.fpjava;

import java.util.function.Function;

public interface Option<A> {

    default <B> Option<B> map(Function<A, B> f) {
        return this.flatMap(a -> unit(f.apply(a)));
    }

    <B> Option<B> flatMap(Function<A, Option<B>> f);

    static <A> Option<A> unit(A value) {
        return new Some<>(value);
    }

    static <A> Option<A> none() {
        final None<A> none = (None<A>) None.INSTANCE;
        return none;
    }
}


