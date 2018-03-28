package com.sorsix.fpkotlin

data class User(val name: String, val age: Int)


val users = mapOf(
        "john" to User("John", 20),
        "peter" to User("Peter", 29),
        "ann" to User("Ann", 33)
)

fun findUserOptional(id: String) = ofNullable(users[id])

fun findUser(id: String) = users[id]

fun bonus(age: Int) = if(age > 0) age * 100 else 0