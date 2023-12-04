package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03 extends Day {

    public Day03() throws URISyntaxException, IOException {
        super(3, 2023);
    }

    @Override
    public void execute() {
        char[][] chars = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int sum1 = part1(chars);
        System.out.println("Part 1: " + sum1);

        int sum2 = part2(chars);
        System.out.println("Part 2: " + sum2);
    }

    private int part1(char[][] chars) {
        int sum = 0;
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[y].length; x++) {
                if (Character.isDigit(chars[y][x])) {
                    int lengthOfNumber = lengthOfNumber(chars[y], x);
                    String number = new String(Arrays.copyOfRange(chars[y], x, x + lengthOfNumber));

                    if (isSymbolAdjacent(chars, x, y, lengthOfNumber)) {
                        sum += Integer.parseInt(number);
                    }

                    x += lengthOfNumber;
                }
            }
        }
        return sum;
    }

    private static boolean isSymbolAdjacent(char[][] chars, int x, int y, int lengthOfNumber) {
        int startY = Math.max(0, y - 1);
        int endY = Math.min(chars.length - 1, y + 1);
        int startX = Math.max(0, x - 1);
        for (int j = startY; j <= endY; j++) {
            int endX = Math.min(chars[j].length - 1, x + lengthOfNumber);
            for (int i = startX; i <= endX; i++) {
                char current = chars[j][i];
                if (!Character.isDigit(current) && current != '.') {
                    return true;
                }
            }
        }
        return false;
    }

    private static int lengthOfNumber(char[] row, int x) {
        int length = 0;
        while (x < row.length && Character.isDigit(row[x])) {
            length++;
            x++;
        }
        return length;
    }

    private int part2(char[][] chars) {
        int sum = 0;
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[y].length; x++) {
                char current = chars[y][x];
                if (current != '*') {
                    continue;
                }

                sum += getRatio(chars, x, y);
            }
        }
        return sum;
    }

    private static int getRatio(char[][] chars, int x, int y) {
        List<Pair<Integer, Integer>> adjacentNumbers = new ArrayList<>();

        for (int j = Math.max(0, y - 1); j <= Math.min(139, y + 1); j++) {
            boolean skip = false;
            for (int i = Math.max(0, x - 1); i <= Math.min(139, x + 1); i++) {
                if (Character.isDigit(chars[j][i])) {
                    if (skip) {
                        continue;
                    }
                    skip = true;
                    adjacentNumbers.add(new Pair<>(j, i));
                } else {
                    skip = false;
                }
            }
        }

        if (adjacentNumbers.size() != 2) return 0;

        int ratio = 1;

        for (Pair<Integer, Integer> adjacentNumber : adjacentNumbers) {
            ratio *= getNumber(chars, adjacentNumber);
        }
        return ratio;
    }

    private static int getNumber(char[][] chars, Pair<Integer, Integer> number) {
        int numberY = number.first();
        int startX = number.second();

        while (startX > 0 && Character.isDigit(chars[number.first()][Math.max(0, startX - 1)])) {
            startX--;
        }

        int numberLength = lengthOfNumber(chars[numberY], startX);

        return Integer.parseInt(new String(Arrays.copyOfRange(chars[numberY], startX, startX + numberLength)));
    }

    private record Pair<F, S>(F first, S second) {

    }
}
