package com.sorsix.fpkotlin.wordcount

import kotlin.math.min

sealed class WordCount

data class Stub(val chars: String) : WordCount()
data class Part(val left: String, val words: Int, val right: String) : WordCount()

interface Combiner<A> {
    fun combine(a: A, b: A): A

    fun unit(): A
}

val wcCombiner: Combiner<WordCount> = object : Combiner<WordCount> {
    override fun combine(a: WordCount, b: WordCount): WordCount {
        return when (a) {
            is Stub -> when (b) {
                is Stub -> Stub(a.chars + b.chars)
                is Part -> Part(a.chars + b.left, b.words, b.right)
            }
            is Part -> when (b) {
                is Stub -> Part(a.left, a.words, a.right + b.chars)
                is Part -> Part(a.left, a.words + b.words + if ((a.right + b.left).isNotEmpty()) 1 else 0, b.right)
            }
        }
    }

    override fun unit() = Stub("")
}

fun count(text: String): Int {

    fun wc(c: Char): WordCount =
            if (c.isWhitespace() || c.isDigit() || c == '\n' || c == '\r') Part("", 0, "")
            else Stub(c.toString())

    fun unstub(s: String) = min(s.length, 1)

    val result = text.chars()
            .mapToObj { it.toChar() }
            .map(::wc)
            .reduce(wcCombiner.unit(), wcCombiner::combine)


    return when (result) {
        is Stub -> unstub(result.chars)
        is Part -> unstub(result.left) + result.words + unstub(result.right)
    }
}


fun main(args: Array<String>) {
    val words = count("some random text with few words a b c")
    println(words)
}