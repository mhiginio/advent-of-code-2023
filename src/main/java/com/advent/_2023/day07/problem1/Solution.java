package com.advent._2023.day07.problem1;

import com.advent._2023.Parser;
import lombok.Data;

import java.util.*;

public class Solution {
    public long solution(Parser parser) {
        List<Hand> sortedHands = parser.streamLines()
                .map(Hand::parse)
                .sorted(Collections.reverseOrder(new HandComparator())).toList();

        int strength = sortedHands.size();
        int solution = 0;
        for (Hand hand : sortedHands) {
            solution = solution + hand.getBid() * strength;
            strength--;
        }
        return solution;

    }

    @Data
    public static class Hand {
        public static final int FIVE_OF_A_KIND = 10;
        public static final int FOUR_OF_A_KIND = 9;
        public static final int FULL_HOUSE = 8;
        public static final int THREE_OF_A_KIND = 7;
        public static final int TWO_PAIR = 6;
        public static final int ONE_PAIR = 5;
        public static final int HIGH_CARD = 4;
        private char[] cards;
        private int bid;

        public static Hand parse(String rawData) {
            Hand hand = new Hand();
            String[] split = rawData.split(" ");
            hand.setCards(split[0].toCharArray());
            hand.setBid(Integer.parseInt(split[1]));
            return hand;
        }

        public int getType() {
            Collection<Integer> frequencies = frequencies().values();
            if (frequencies.contains(5)) {
                return FIVE_OF_A_KIND;
            }
            if (frequencies.contains(4)) {
                return FOUR_OF_A_KIND;
            }
            if (frequencies.contains(3) && frequencies.contains(2)) {
                return FULL_HOUSE;
            }
            if (frequencies.contains(3)) {
                return THREE_OF_A_KIND;
            }
            if (frequencies.stream().filter(k -> k == 2).count() == 2) {
                return TWO_PAIR;
            }
            if (frequencies.contains(2)) {
                return ONE_PAIR;
            }
            return HIGH_CARD;
        }

        private Map<Character, Integer> frequencies() {
            Map<Character, Integer> frequencies = new HashMap<>();
            for (char card : cards) {
                frequencies.put(card, frequencies.getOrDefault(card, 0) + 1);
            }
            return frequencies;
        }
    }

    public static final class HandComparator implements Comparator<Hand> {
        private static final String SORTED_CARDS = "AKQJT98765432";

        @Override
        public int compare(Hand o1, Hand o2) {
            int type1 = o1.getType();
            int type2 = o2.getType();
            if (type1 > type2) {
                return 1;
            }
            if (type2 > type1) {
                return -1;
            }
            return compareCards(o1.getCards(), o2.getCards());
        }

        private int compareCards(char[] cards1, char[] cards2) {
            for (int i = 0; i < cards1.length; i++) {
                int compareCard = compareCard(cards1[i], cards2[i]);
                if (compareCard != 0) {
                    return compareCard;
                }
            }
            return 0;
        }

        private int compareCard(char card1, char card2) {
            int idx1 = SORTED_CARDS.indexOf(card1);
            int idx2 = SORTED_CARDS.indexOf(card2);
            if (idx1 < idx2) {
                return 1;
            }
            if (idx1 > idx2) {
                return -1;
            }
            return 0;
        }
    }
}