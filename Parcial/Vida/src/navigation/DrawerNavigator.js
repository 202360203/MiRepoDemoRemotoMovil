import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';

import TabNavigator from './TabNavigator';
import GamesScreen from '../screens/GamesScreen';

const Drawer = createDrawerNavigator();

export default function DrawerNavigator() {
  return (
    <Drawer.Navigator
      screenOptions={{
        headerStyle: {
          backgroundColor: '#6C63FF',
        },
        headerTintColor: '#fff',
        drawerActiveTintColor: '#6C63FF',
      }}
    >
      <Drawer.Screen name="Inicio" component={TabNavigator} />
      <Drawer.Screen name="Juegos" component={GamesScreen} />
    </Drawer.Navigator>
  );
}
