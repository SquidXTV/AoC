package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends Day {

    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;

    public Day02() throws URISyntaxException, IOException {
        super(2, 2023);
    }

    @Override
    public void execute() {
        int sum1 = input.stream()
                .map(Game::new)
                .filter(Game::fits)
                .mapToInt(Game::getId)
                .sum();
        System.out.println("Part 1 " + sum1);

        int sum2 = input.stream()
                .map(Game::new)
                .mapToInt(Game::powers)
                .sum();
        System.out.println("Part 2: " + sum2);
    }


    static class Game {

        private static final Pattern ID_PATTERN = Pattern.compile("Game (\\d+):");

        private final int id;
        private final Subset[] sets;

        Game(String line) {
            int indexOfColon = line.indexOf(':');
            this.id = getGameId(line.substring(0, indexOfColon + 1));
            String[] subsets = line.substring(indexOfColon + 1).split(";");
            sets = new Subset[subsets.length];
            for (int i = 0; i < subsets.length; i++) {
                sets[i] = new Subset(subsets[i]);
            }
        }

        public boolean fits() {
            return Arrays.stream(sets).allMatch(Subset::fits);
        }

        public int powers() {
            int maxRed = 0;
            int maxGreen = 0;
            int maxBlue = 0;
            for (Subset set : sets) {
                if (set.reds > maxRed) {
                    maxRed = set.reds;
                }
                if (set.greens > maxGreen) {
                    maxGreen = set.greens;
                }
                if (set.blue > maxBlue) {
                    maxBlue = set.blue;
                }
            }
            return maxRed * maxGreen * maxBlue;
        }

        public int getId() {
            return id;
        }

        private static int getGameId(String id) {
            Matcher m = ID_PATTERN.matcher(id);
            m.find();
            return Integer.parseInt(m.group(1));
        }

    }

    static class Subset {

        private static final Pattern COLOR_PATTERN = Pattern.compile("^(\\d+)\\s+(\\w+)$");

        private int reds = 0;
        private int greens = 0;
        private int blue = 0;

        public Subset(String line) {
            String[] colors = line.split(",");
            for (String color : colors) {
                color = color.trim();
                Matcher m = COLOR_PATTERN.matcher(color);
                m.find();
                String c = m.group(2);
                int number = Integer.parseInt(m.group(1));
                switch (c) {
                    case "red" -> reds = number;
                    case "green" -> greens = number;
                    case "blue" -> blue = number;
                }
            }
        }

        public boolean fits() {
            return reds <= MAX_RED && greens <= MAX_GREEN && blue <= MAX_BLUE;
        }

    }

}
