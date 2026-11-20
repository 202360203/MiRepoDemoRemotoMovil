package com.example.solitario.model;

public enum Suit {

    HEARTS("♥", true),
    DIAMONDS("♦", true),
    CLUBS("♣", false),
    SPADES("♠", false);

    private final String symbol;
    private final boolean red;

    Suit(String symbol, boolean red) {
        this.symbol = symbol;
        this.red = red;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isRed() {
        return red;
    }
}
