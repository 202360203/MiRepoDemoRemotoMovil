import React from 'react';
import {
  ScrollView,
  StyleSheet,
} from 'react-native';

import GameCard from '../components/GameCard';
import CustomHeader from '../components/CustomHeader';

export default function GamesScreen({ navigation }) {
  const games = [
    {
      title: 'Dados',
      icon: '🎲',
      description: 'Lanza los dados',
      color: '#FF6584',
      screen: 'Dados',
    },
    {
      title: 'Memorama',
      icon: '🧠',
      description: 'Encuentra las parejas',
      color: '#6C63FF',
      screen: 'Memorama',
    },
    {
      title: 'Tic Tac Toe',
      icon: '❌',
      description: 'Tres en línea',
      color: '#36C5F0',
      screen: 'Tic Tac Toe',
    },
    {
      title: 'Adivina el número',
      icon: '🔢',
      description: 'Adivina el número secreto',
      color: '#FFB84D',
      screen: 'Adivina el número',
    },
  ];

  return (
    <ScrollView style={styles.container}>
      <CustomHeader
        title="🎮 Juegos"
        subtitle="Selecciona un juego para comenzar"
      />

      {games.map((game) => (
        <GameCard
          key={game.title}
          title={game.title}
          icon={game.icon}
          description={game.description}
          color={game.color}
          onPress={() => navigation.navigate(game.screen)}
        />
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F7F7FC',
    paddingHorizontal: 20,
  },
});
