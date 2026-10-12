package com.one_half_men.app.location;

public record Ratio(int numerator, int denominator) {
    public double getDecimal() {
        return (double) numerator / denominator;
    }
}
