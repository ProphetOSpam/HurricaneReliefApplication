package com.one_half_men.app.distance;

public record Ratio(int numerator, int denominator) {
    /** @return The ratio in decimal form (numerator / denominator) */
    public double decimal() {
        return (double) numerator / denominator;
    }
}
