package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 extends Day {

    public Day09() throws URISyntaxException, IOException {
        super(9, 2023);
    }

    @Override
    public void execute() {
        List<NumberHistory> history = input.stream()
                .map(NumberHistory::new)
                .toList();

        int sum = history.stream()
                .mapToInt(NumberHistory::getExtrapolatedValue)
                .sum();
        System.out.println("Part 1: " + sum);

        int sumPrevious = history.stream()
                .mapToInt(NumberHistory::getPreviousExtrapolatedValue)
                .sum();
        System.out.println("Part 2: " + sumPrevious);
    }

    private static class NumberHistory {

        private final int[] numbers;

        public NumberHistory(String line) {
            numbers = Arrays.stream(line.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        public int getExtrapolatedValue() {
            List<int[]> differences = getDifferences();
            int extra = 0;
            for (int[] diff : differences.reversed()) {
                extra = diff[diff.length - 1] + extra;
            }
            return extra;
        }

        public int getPreviousExtrapolatedValue() {
            List<int[]> differences = getDifferences();
            int extra = 0;
            for (int[] diff : differences.reversed()) {
                extra = diff[0] - extra;
            }
            return extra;
        }

        public List<int[]> getDifferences() {
            List<int[]> differences = new ArrayList<>();
            int[] current = numbers;
            while (!Arrays.stream(current).allMatch(value -> value == 0)) {
                differences.add(current);

                int[] difference = new int[current.length - 1];
                for (int i = 0; i < difference.length; i++) {
                    difference[i] = current[i + 1] - current[i];
                }
                current = difference;
            }
            return differences;
        }
    }

}
