package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day10 extends Day {

//    | is a vertical pipe connecting north and south.
//    - is a horizontal pipe connecting east and west.
//    L is a 90-degree bend connecting north and east.
//    J is a 90-degree bend connecting north and west.
//    7 is a 90-degree bend connecting south and west.
//    F is a 90-degree bend connecting south and east.
//    . is ground; there is no pipe in this tile.
//    S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.

    public Day10() throws URISyntaxException, IOException {
        super(10, 2023);
    }

    @Override
    public void execute() {
        Map<Tile, List<Tile>> graph = parse();
        System.out.println("Part 1: " + getFurthestPoint(graph));
    }

    private static int getFurthestPoint(Map<Tile, List<Tile>> graph) {
        Tile start = graph.keySet().stream()
                .filter(tile -> tile.character == 'S')
                .findFirst()
                .get();

        return (int) graph.keySet().stream()
                .filter(tile -> tile.character != 'S')
                .mapToDouble(tile -> shortestPathLength(graph, start, tile))
                .filter(Double::isFinite)
                .max().getAsDouble();
    }

    private static double shortestPathLength(Map<Tile, List<Tile>> graph, Tile source, Tile destination) {
        Map<Tile, Double> distances = new HashMap<>();
        PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<Tile> settledNodes = new HashSet<>();

        for (Tile node : graph.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }

        distances.put(source, 0.0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            settledNodes.add(current);

            for (Tile neighbor : graph.get(current)) {
                if (!settledNodes.contains(neighbor)) {
                    double oldDistance = distances.get(neighbor);
                    double newDistance = distances.get(current) + 1; // Add the weight of the edge

                    if (newDistance < oldDistance) {
                        distances.put(neighbor, newDistance);

                        queue.remove(neighbor); // Remove the old node
                        queue.add(neighbor); // Add the updated node
                    }
                }
            }
        }

        return distances.get(destination);
    }

    private Map<Tile, List<Tile>> parse() {
        Map<Tile, List<Tile>> graph = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            char[] line = input.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                char character = line[x];
                if (character == '.') {
                    continue;
                }
                List<Tile> connected = new ArrayList<>();
                switch (character) {
                    case '|' -> {
                        addAbove(connected, x, y);
                        addBelow(connected, x, y);
                    }
                    case '-' -> {
                        addLeft(connected, x, y, line);
                        addRight(connected, x, y, line);
                    }
                    case 'L' -> {
                        addAbove(connected, x, y);
                        addRight(connected, x, y, line);
                    }
                    case 'J' -> {
                        addAbove(connected, x, y);
                        addLeft(connected, x, y, line);
                    }
                    case '7' -> {
                        addLeft(connected, x, y, line);
                        addBelow(connected, x, y);
                    }
                    case 'F' -> {
                        addBelow(connected, x, y);
                        addRight(connected, x, y, line);
                    }
                    case 'S' -> {
                        addAbove(connected, x, y);
                        addBelow(connected, x, y);
                        addLeft(connected, x, y, line);
                        addRight(connected, x, y, line);
                    }
                }
                graph.put(new Tile(x, y, character), connected);
            }
        }
        return graph;
    }

    private void addAbove(List<Tile> list, int x, int y) {
        if (y != 0) {
            char character = input.get(y - 1).charAt(x);
            if (character == '.') {
                return;
            }
            if (character != '|'
                    && character != '7'
                    && character != 'F'
                    && character != 'S') {
                return;
            }
            list.add(new Tile(x, y - 1, character));
        }
    }

    private void addBelow(List<Tile> list, int x, int y) {
        if (y != input.size() - 1) {
            char character = input.get(y + 1).charAt(x);
            if (character == '.') {
                return;
            }
            if (character != '|'
                    && character != 'J'
                    && character != 'L'
                    && character != 'S') {
                return;
            }
            list.add(new Tile(x, y + 1, character));
        }
    }

    private void addLeft(List<Tile> list, int x, int y, char[] line) {
        if (x != 0) {
            char character = line[x - 1];
            if (character == '.') {
                return;
            }
            if (character != '-'
                    && character != 'L'
                    && character != 'F'
                    && character != 'S') {
                return;
            }
            list.add(new Tile(x - 1, y, character));
        }
    }

    private void addRight(List<Tile> list, int x, int y, char[] line) {
        if (x != line.length - 1) {
            char character = line[x + 1];
            if (character == '.') {
                return;
            }
            if (character != '-'
                    && character != 'J'
                    && character != '7'
                    && character != 'S') {
                return;
            }
            list.add(new Tile(x + 1, y, character));
        }
    }

    private record Tile(int x, int y, char character) {
    }



}
