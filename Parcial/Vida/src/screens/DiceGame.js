import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
} from 'react-native';

export default function DiceGame() {
  const [dice1, setDice1] = useState(1);
  const [dice2, setDice2] = useState(6);
  const [rolling, setRolling] = useState(false);

  const rollDice = () => {
    setRolling(true);

    setTimeout(() => {
      setDice1(Math.floor(Math.random() * 6) + 1);
      setDice2(Math.floor(Math.random() * 6) + 1);
      setRolling(false);
    }, 500);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>🎲 Dados</Text>

      <Text style={styles.subtitle}>
        Lanza los dados y prueba tu suerte
      </Text>

      <View style={styles.dices}>
        <View style={styles.dice}>
          <Text style={styles.diceText}>
            {rolling ? '🎲' : dice1}
          </Text>
        </View>

        <View style={styles.dice}>
          <Text style={styles.diceText}>
            {rolling ? '🎲' : dice2}
          </Text>
        </View>
      </View>

      <Text style={styles.total}>
        Total: {dice1 + dice2}
      </Text>

      <TouchableOpacity
        style={styles.button}
        onPress={rollDice}
        disabled={rolling}
      >
        <Text style={styles.buttonText}>
          {rolling ? 'Lanzando...' : 'Lanzar dados'}
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
    fontSize: 32,
    fontWeight: 'bold',
  },

  subtitle: {
    color: '#777',
    marginTop: 8,
  },

  dices: {
    flexDirection: 'row',
    gap: 20,
    marginTop: 60,
  },

  dice: {
    width: 120,
    height: 120,
    backgroundColor: '#fff',
    borderRadius: 20,
    justifyContent: 'center',
    alignItems: 'center',

    elevation: 5,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },

  diceText: {
    fontSize: 65,
    fontWeight: 'bold',
  },

  total: {
    fontSize: 25,
    fontWeight: 'bold',
    marginTop: 35,
  },

  button: {
    backgroundColor: '#6C63FF',
    paddingVertical: 16,
    paddingHorizontal: 45,
    borderRadius: 15,
    marginTop: 30,
  },

  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 17,
  },
});
