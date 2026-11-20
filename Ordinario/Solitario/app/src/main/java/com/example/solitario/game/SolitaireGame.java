package com.example.solitario.game;

import com.example.solitario.model.Card;
import com.example.solitario.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class SolitaireGame {

    private Deck deck;

    private final List<Card> waste = new ArrayList<>();

    private final List<List<Card>> tableau = new ArrayList<>();

    private final List<List<Card>> foundations = new ArrayList<>();

    private int drawCount = 1;
    private int moves = 0;

    public SolitaireGame() {
        newGame();
    }

    public void setDrawCount(int count) {
        if (count == 1 || count == 3) {
            drawCount = count;
        }
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getMoves() {
        return moves;
    }

    public void newGame() {

        deck = new Deck();

        waste.clear();
        tableau.clear();
        foundations.clear();

        moves = 0;

        for (int i = 0; i < 7; i++) {
            tableau.add(new ArrayList<Card>());
        }

        for (int i = 0; i < 4; i++) {
            foundations.add(new ArrayList<Card>());
        }

        for (int column = 0; column < 7; column++) {

            for (int row = 0; row <= column; row++) {

                Card card = deck.drawCard();

                if (card != null) {
                    card.setFaceUp(row == column);
                    tableau.get(column).add(card);
                }
            }
        }
    }

    public void drawFromDeck() {

        if (!deck.isEmpty()) {

            for (int i = 0; i < drawCount; i++) {

                Card card = deck.drawCard();

                if (card == null) break;

                card.setFaceUp(true);
                waste.add(card);
            }

        } else if (!waste.isEmpty()) {

            List<Card> recycled = new ArrayList<>();

            for (int i = waste.size() - 1; i >= 0; i--) {

                Card card = waste.get(i);
                card.setFaceUp(false);
                recycled.add(card);
            }

            waste.clear();
            deck = new Deck(recycled);
        }

        moves++;
    }

    public boolean moveTableau(
            int sourceColumn,
            int cardIndex,
            int destinationColumn) {

        if (sourceColumn < 0 || sourceColumn >= 7 ||
                destinationColumn < 0 || destinationColumn >= 7 ||
                sourceColumn == destinationColumn) {
            return false;
        }

        List<Card> source = tableau.get(sourceColumn);
        List<Card> destination = tableau.get(destinationColumn);

        if (cardIndex < 0 || cardIndex >= source.size()) {
            return false;
        }

        if (!GameRules.isValidSequence(source, cardIndex)) {
            return false;
        }

        Card movingCard = source.get(cardIndex);

        Card destinationTop = destination.isEmpty()
                ? null
                : destination.get(destination.size() - 1);

        if (!GameRules.canMoveToTableau(
                movingCard, destinationTop)) {
            return false;
        }

        List<Card> movingCards = new ArrayList<>(
                source.subList(cardIndex, source.size())
        );

        source.subList(cardIndex, source.size()).clear();

        destination.addAll(movingCards);

        revealLastCard(source);

        moves++;

        return true;
    }

    public boolean moveWasteToTableau(int columnIndex) {

        if (waste.isEmpty() ||
                columnIndex < 0 || columnIndex >= 7) {
            return false;
        }

        List<Card> destination = tableau.get(columnIndex);

        Card movingCard = getWasteTop();

        Card destinationTop = destination.isEmpty()
                ? null
                : destination.get(destination.size() - 1);

        if (!GameRules.canMoveToTableau(
                movingCard, destinationTop)) {
            return false;
        }

        waste.remove(waste.size() - 1);
        destination.add(movingCard);

        moves++;

        return true;
    }

    public boolean moveWasteToFoundation(int index) {

        if (waste.isEmpty() || index < 0 || index >= 4) {
            return false;
        }

        List<Card> foundation = foundations.get(index);

        Card movingCard = getWasteTop();

        Card top = foundation.isEmpty()
                ? null
                : foundation.get(foundation.size() - 1);

        if (!GameRules.canMoveToFoundation(movingCard, top)) {
            return false;
        }

        waste.remove(waste.size() - 1);
        foundation.add(movingCard);

        moves++;

        return true;
    }

    public boolean moveTableauToFoundation(
            int columnIndex,
            int foundationIndex) {

        if (columnIndex < 0 || columnIndex >= 7 ||
                foundationIndex < 0 || foundationIndex >= 4) {
            return false;
        }

        List<Card> column = tableau.get(columnIndex);
        List<Card> foundation = foundations.get(foundationIndex);

        if (column.isEmpty()) {
            return false;
        }

        Card movingCard = column.get(column.size() - 1);

        Card top = foundation.isEmpty()
                ? null
                : foundation.get(foundation.size() - 1);

        if (!GameRules.canMoveToFoundation(movingCard, top)) {
            return false;
        }

        column.remove(column.size() - 1);
        foundation.add(movingCard);

        revealLastCard(column);

        moves++;

        return true;
    }

    private void revealLastCard(List<Card> column) {

        if (!column.isEmpty()) {

            Card last = column.get(column.size() - 1);

            last.setFaceUp(true);
        }
    }

    public Card getWasteTop() {
        return waste.isEmpty()
                ? null
                : waste.get(waste.size() - 1);
    }

    public Card getTableauTop(int index) {
        if (index < 0 || index >= 7 || tableau.get(index).isEmpty()) {
            return null;
        }

        List<Card> column = tableau.get(index);
        return column.get(column.size() - 1);
    }

    public List<Card> getColumn(int index) {
        return tableau.get(index);
    }

    public List<Card> getWaste() {
        return waste;
    }

    public List<List<Card>> getTableau() {
        return tableau;
    }

    public List<List<Card>> getFoundations() {
        return foundations;
    }

    public boolean hasStockCards() {
        return !deck.isEmpty();
    }

    public boolean hasWon() {

        int total = 0;

        for (List<Card> foundation : foundations) {
            total += foundation.size();
        }

        return total == 52;
    }

    public int getRemainingCards() {
        return deck.size();
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }
}
