package br.edu.ifto.pweb.ecommerce.utils;

public class PercentUtil {
    public static Double percentOf(Double percent, Double amount) {
        return amount * percent;
    }

    public static Double increase(Double percent, Double amount) {
        return amount + (amount * (1 + percent));
    }

    public static Double decrease(Double percent, Double amount) {
        return amount - (amount * (1 + percent));
    }
}
