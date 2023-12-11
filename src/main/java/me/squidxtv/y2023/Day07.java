package me.squidxtv.y2023;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day07 extends Day {

    public Day07() throws URISyntaxException, IOException {
        super(7, 2023);
    }

    @Override
    public void execute() {
        List<Hand> hands = input.stream()
                .map(Hand::new)
                .collect(Collectors.toList());
        sort(hands);
        System.out.println("Part 1: " + sum(hands));

        Card.updateForPart2();
        hands.forEach(Hand::updateRank);
        sort(hands);
        System.out.println("Part 2: " + sum(hands));
    }

    private static void sort(List<Hand> list) {
        list.sort((o1, o2) -> {
            if (o1.rank == o2.rank) {
                return o1.betterHand(o2);
            }
            if (o1.rank.strength > o2.rank.strength) {
                return 1;
            }
            return -1;
        });
    }

    private static int sum(List<Hand> hands) {
        int sum = 0;
        for (int i = 0; i < hands.size(); i++) {
            sum += hands.get(i).bid * (i+1);
        }
        return sum;
    }

    private static class Hand {

        private final Map<Card, Integer> cardCount;
        private final Card[] cards;
        private final int bid;
        private Rank rank;

        public Hand(String line) {
            String[] input = line.split(" ");

            char[] c = input[0].toCharArray();
            this.cards = new Card[c.length];
            this.cardCount = new EnumMap<>(Card.class);
            for (int i = 0; i < c.length; i++) {
                this.cards[i] = Card.getByType(c[i] + "");
                cardCount.merge(this.cards[i], 1, (integer, integer2) -> ++integer);
            }

            updateRank();

            this.bid = Integer.parseInt(input[1]);
        }

        public void updateRank() {
            if (fiveOfAKind()) {
                rank = Rank.FIVE_OF_A_KIND;
            } else if(fourOfAKind()) {
                rank = Rank.FOUR_OF_A_KIND;
            } else if (fullHouse()) {
                rank = Rank.FULL_HOUSE;
            } else if (threeOfAKind()) {
                rank = Rank.THREE_OF_A_KIND;
            } else if (twoPair()) {
                rank = Rank.TWO_PAIR;
            } else if (onePair()) {
                rank = Rank.ONE_PAIR;
            } else {
                rank = Rank.HIGH_CARD;
            }
        }


        public boolean fiveOfAKind() {
            if (cardCount.size() == 1) {
                return true;
            }

            if (Card.J.strength == 11) {
                return false;
            }

            // Case J -> Joker
            if (!cardCount.containsKey(Card.J)) {
                return false;
            }
            return cardCount.size() == 2;
        }

        public boolean fourOfAKind() {
            if (cardCount.size() == 2 && cardCount.containsValue(4)) {
                return true;
            }

            if (Card.J.strength == 11) {
                return false;
            }

            // Case J -> Joker
            if (!cardCount.containsKey(Card.J)) {
                return false;
            }
            return cardCount.size() == 3 && ((cardCount.get(Card.J) == 1 && cardCount.containsValue(3)) || cardCount.get(Card.J) == 2 || cardCount.get(Card.J) == 3);
        }

        public boolean fullHouse() {
            if (cardCount.size() == 2 && cardCount.containsValue(3) && cardCount.containsValue(2)) {
                return true;
            }

            if (Card.J.strength == 11) {
                return false;
            }

            // Case J -> Joker
            if (!cardCount.containsKey(Card.J)) {
                return false;
            }
            return cardCount.size() == 3;
        }

        public boolean threeOfAKind() {
            if (cardCount.size() == 3 && cardCount.containsValue(3)) {
                return true;
            }

            if (Card.J.strength == 11) {
                return false;
            }

            // Case J -> Joker
            if (!cardCount.containsKey(Card.J)) {
                return false;
            }
            return cardCount.size() == 4;
        }

        public boolean twoPair() {
            return (cardCount.size() == 3 && cardCount.containsValue(2));
        }

        public boolean onePair() {
            if (cardCount.size() == 4 && cardCount.containsValue(2)) {
                return true;
            }

            if (Card.J.strength == 11) {
                return false;
            }

            // Case J -> Joker
            if (!cardCount.containsKey(Card.J)) {
                return false;
            }
            return cardCount.size() == 5;
        }

        public int betterHand(Hand other) {
            for (int i = 0; i < other.cards.length; i++) {
                Card handCard = this.cards[i];
                Card otherCard = other.cards[i];
                if (handCard.strength == otherCard.strength) {
                    continue;
                }
                if (handCard.strength > otherCard.strength) {
                    return 1;
                }
                return -1;
            }

            return 0;
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards=" + Arrays.toString(cards) +
                    ", rank=" + rank +
                    ", counts=" + cardCount +
                    '}';
        }

    }

    private enum Rank {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int strength;

        Rank(int strength) {
            this.strength = strength;
        }

    }

    private enum Card {
        A("A", 14),
        K("K", 13),
        Q("Q", 12),
        J("J", 11),
        T("T", 10),
        NINE("9", 9),
        EIGHT("8", 8),
        SEVEN("7", 7),
        SIX("6", 6),
        FIVE("5", 5),
        FOUR("4", 4),
        THREE("3", 3),
        TWO("2", 2);

        private final String type;
        private int strength;

        Card(String type, int strength) {
            this.type = type;
            this.strength = strength;
        }

        public static Card getByType(String type) {
            for (Card card : values()) {
                if (card.type.equals(type)) {
                    return card;
                }
            }
            throw new IllegalArgumentException("Type " + type + " does not exist!");
        }

        private static void updateForPart2() {
            Card.J.strength = 1;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
