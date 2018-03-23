package com.sorsix.fpjava.wordcount;

public interface Combiner<A> {
    A combine(A left, A right);

    A unit();
}
