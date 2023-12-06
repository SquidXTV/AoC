package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day06 extends Day {

    private static final int SPEED = 1; // 1 mm/ms

    public Day06() throws URISyntaxException, IOException {
        super(6, 2023);
    }

    @Override
    public void execute() {
        long[] time = Arrays.stream(input.get(0).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();
        long[] distance = Arrays.stream(input.get(1).split(":")[1].trim().split("\\s+"))
                .mapToLong(Long::parseLong)
                .toArray();

        Map<Long, Long> mapping = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            mapping.put(time[i], distance[i]);
        }

        long wins = mapping.entrySet().stream()
                .mapToLong(Day06::getNumberOfWins)
                .reduce((n1, n2) -> n1 * n2)
                .getAsLong();
        System.out.println("Part 1: " + wins);

        long time2 = Long.parseLong(Arrays.stream(time)
                .mapToObj(String::valueOf)
                .reduce((s, s2) -> s + s2)
                .get());
        long distance2 = Long.parseLong(Arrays.stream(distance)
                .mapToObj(String::valueOf)
                .reduce((s, s2) -> s + s2)
                .get());
        System.out.println(time2);
        System.out.println(distance2);
        System.out.println("Part 2: " + getNumberOfWins(Map.entry(time2, distance2)));
    }


    private static long getNumberOfWins(Map.Entry<Long, Long> entry) {
        long time = entry.getKey();
        long distance = entry.getValue();
        long wins = 0;
        for (long i = 0; i <= time; i++) {
            long timeTraveling = time - i;
            long speed = i * SPEED;
            long dist = timeTraveling * speed;
            if (dist > distance) {
                wins++;
            }
        }
        return wins;
    }

}
