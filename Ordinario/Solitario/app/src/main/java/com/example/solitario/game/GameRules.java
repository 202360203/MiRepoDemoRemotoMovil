package com.example.solitario.game;

import com.example.solitario.model.Card;
import java.util.List;

public class GameRules {

    public static boolean canMoveToTableau(
            Card movingCard,
            Card destinationCard) {

        if (movingCard == null) {
            return false;
        }

        if (destinationCard == null) {
            return movingCard.getValue() == 13;
        }

        return movingCard.isRed() != destinationCard.isRed()
                && movingCard.getValue() ==
                destinationCard.getValue() - 1;
    }

    public static boolean canMoveToFoundation(
            Card movingCard,
            Card foundationTop) {

        if (movingCard == null) {
            return false;
        }

        if (foundationTop == null) {
            return movingCard.getValue() == 1;
        }

        return movingCard.getSuit() == foundationTop.getSuit()
                && movingCard.getValue() ==
                foundationTop.getValue() + 1;
    }

    public static boolean isValidSequence(
            List<Card> cards,
            int startIndex) {

        if (cards == null || startIndex < 0 ||
                startIndex >= cards.size()) {
            return false;
        }

        for (int i = startIndex; i < cards.size(); i++) {

            Card current = cards.get(i);

            if (!current.isFaceUp()) {
                return false;
            }

            if (i > startIndex) {

                Card previous = cards.get(i - 1);

                if (!canMoveToTableau(current, previous)) {
                    return false;
                }
            }
        }

        return true;
    }
}
