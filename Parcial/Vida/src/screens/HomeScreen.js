import React from 'react';
import {
  ScrollView,
  View,
  Text,
  StyleSheet,
} from 'react-native';

import GameCard from '../components/GameCard';
import CustomHeader from '../components/CustomHeader';

export default function HomeScreen({ navigation }) {
  const games = [
    {
      title: 'Dados',
      icon: '🎲',
      description: 'Lanza los dados y prueba tu suerte',
      color: '#FF6584',
      screen: 'Dados',
    },
    {
      title: 'Memorama',
      icon: '🧠',
      description: 'Encuentra todas las parejas',
      color: '#6C63FF',
      screen: 'Memorama',
    },
    {
      title: 'Tic Tac Toe',
      icon: '❌',
      description: 'Enfréntate a otro jugador',
      color: '#36C5F0',
      screen: 'Tic Tac Toe',
    },
    {
      title: 'Adivina el número',
      icon: '🔢',
      description: 'Encuentra el número secreto',
      color: '#FFB84D',
      screen: 'Adivina el número',
    },
  ];

  return (
    <ScrollView style={styles.container}>
      <CustomHeader
        title="¡Hola! 👋"
        subtitle="¿A qué quieres jugar hoy?"
      />

      <View style={styles.banner}>
        <Text style={styles.bannerEmoji}>🎮</Text>

        <View style={styles.bannerInfo}>
          <Text style={styles.bannerTitle}>
            Bienvenido a GameHub
          </Text>

          <Text style={styles.bannerText}>
            Elige uno de nuestros juegos y comienza a divertirte.
          </Text>
        </View>
      </View>

      <Text style={styles.sectionTitle}>
        Juegos disponibles
      </Text>

      <View style={styles.games}>
        {games.map((game) => (
          <GameCard
            key={game.title}
            title={game.title}
            icon={game.icon}
            description={game.description}
            color={game.color}
            onPress={() =>
              navigation
                .getParent()
                ?.navigate(game.screen)
            }
          />
        ))}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F7F7FC',
  },

  banner: {
    marginHorizontal: 20,
    backgroundColor: '#6C63FF',
    borderRadius: 20,
    padding: 20,
    flexDirection: 'row',
    alignItems: 'center',
  },

  bannerEmoji: {
    fontSize: 45,
    marginRight: 15,
  },

  bannerInfo: {
    flex: 1,
  },

  bannerTitle: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 18,
    marginBottom: 5,
  },

  bannerText: {
    color: '#E9E7FF',
    fontSize: 13,
    lineHeight: 19,
  },

  sectionTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginHorizontal: 20,
    marginTop: 25,
    marginBottom: 15,
    color: '#222',
  },

  games: {
    paddingHorizontal: 20,
    paddingBottom: 30,
  },
});
