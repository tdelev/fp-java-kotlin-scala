package com.sorsix.fpkotlin

sealed class Option<out T> {

    fun <B : Any> map(f: (T) -> B): Option<B> =
            when (this) {
                is Some -> Some(f(this.value))
                None -> None
            }
    //flatMap { unit(f(it)) }

    fun <B> flatMap(f: (T) -> Option<B>): Option<B> =
            when (this) {
                None -> None
                is Some -> f(this.value)
            }

    override fun toString() = when (this) {
        None -> "None"
        is Some -> "Some($value)"
    }

}

object None : Option<Nothing>()
data class Some<out T>(val value: T) : Option<T>()

fun <T> unit(a: T) = Some(a)

fun <T> ofNullable(a: T?) = if (a == null) None else Some(a)

fun <T> sequence(list: List<Option<T>>): Option<List<T>> =
        if (list.isEmpty()) Some(listOf())
        else {
            val first = list.first()
            first.flatMap { head -> sequence(if (list.size == 1) listOf() else list.subList(1, list.size)).map { listOf(head) + it } }
        }

fun <T> Option<T>.getOrElse(get: () -> T): T =
        when (this) {
            None -> get()
            is Some -> this.value
        }

fun <T> Option<T>.orElse(other: Option<T>): Option<T> =
        when (this) {
            None -> other
            is Some -> this
        }

fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> =
        when (a) {
            is Some ->
                when (b) {
                    is Some -> Some(f(a.value, b.value))
                    else -> None
                }
            else -> None
        }
