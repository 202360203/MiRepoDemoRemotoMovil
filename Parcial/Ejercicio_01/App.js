import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import MiComponente from './componentes/MiComponente';
import Mensaje from './componentes/Mensaje';

export default function App() {
  return (
    <View style={styles.container}>
      <Mensaje titulo="Hola desde una propiedad " numero="25" color="black" size={15}/>
      <Mensaje titulo="Bienvenido al curso ReactNative " numero="30" color="blue" size={40}/>
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
