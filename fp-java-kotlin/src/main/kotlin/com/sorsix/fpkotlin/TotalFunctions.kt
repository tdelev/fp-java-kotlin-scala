package com.sorsix.fpkotlin

fun div(a: Int, b: Int) = a * 1.0 / b

fun divSafe(a: Int, b: Int) =
        if (b != 0) Some(a * 1.0 / b)
        else None

fun tax(salary: Double, ratio: Double) = if (salary > 0) salary * ratio
else throw RuntimeException("Give the man some salary")

fun taxSafe(salary: Double, ratio: Double) =
        if (salary > 0) Some(salary * ratio)
        else None


fun main(args: Array<String>) {
    val budget = -5000
    val people = 2
    val taxRatio = 0.15

    //val taxPerPerson = tax(div(budget, people), taxRatio)

    //println(taxPerPerson)

    var salaryPerPerson = 0.0
    var resultTax = 0.0
    try {
        salaryPerPerson = div(budget, people)
        resultTax = tax(salaryPerPerson, taxRatio)
        println(taxSafe(salaryPerPerson, taxRatio))
    } catch (e: RuntimeException) {
        println("Exception: $e")
    }
    println(resultTax)

    val taxPerPersonSafe = divSafe(budget, people).flatMap { taxSafe(it, taxRatio) }
    println(taxPerPersonSafe)
}