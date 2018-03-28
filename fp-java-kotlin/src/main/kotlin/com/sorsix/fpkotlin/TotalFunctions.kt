package com.sorsix.fpkotlin

operator fun Option<User>.plus(other: Option<User>): Option<User> =
        when (this) {
            is Some -> when (other) {
                is Some -> Some(User("sum", this.value.age + other.value.age))
                else -> this
            }
            else -> when (other) {
                is Some -> other
                else -> None
            }
        }

fun main(args: Array<String>) {
    val ids = listOf("john", "peter", "ann", "nonexisting")

    val totalBonus = ids.map {
        findUserOptional(it)
                .map { bonus(it.age) }.getOrElse { 0 }
    }.reduce { acc, i -> acc + i }

    var total = 0
    ids.forEach {
        val user = findUser(it)
        if (user != null) {
            total += bonus(user.age)
        }
    }

    println(totalBonus)

}