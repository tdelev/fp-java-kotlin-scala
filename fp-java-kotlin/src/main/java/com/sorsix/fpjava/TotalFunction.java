package com.sorsix.fpjava;

public class TotalFunction {
    public static Option<Double> saveDiv(double num, double n) {
        if (n == 0) return Option.none();
        else return Option.unit(num / n);
    }

    public static Option<Double> tax(double salary, double taxRatio) {
        if (salary > 0) {
            return Option.unit(salary * taxRatio);
        } else return Option.none();
    }

    public static void main(String[] args) {
        Option<Double> result = saveDiv(5000, 5);
        System.out.println(result);
        Option<Double> tax = result.flatMap(salary -> tax(salary, 0.15));
        System.out.println(tax);
        result = saveDiv(1500, 0);
        System.out.println(result);
        tax = result.flatMap(salary -> tax(salary, 0.15));
        System.out.println(tax);
    }
}
