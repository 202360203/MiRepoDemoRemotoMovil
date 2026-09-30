import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Animated, Text, View, Modal, Button, FlatList, TouchableOpacity, ScrollView, Dimensions, SectionList, ImageBackground, Image, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createDrawerNavigator } from '@react-navigation/drawer';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
