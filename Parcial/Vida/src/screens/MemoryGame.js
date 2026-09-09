import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from 'react-native';

const symbols = ['🍎', '🍌', '🍇', '🍉', '🍓', '🥝', '🍒', '🥥'];

function createBoard() {
  return [...symbols, ...symbols]
    .sort(() => Math.random() - 0.5)
    .map((symbol, index) => ({
      id: index,
      symbol,
      flipped: false,
      matched: false,
    }));
}

export default function MemoryGame() {
  const [cards, setCards] = useState(createBoard);
  const [selected, setSelected] = useState([]);
  const [moves, setMoves] = useState(0);

  useEffect(() => {
    if (selected.length !== 2) return;

    const [first, second] = selected;

    setMoves((value) => value + 1);

    if (cards[first].symbol === cards[second].symbol) {
      setCards((current) =>
        current.map((card, index) =>
          index === first || index === second
            ? { ...card, matched: true }
            : card
        )
      );

      setSelected([]);
    } else {
      const timer = setTimeout(() => {
        setCards((current) =>
          current.map((card, index) =>
            index === first || index === second
              ? { ...card, flipped: false }
              : card
          )
        );

        setSelected([]);
      }, 800);

      return () => clearTimeout(timer);
    }
  }, [selected]);

  useEffect(() => {
    if (
      cards.length > 0 &&
      cards.every((card) => card.matched)
    ) {
      setTimeout(() => {
        Alert.alert(
          '🎉 ¡Ganaste!',
          `Completaste el memorama en ${moves} movimientos.`,
          [
            {
              text: 'Jugar otra vez',
              onPress: resetGame,
            },
          ]
        );
      }, 300);
    }
  }, [cards]);

  const flipCard = (index) => {
    if (
      selected.length >= 2 ||
      cards[index].flipped ||
      cards[index].matched
    ) {
      return;
    }

    setCards((current) =>
      current.map((card, i) =>
        i === index
          ? { ...card, flipped: true }
          : card
      )
    );

    setSelected((current) => [...current, index]);
  };

  const resetGame = () => {
    setCards(createBoard());
    setSelected([]);
    setMoves(0);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>🧠 Memorama</Text>

      <Text style={styles.moves}>
        Movimientos: {moves}
      </Text>

      <View style={styles.board}>
        {cards.map((card, index) => (
          <TouchableOpacity
            key={card.id}
            style={[
              styles.card,
              card.flipped || card.matched
                ? styles.cardOpen
                : styles.cardClosed,
            ]}
            onPress={() => flipCard(index)}
          >
            <Text style={styles.cardText}>
              {card.flipped || card.matched
                ? card.symbol
                : '?'}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      <TouchableOpacity
        style={styles.reset}
        onPress={resetGame}
      >
        <Text style={styles.resetText}>
          Reiniciar
        </Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F7F7FC',
    alignItems: 'center',
    paddingTop: 35,
  },

  title: {
    fontSize: 30,
    fontWeight: 'bold',
  },

  moves: {
    fontSize: 16,
    color: '#777',
    marginTop: 8,
  },

  board: {
    width: 340,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    marginTop: 30,
    gap: 8,
  },

  card: {
    width: 76,
    height: 76,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },

  cardClosed: {
    backgroundColor: '#6C63FF',
  },

  cardOpen: {
    backgroundColor: '#fff',
    borderWidth: 2,
    borderColor: '#6C63FF',
  },

  cardText: {
    fontSize: 30,
    fontWeight: 'bold',
  },

  reset: {
    backgroundColor: '#6C63FF',
    paddingVertical: 14,
    paddingHorizontal: 35,
    borderRadius: 12,
    marginTop: 25,
  },

  resetText: {
    color: '#fff',
    fontWeight: 'bold',
  },
});
