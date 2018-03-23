package com.sorsix.fpjava.wordcount;

public interface Monoid<A> {
    A combine(A left, A right);

    A unit();
}
