package com.sorsix.fpjava;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Functions {

    /**
     * Functions are a thing
     */

    static int sum(int a, int b) {
        return a + b;
    }

    static BiFunction<Integer, Integer, Integer> sumF = Functions::sum;

    /**
     * Partial applications
     */

    static int sum5(int a) {
        return sum(5, a);
    }

    static Function<Integer, Integer> sum5Partial = a -> sumF.apply(5, a);

    static <A, B, C> Function<B, C> partial(A a, BiFunction<A, B, C> f) {
        return b -> f.apply(a, b);
    }

    static Function<Integer, Integer> sum10Partial = partial(10, sumF);

    /**
     * Curring
     */

    static Function<Integer, Integer> sumA(int a) {
        return b -> sum(a, b);
    }

    static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> f) {
        return a -> b -> f.apply(a, b);
    }

    static Function<Integer, Function<Integer, Integer>> sumACurried = curry(sumF);

    /**
     * Composition
     */
    static String result(int a) {
        return String.format("Result is: %d", a);
    }

    static String resultSum(int a, int b) {
        return result(sum(a, b));
    }

    static BiFunction<Integer, Integer, String> resultComposed = sumF.andThen(Functions::result);

    static <A, B, C> Function<A, C> compose(Function<B, C> f, Function<A, B> g) {
        return a -> f.apply(g.apply(a));
        // return g.andThen(f);
    }

    static Function<Integer, String> square = compose(Functions::result, a -> a * a);

    public static void main(String[] args) {
        System.out.println(sum(1, 2));

        System.out.println(sumF.apply(5, 10));

        System.out.println(sum5Partial.apply(20));

        System.out.println(sum10Partial.apply(30));

        System.out.println(sumA(10).apply(20));

        System.out.println(sumACurried.apply(10).apply(20));

        System.out.println(resultComposed.apply(15, 20));

        System.out.println(square.apply(10));
    }
}
