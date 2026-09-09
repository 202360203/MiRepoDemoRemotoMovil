import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';

import TabNavigator from './TabNavigator';
import GamesScreen from '../screens/GamesScreen';
import DiceGame from '../screens/DiceGame';
import MemoryGame from '../screens/MemoryGame';
import TicTacToe from '../screens/TicTacToe';
import GuessNumber from '../screens/GuessNumber';

const Drawer = createDrawerNavigator();

export default function AppNavigator() {
  return (
    <Drawer.Navigator
      screenOptions={{
        headerStyle: {
          backgroundColor: '#6C63FF',
        },
        headerTintColor: '#fff',
        headerTitleStyle: {
          fontWeight: 'bold',
        },
        drawerActiveTintColor: '#6C63FF',
        drawerLabelStyle: {
          fontSize: 16,
        },
      }}
    >
      <Drawer.Screen
        name="Inicio"
        component={TabNavigator}
        options={{
          title: 'GameHub',
        }}
      />

      <Drawer.Screen
        name="Todos los juegos"
        component={GamesScreen}
        options={{
          title: 'Juegos',
        }}
      />

      <Drawer.Screen
        name="Dados"
        component={DiceGame}
      />

      <Drawer.Screen
        name="Memorama"
        component={MemoryGame}
      />

      <Drawer.Screen
        name="Tic Tac Toe"
        component={TicTacToe}
      />

      <Drawer.Screen
        name="Adivina el número"
        component={GuessNumber}
      />
    </Drawer.Navigator>
  );
}
