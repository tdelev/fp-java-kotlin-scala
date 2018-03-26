package com.sorsix.fpscala

import scala.annotation.tailrec

object Functions {
  /**
    * Functions are things
    */
  def sum(a: Int, b: Int) = a + b

  val sumF: (Int, Int) => Int = sum

  def mulFun(a: Int, b: Int, f: (Int, Int) => Int): Int => Int =
    x => x * f(a, b)

  /**
    * Partial applications
    */

  def sum5(a: Int): Int = {
    sum(5, a)
  }

  val sum5Partial: (Int) => Int = a => sumF(5, a)

  def partial[A, B, C](a: A, f: (A, B) => C): (B) => C = b => f(a, b)

  val sum10Partial = partial(10, sumF)

  /**
    * Curring
    */

  val sumA = (a: Int) => (b: Int) => sumF(a, b)

  def curry[A, B, C](f: (A, B) => C): (A) => (B) => C = a => b => f(a, b)

  val sumCurried = curry(sumF)

  /**
    * Composition
    */

  def result(a: Int) = s"Result is: $a"

  def resultSum(a: Int, b: Int): String = {
    result(sum(a, b))
  }

  def compose[A, B, C](f: (B) => C, g: (A) => B): (A) => C = {
    a => f(g(a))
  }

  val square = compose(result, (a: Int) => a * a)


  /**
    * Iteration
    */

  @tailrec
  def iterate(from: Int, to: Int, action: (Int) => Unit) {
    if (from < to) {
      action(from)
      iterate(from + 1, to, action)
    }
  }

  def main(args: Array[String]): Unit = {
    println(sum(5, 10))
    println(sumF(12, 20))
  }
}

