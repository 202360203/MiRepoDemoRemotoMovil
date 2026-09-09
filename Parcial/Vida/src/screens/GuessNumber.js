import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from 'react-native';

export default function GuessNumber() {
  const [secret, setSecret] = useState(
    Math.floor(Math.random() * 100) + 1
  );

  const [guess, setGuess] = useState('');
  const [message, setMessage] = useState(
    'Adivina un número del 1 al 100'
  );

  const [attempts, setAttempts] = useState(0);

  const checkGuess = () => {
    const number = Number(guess);

    if (!number || number < 1 || number > 100) {
      setMessage('Ingresa un número válido del 1 al 100');
      return;
    }

    const newAttempts = attempts + 1;
    setAttempts(newAttempts);

    if (number === secret) {
      Alert.alert(
        '🎉 ¡Correcto!',
        `Adivinaste el número en ${newAttempts} intentos.`,
        [{ text: 'Jugar otra vez', onPress: reset }]
      );

      setMessage('¡Encontraste el número!');
    } else if (number < secret) {
      setMessage('📈 El número secreto es MAYOR');
    } else {
      setMessage('📉 El número secreto es MENOR');
    }

    setGuess('');
  };

  const reset = () => {
    setSecret(
      Math.floor(Math.random() * 100) + 1
    );
    setGuess('');
    setAttempts(0);
    setMessage(
      'Adivina un número del 1 al 100'
    );
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>
        🔢 Adivina el número
      </Text>

      <Text style={styles.subtitle}>
        {message}
      </Text>

      <TextInput
        style={styles.input}
        value={guess}
        onChangeText={setGuess}
        keyboardType="numeric"
        placeholder="Escribe un número"
        maxLength={3}
      />

      <TouchableOpacity
        style={styles.button}
        onPress={checkGuess}
      >
        <Text style={styles.buttonText}>
          Adivinar
        </Text>
      </TouchableOpacity>

      <Text style={styles.attempts}>
        Intentos: {attempts}
      </Text>

      <TouchableOpacity
        style={styles.reset}
        onPress={reset}
      >
        <Text style={styles.resetText}>
          Nueva partida
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
    paddingHorizontal: 25,
  },

  title: {
    fontSize: 29,
    fontWeight: 'bold',
    textAlign: 'center',
  },

  subtitle: {
    color: '#666',
    fontSize: 17,
    textAlign: 'center',
    marginTop: 25,
    lineHeight: 25,
  },

  input: {
    width: '90%',
    height: 60,
    backgroundColor: '#fff',
    borderRadius: 15,
    marginTop: 40,
    paddingHorizontal: 20,
    fontSize: 22,
    textAlign: 'center',
    borderWidth: 1,
    borderColor: '#DDD',
  },

  button: {
    backgroundColor: '#6C63FF',
    paddingVertical: 16,
    paddingHorizontal: 55,
    borderRadius: 14,
    marginTop: 20,
  },

  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 17,
  },

  attempts: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 30,
  },

  reset: {
    marginTop: 20,
  },

  resetText: {
    color: '#6C63FF',
    fontWeight: 'bold',
  },
});
        