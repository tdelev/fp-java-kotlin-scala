package com.sorsix.fpkotlin.wordcount

import kotlin.math.min

sealed class WordCount

data class WordSegment(val chars: String) : WordCount()
data class Separator(val left: String, val words: Int, val right: String) : WordCount()

interface Combiner<A> {
    fun combine(a: A, b: A): A

    fun unit(): A
}

val wcCombiner: Combiner<WordCount> = object : Combiner<WordCount> {
    override fun combine(a: WordCount, b: WordCount): WordCount {
        return when (a) {
            is WordSegment -> when (b) {
                is WordSegment -> WordSegment(a.chars + b.chars)
                is Separator -> Separator(a.chars + b.left, b.words, b.right)
            }
            is Separator -> when (b) {
                is WordSegment -> Separator(a.left, a.words, a.right + b.chars)
                is Separator -> Separator(a.left, a.words + b.words + if ((a.right + b.left).isNotEmpty()) 1 else 0, b.right)
            }
        }
    }

    override fun unit() = WordSegment("")
}

fun count(text: String): Int {

    fun wc(c: Char): WordCount =
            if (c.isWhitespace() || c.isDigit() || c == '\n' || c == '\r') Separator("", 0, "")
            else WordSegment(c.toString())

    fun unstub(s: String) = min(s.length, 1)

    /*val result = text.chars()
            .mapToObj { it.toChar() }
            .map(::wc)
            .reduce(wcCombiner.unit(), wcCombiner::combine)*/

    fun split(str: String) =
            Pair(str.substring(0, str.length / 2), str.substring(str.length / 2, str.length))

    fun countRecursive(left: String, right: String): WordCount {
        return if (left.isEmpty() && right.isEmpty()) {
            wcCombiner.unit()
        } else if (left.isEmpty() && right.length == 1) {
            wc(right[0])
        } else if (right.isEmpty() && left.length == 1) {
            wc(left[0])
        } else {
            val leftParts = split(left)
            val rightParts = split(right)
            val leftWc = countRecursive(leftParts.first, leftParts.second)
            val rightWc = countRecursive(rightParts.first, rightParts.second)
            wcCombiner.combine(leftWc, rightWc)
        }
    }

    val parts = split(text)
    val result = countRecursive(parts.first, parts.second)

    return when (result) {
        is WordSegment -> unstub(result.chars)
        is Separator -> unstub(result.left) + result.words + unstub(result.right)
    }

}


fun main(args: Array<String>) {
    val sentence = "some random text with few words a b c"
    val sen2 = "There's never enough time to design the right solution, but somehow always an infinite amount of time for supporting the wrong solution."
    val words = count(sentence)
    println(words)
    println(count(sen2))
}