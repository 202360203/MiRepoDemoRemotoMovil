import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from 'react-native';

const winningLines = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6],
];

export default function TicTacToe() {
  const [board, setBoard] = useState(Array(9).fill(null));
  const [player, setPlayer] = useState('X');

  const checkWinner = (newBoard) => {
    for (const [a, b, c] of winningLines) {
      if (
        newBoard[a] &&
        newBoard[a] === newBoard[b] &&
        newBoard[a] === newBoard[c]
      ) {
        return newBoard[a];
      }
    }

    return null;
  };

  const play = (index) => {
    if (board[index]) return;

    const newBoard = [...board];
    newBoard[index] = player;

    const winner = checkWinner(newBoard);

    setBoard(newBoard);

    if (winner) {
      Alert.alert(
        '🎉 ¡Ganador!',
        `El jugador ${winner} ha ganado.`,
        [{ text: 'Nueva partida', onPress: reset }]
      );
      return;
    }

    if (newBoard.every(Boolean)) {
      Alert.alert(
        '🤝 Empate',
        'Nadie ganó esta partida.',
        [{ text: 'Nueva partida', onPress: reset }]
      );
      return;
    }

    setPlayer(player === 'X' ? 'O' : 'X');
  };

  const reset = () => {
    setBoard(Array(9).fill(null));
    setPlayer('X');
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>
        ❌⭕ Tic Tac Toe
      </Text>

      <Text style={styles.turn}>
        Turno del jugador: {player}
      </Text>

      <View style={styles.board}>
        {board.map((value, index) => (
          <TouchableOpacity
            key={index}
            style={styles.cell}
            onPress={() => play(index)}
          >
            <Text
              style={[
                styles.cellText,
                {
                  color:
                    value === 'X'
                      ? '#FF6584'
                      : '#6C63FF',
                },
              ]}
            >
              {value}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      <TouchableOpacity
        style={styles.button}
        onPress={reset}
      >
        <Text style={styles.buttonText}>
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
    paddingTop: 50,
  },

  title: {
    fontSize: 30,
    fontWeight: 'bold',
  },

  turn: {
    fontSize: 18,
    marginTop: 15,
    color: '#666',
  },

  board: {
    width: 315,
    height: 315,
    flexDirection: 'row',
    flexWrap: 'wrap',
    marginTop: 40,
  },

  cell: {
    width: 105,
    height: 105,
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#DDD',
    justifyContent: 'center',
    alignItems: 'center',
  },

  cellText: {
    fontSize: 55,
    fontWeight: 'bold',
  },

  button: {
    backgroundColor: '#6C63FF',
    paddingVertical: 15,
    paddingHorizontal: 40,
    borderRadius: 12,
    marginTop: 35,
  },

  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
