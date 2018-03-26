package com.sorsix.fpkotlin

/**
 * Functions are a thing
 */

fun sum(a: Int, b: Int) = a + b

val sumF = ::sum

fun mulFun(a: Int, b: Int, f: (Int, Int) -> Int): (Int) -> Int = { x: Int -> x * f(a, b) }

/**
 * Partial applications
 */

fun sum5(a: Int): Int {
    return sum(5, a)
}

val sum5Partial = { a: Int -> sumF(5, a) }

fun <A, B, C> partial(a: A, f: (A, B) -> C): (B) -> C = { b -> f(a, b) }

val sum10Partial = partial(10, sumF)

/**
 * Curring
 */

val sumA = { a: Int -> { b: Int -> sumF(a, b) } }

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
        { a -> { b -> f(a, b) } }

fun <A, B, C> ((A, B) -> C).curried(): (A) -> (B) -> C =
        curry(this)

val sumCurried = curry(sumF)

/**
 * Composition
 */
fun result(a: Int) = "Result is: $a"

fun resultSum(a: Int, b: Int): String {
    return result(sum(a, b))
}

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { a -> f(g(a)) }
}

val square = compose(::result, { a: Int -> a * a })

/**
 * Iteration
 */

tailrec fun iterate(from: Int, to: Int, action: (Int) -> Unit) {
    if (from < to) {
        action(from)
        iterate(from + 1, to, action)
    }
}

fun main(args: Array<String>) {
    println(sum(2, 3))

    println(sumF(5, 10))

    println(sumA(10)(20))

    println(sumCurried(10)(20))

    println(sumF.curried()(10)(30))

    println(square(20))

    iterate(0, 15000, { println(it) })
}