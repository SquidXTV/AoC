package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day04 extends Day {

    public Day04() throws URISyntaxException, IOException {
        super(4, 2023);
    }

    @Override
    public void execute() {
        Card[] cards = input.stream()
                .map(Card::new)
                .toArray(Card[]::new);

        int points = Arrays.stream(cards).mapToInt(Card::score).sum();
        System.out.println("Part 1: " + points);

        List<Integer> numberOfCards = new ArrayList<>(Collections.nCopies(cards.length, 0));
        for (int i = 0; i < cards.length; i++) {
            fillCards(cards, numberOfCards, i);
        }
        int sum = numberOfCards.stream()
                .mapToInt(value -> value)
                .sum();
        System.out.println("Part 2: " + sum);
    }

    private static void fillCards(Card[] cards, List<Integer> numberOfCards, int currentIndex) {
        Card current = cards[currentIndex];
        numberOfCards.set(currentIndex, numberOfCards.get(currentIndex) + 1);

        for (int i = currentIndex + 1; i <= currentIndex + current.hits; i++) {
            fillCards(cards, numberOfCards, i);
        }
    }

    private static class Card {
        private final int id;
        private final List<Integer> winning = new ArrayList<>();
        private final List<Integer> got = new ArrayList<>();

        private final int hits;

        public Card(String line) {
            String[] split = line.split(":");
            this.id = Integer.parseInt(split[0].split("\\s+")[1]);
            split = split[1].split("\\|");
            for (String s : split[0].trim().split("\\s+")) {
                winning.add(Integer.parseInt(s));
            }
            for (String s : split[1].trim().split("\\s+")) {
                got.add(Integer.parseInt(s));
            }

            int h = 0;
            for (int number : got) {
                if (winning.contains(number)) {
                    h++;
                }
            }
            this.hits = h;
        }

        public int score() {
            if (hits == 0) return 0;
            return (int) Math.pow(2.0, hits - 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Card card = (Card) o;

            return id == card.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

}
