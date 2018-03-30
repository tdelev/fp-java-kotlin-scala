package com.sorsix.fpjava;

public class TotalFunction {
    static Double div(double num, double d) {
        return num / d;
    }

    static Option<Double> saveDiv(double num, double n) {
        if (n == 0) return Option.none();
        else return Option.unit(num / n);
    }

    static Option<Double> tax(double salary, double taxRatio) {
        if (salary > 0) {
            return Option.unit(salary * taxRatio);
        } else return Option.none();
    }

    public static void main(String[] args) {
        double budget = 5000;
        int people = 5;

        Option<Double> result = saveDiv(budget, people);
        System.out.println(result);
        Option<Double> tax = result.flatMap(salary -> tax(salary, 0.15));
        System.out.println(tax);
        result = saveDiv(1500, 0);
        System.out.println(result);
        tax = result.flatMap(salary -> tax(salary, 0.15));
        System.out.println(tax);
    }
}
