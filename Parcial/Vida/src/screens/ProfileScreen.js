import React from 'react';
import {
  View,
  Text,
  StyleSheet,
} from 'react-native';

export default function ProfileScreen() {
  return (
    <View style={styles.container}>
      <View style={styles.avatar}>
        <Text style={styles.avatarText}>👤</Text>
      </View>

      <Text style={styles.name}>Jugador</Text>

      <Text style={styles.email}>
        Bienvenido a GameHub
      </Text>

      <View style={styles.card}>
        <Text style={styles.cardTitle}>
          🎮 Tu perfil
        </Text>

        <Text style={styles.cardText}>
          Aquí podrás consultar tu información y
          próximamente tus estadísticas de juego.
        </Text>
      </View>
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

  avatar: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: '#6C63FF',
    justifyContent: 'center',
    alignItems: 'center',
  },

  avatarText: {
    fontSize: 50,
  },

  name: {
    fontSize: 25,
    fontWeight: 'bold',
    marginTop: 15,
    color: '#222',
  },

  email: {
    color: '#777',
    marginTop: 5,
  },

  card: {
    backgroundColor: '#fff',
    margin: 25,
    padding: 20,
    borderRadius: 18,
    width: '85%',
  },

  cardTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
  },

  cardText: {
    color: '#777',
    lineHeight: 21,
  },
});
