import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import ImagenFondo from './componentes/ImagenFondo';
import DemoFlatList from './componentes/DemoFlatList'

export default function App() {
  return (
    <View style={styles.container}>
      <ImagenFondo/>
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
