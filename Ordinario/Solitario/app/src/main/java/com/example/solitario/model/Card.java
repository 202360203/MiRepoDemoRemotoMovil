
package com.example.solitario.model;

public class Card {

    private final Suit suit;
    private final int value;
    private boolean faceUp;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isRed() {
        return suit.isRed();
    }

    public String getDisplayValue() {
        switch (value) {
            case 1: return "A";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: return String.valueOf(value);
        }
    }

    public String getDisplayName() {
        return getDisplayValue() + suit.getSymbol();
    }
}
