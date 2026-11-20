package com.example.solitario.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {

        cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (int value = 1; value <= 13; value++) {
                cards.add(new Card(suit, value));
            }
        }

        shuffle();
    }

    public Deck(List<Card> existingCards) {
        cards = new ArrayList<>(existingCards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {

        if (cards.isEmpty()) {
            return null;
        }

        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }
}
