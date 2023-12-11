package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Day11 extends Day {

    public Day11() throws URISyntaxException, IOException {
        super(11, 2023);
    }

    @Override
    public void execute() {
        Predicate<String> allDots = Pattern.compile("^\\.+$").asPredicate();
        List<Integer> expansionHorizontally = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            if (allDots.test(input.get(i))) {
                expansionHorizontally.add(i);
            }
        }

        List<Integer> expansionVertically = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            int index = i;
            String vertical = input.stream()
                    .map(s -> s.charAt(index))
                    .map(c -> String.valueOf(c))
                    .reduce((s, s2) -> s + s2)
                    .get();

            if (allDots.test(vertical)) {
                expansionVertically.add(index);
            }
        }

        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] == '#') {
                    galaxies.add(new Galaxy(j, i));
                }
            }
        }

        List<Long> distances1 = distances(galaxies, expansionHorizontally, expansionVertically, 1);
        long sum1 = distances1.stream().mapToLong(Long::longValue).sum();
        System.out.println("Part 1: " + sum1);

        List<Long> distances2 = distances(galaxies, expansionHorizontally, expansionVertically, 1_000_000 - 1);
        long sum2 = distances2.stream().mapToLong(Long::longValue).sum();
        System.out.println("Part 2: " + sum2);
    }

    private static List<Long> distances(List<Galaxy> galaxies, List<Integer> horizontal, List<Integer> vertical, int factor) {
        List<Long> distances = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            Galaxy first = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                Galaxy second = galaxies.get(j);

                int startY;
                int endY;
                if (first.y <= second.y) {
                    startY = first.y;
                    endY = second.y;
                } else {
                    startY = second.y;
                    endY = first.y;
                }
                long distance = endY - startY;

                distance += factor * horizontal.stream().filter(n -> n > startY && n < endY).count();

                int startX;
                int endX;
                if (first.x <= second.x) {
                    startX = first.x;
                    endX = second.x;
                } else {
                    startX = second.x;
                    endX = first.x;
                }
                distance += endX - startX;

                distance += factor * vertical.stream().filter(n -> n > startX && n < endX).count();
                distances.add(distance);
            }
        }
        return distances;
    }

    private record Galaxy(int x, int y) {
    }
}
