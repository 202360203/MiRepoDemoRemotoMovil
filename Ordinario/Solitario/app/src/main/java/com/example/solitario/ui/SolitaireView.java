package com.example.solitario.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import com.example.solitario.game.SolitaireGame;
import com.example.solitario.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SolitaireView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final SolitaireGame game;

    private float cardWidth;
    private float cardHeight;
    private float columnGap;
    private float cardOffset;

    private final int green = Color.rgb(20, 110, 65);
    private final int darkGreen = Color.rgb(12, 75, 45);

    private static final int SOURCE_NONE = 0;
    private static final int SOURCE_WASTE = 1;
    private static final int SOURCE_TABLEAU = 2;

    private int selectedSource = SOURCE_NONE;
    private int selectedColumn = -1;
    private int selectedCardIndex = -1;

    private boolean dragging = false;
    private boolean won = false;

    private float dragX;
    private float dragY;

    private long startTime;
    private long elapsedTime = 0;

    private int lastMoves = -1;

    private final Handler handler = new Handler();

    private final List<Card> draggedCards = new ArrayList<>();

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!won) {
                elapsedTime = System.currentTimeMillis() - startTime;
                invalidate();
                handler.postDelayed(this, 1000);
            }
        }
    };

    public SolitaireView(Context context) {
        super(context);

        game = new SolitaireGame();

        startNewGame();

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void startNewGame() {

        game.newGame();

        game.setDrawCount(1);

        won = false;
        elapsedTime = 0;
        startTime = System.currentTimeMillis();

        handler.removeCallbacks(timerRunnable);
        handler.post(timerRunnable);

        clearDrag();
    }

    private void calculateDimensions(Canvas canvas) {

        float width = canvas.getWidth();

        cardWidth = (width - 32f) / 7.3f;
        cardHeight = cardWidth * 1.4f;

        columnGap = (width - cardWidth * 7) / 8f;

        cardOffset = cardHeight * 0.28f;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        calculateDimensions(canvas);

        canvas.drawColor(green);

        drawHeader(canvas);
        drawTopArea(canvas);
        drawTableau(canvas);

        if (dragging) {
            drawDraggedCards(canvas);
        }

        if (game.hasWon() && !won) {
            won = true;
            handler.removeCallbacks(timerRunnable);
        }

        if (won) {
            drawVictory(canvas);
        }
    }

    private void drawHeader(Canvas canvas) {

        paint.setColor(Color.WHITE);
        paint.setTextSize(22);
        paint.setFakeBoldText(true);

        canvas.drawText("SOLITARIO", 15, 25, paint);

        paint.setTextSize(14);
        paint.setFakeBoldText(false);

        canvas.drawText(
                "Movimientos: " + game.getMoves(),
                15, 48, paint
        );

        canvas.drawText(
                "Tiempo: " + getFormattedTime(),
                170, 48, paint
        );

        canvas.drawText(
                "Robar: " + game.getDrawCount(),
                15, 70, paint
        );

        paint.setColor(Color.rgb(255, 220, 120));
        canvas.drawText("Nueva partida", 190, 70, paint);
    }

    private String getFormattedTime() {

        long seconds = elapsedTime / 1000;

        long minutes = seconds / 60;

        seconds %= 60;

        return String.format(
                Locale.getDefault(),
                "%02d:%02d",
                minutes,
                seconds
        );
    }

    private void drawTopArea(Canvas canvas) {

        float top = 100;

        float stockX = columnGap;

        drawEmptySlot(canvas, stockX, top);

        if (game.hasStockCards()) {
            drawCardBack(canvas, stockX, top);
        }

        float wasteX = columnGap * 2 + cardWidth;

        drawEmptySlot(canvas, wasteX, top);

        Card wasteCard = game.getWasteTop();

        if (wasteCard != null &&
                !(dragging && selectedSource == SOURCE_WASTE)) {
            drawCard(canvas, wasteCard, wasteX, top);
        }

        for (int i = 0; i < 4; i++) {

            float x = columnGap * (4 + i)
                    + cardWidth * (3 + i);

            drawEmptySlot(canvas, x, top);

            List<Card> foundation = game.getFoundations().get(i);

            if (!foundation.isEmpty()) {
                Card card = foundation.get(foundation.size() - 1);
                drawCard(canvas, card, x, top);
            }
        }
    }

    private void drawTableau(Canvas canvas) {

        List<List<Card>> columns = game.getTableau();

        for (int column = 0; column < 7; column++) {

            float x = columnGap * (column + 1)
                    + cardWidth * column;

            List<Card> cards = columns.get(column);

            if (cards.isEmpty()) {
                drawEmptySlot(canvas, x, cardHeight + 125);
                continue;
            }

            for (int i = 0; i < cards.size(); i++) {

                if (dragging &&
                        selectedSource == SOURCE_TABLEAU &&
                        selectedColumn == column &&
                        i >= selectedCardIndex) {
                    continue;
                }

                Card card = cards.get(i);

                float y = cardHeight + 125 + i * cardOffset;

                drawCard(canvas, card, x, y);
            }
        }
    }

    private void drawEmptySlot(Canvas canvas, float x, float y) {

        paint.setColor(darkGreen);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        canvas.drawRoundRect(
                x, y,
                x + cardWidth,
                y + cardHeight,
                10, 10, paint
        );

        paint.setStyle(Paint.Style.FILL);
    }

    private void drawCardBack(Canvas canvas, float x, float y) {

        paint.setColor(Color.WHITE);

        canvas.drawRoundRect(
                x, y,
                x + cardWidth,
                y + cardHeight,
                10, 10, paint
        );

        paint.setColor(Color.rgb(35, 75, 160));

        canvas.drawRoundRect(
                x + 5, y + 5,
                x + cardWidth - 5,
                y + cardHeight - 5,
                7, 7, paint
        );
    }

    private void drawCard(
            Canvas canvas,
            Card card,
            float x,
            float y) {

        RectF rect = new RectF(
                x, y,
                x + cardWidth,
                y + cardHeight
        );

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(card.isFaceUp()
                ? Color.WHITE
                : Color.rgb(35, 75, 160));

        canvas.drawRoundRect(rect, 10, 10, paint);

        if (!card.isFaceUp()) {
            return;
        }

        paint.setColor(card.isRed() ? Color.RED : Color.BLACK);

        paint.setTextSize(cardWidth * 0.28f);
        paint.setFakeBoldText(true);

        canvas.drawText(
                card.getDisplayName(),
                x + 5,
                y + cardWidth * 0.35f,
                paint
        );

        paint.setTextSize(cardWidth * 0.55f);

        canvas.drawText(
                card.getSuit().getSymbol(),
                x + cardWidth * 0.35f,
                y + cardHeight * 0.65f,
                paint
        );

        paint.setFakeBoldText(false);
    }

    private void drawDraggedCards(Canvas canvas) {

        float x = dragX - cardWidth / 2;
        float y = dragY - cardHeight / 2;

        for (int i = 0; i < draggedCards.size(); i++) {
            drawCard(canvas, draggedCards.get(i),
                    x, y + i * cardOffset);
        }
    }

    private void drawVictory(Canvas canvas) {

        paint.setColor(Color.argb(190, 0, 0, 0));

        canvas.drawRect(
                0, 0, getWidth(), getHeight(), paint
        );

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setFakeBoldText(true);

        canvas.drawText(
                "¡HAS GANADO!",
                getWidth() / 2f - 115,
                getHeight() / 2f,
                paint
        );

        paint.setTextSize(18);
        paint.setFakeBoldText(false);

        canvas.drawText(
                "Tiempo: " + getFormattedTime(),
                getWidth() / 2f - 65,
                getHeight() / 2f + 40,
                paint
        );
    }

    private boolean isInside(
            float x, float y,
            float left, float top,
            float width, float height) {

        return x >= left && x <= left + width
                && y >= top && y <= top + height;
    }

    private int getColumnAt(float x) {

        for (int i = 0; i < 7; i++) {

            float columnX = columnGap * (i + 1)
                    + cardWidth * i;

            if (x >= columnX && x <= columnX + cardWidth) {
                return i;
            }
        }

        return -1;
    }

    private int getCardIndexAt(int column, float y) {

        List<Card> cards = game.getColumn(column);

        float startY = cardHeight + 125;

        for (int i = cards.size() - 1; i >= 0; i--) {

            float cardY = startY + i * cardOffset;

            float visibleHeight =
                    i == cards.size() - 1 ? cardHeight : cardOffset;

            if (y >= cardY && y <= cardY + visibleHeight) {
                return i;
            }
        }

        return -1;
    }

    private void startDrag(float x, float y) {

        draggedCards.clear();

        float wasteX = columnGap * 2 + cardWidth;

        if (isInside(x, y, wasteX, 100,
                cardWidth, cardHeight)) {

            Card card = game.getWasteTop();

            if (card != null) {
                selectedSource = SOURCE_WASTE;
                draggedCards.add(card);
                dragging = true;
            }

            return;
        }

        int column = getColumnAt(x);

        if (column == -1) return;

        List<Card> cards = game.getColumn(column);

        int index = getCardIndexAt(column, y);

        if (index == -1) return;

        Card card = cards.get(index);

        if (!card.isFaceUp()) {

            if (index == cards.size() - 1) {
                card.setFaceUp(true);
            }

            return;
        }

        selectedSource = SOURCE_TABLEAU;
        selectedColumn = column;
        selectedCardIndex = index;

        for (int i = index; i < cards.size(); i++) {
            draggedCards.add(cards.get(i));
        }

        dragging = true;
    }

    private void finishDrag(float x, float y) {

        if (!dragging) return;

        int destination = getColumnAt(x);

        if (y <= cardHeight + 160) {

            int foundation = getFoundationAt(x, y);

            if (foundation != -1 && draggedCards.size() == 1) {

                if (selectedSource == SOURCE_WASTE) {
                    game.moveWasteToFoundation(foundation);

                } else if (selectedSource == SOURCE_TABLEAU) {
                    game.moveTableauToFoundation(
                            selectedColumn, foundation
                    );
                }
            }

        } else if (destination != -1) {

            if (selectedSource == SOURCE_WASTE) {
                game.moveWasteToTableau(destination);

            } else if (selectedSource == SOURCE_TABLEAU) {
                game.moveTableau(
                        selectedColumn,
                        selectedCardIndex,
                        destination
                );
            }
        }

        clearDrag();
        invalidate();
    }

    private int getFoundationAt(float x, float y) {

        for (int i = 0; i < 4; i++) {

            float foundationX = columnGap * (4 + i)
                    + cardWidth * (3 + i);

            if (isInside(x, y, foundationX, 100,
                    cardWidth, cardHeight)) {
                return i;
            }
        }

        return -1;
    }

    private void clearDrag() {

        dragging = false;
        draggedCards.clear();

        selectedSource = SOURCE_NONE;
        selectedColumn = -1;
        selectedCardIndex = -1;
    }

    private void handleTap(float x, float y) {

        if (y < 80) {

            if (x > 180) {
                startNewGame();
            } else if (x > 100) {
                game.setDrawCount(
                        game.getDrawCount() == 1 ? 3 : 1
                );
            }

            invalidate();
            return;
        }

        if (isInside(x, y, columnGap, 100,
                cardWidth, cardHeight)) {

            game.drawFromDeck();
            invalidate();
            return;
        }

        startDrag(x, y);

        if (dragging) {
            finishDrag(x, y);
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                dragX = x;
                dragY = y;

                if (y >= 80) {
                    startDrag(x, y);
                }

                return true;

            case MotionEvent.ACTION_MOVE:

                if (dragging) {
                    dragX = x;
                    dragY = y;
                    invalidate();
                }

                return true;

            case MotionEvent.ACTION_UP:

                if (dragging) {
                    finishDrag(x, y);
                } else {
                    handleTap(x, y);
                }

                performClick();
                invalidate();

                return true;

            case MotionEvent.ACTION_CANCEL:

                clearDrag();
                invalidate();

                return true;
        }

        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(timerRunnable);
    }

    public SolitaireGame getGame() {
        return game;
    }
}
