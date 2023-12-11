package me.squidxtv.util;

import java.util.Arrays;

public final class Algorithms {

    private Algorithms() {
        throw new UnsupportedOperationException("Static helper class.");
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long gcd(long[] input) {
        return Arrays.stream(input)
                .reduce(Algorithms::gcd)
                .orElse(0);
    }

    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long lcm(long[] input) {
        return Arrays.stream(input)
                .reduce(Algorithms::lcm).orElse(0);
    }

}
