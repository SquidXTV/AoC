package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class Day01 extends Day {

    enum Number {

        ZERO('0'),
        ONE('1'),
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9');

        private final String name;
        private final char character;

        Number(char character) {
            this.name = name().toLowerCase();
            this.character = character;
        }

    }

    public Day01() throws URISyntaxException, IOException {
        super(1, 2023);

    }

    @Override
    public void execute() {
        // Part 1
        int sum1 = input.stream()
                .mapToInt(this::part1).sum();
        System.out.println("Part 1: " + sum1);

        // Part 2
        int sum2 = input.stream()
                .mapToInt(this::part2).sum();
        System.out.println("Part 2: " + sum2);
    }

    private int part1(String text) {
        char first = 0;
        char last = 0;
        char[] chars = text.toCharArray();
        for (char current : chars) {
            if (Character.isDigit(current)) {
                if (first == 0) {
                    first = current;
                }
                last = current;
            }
        }
        return Integer.parseInt("" + first + last);
    }

    private int part2(String text) {
        char first = 0;
        char last = 0;
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (Character.isDigit(current)) {
                if (first == 0) {
                    first = current;
                }
                last = current;
            } else {
                Optional<Number> number = startsWithNumber(text.substring(i));
                if (number.isEmpty()) {
                    continue;
                }
                last = number.get().character;
                if (first == 0) {
                    first = last;
                }
            }

        }
        return Integer.parseInt("" + first + last);
    }

    private static Optional<Number> startsWithNumber(String text) {
        for (Number number : Number.values()) {
            if (text.startsWith(number.name)) {
                return Optional.of(number);
            }
        }
        return Optional.empty();
    }

}
