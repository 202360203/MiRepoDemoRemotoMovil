import { Accelerometer } from 'expo-sensors';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import AccelerometerSensor from './components/AccelerometerSensors';
import AccelerometerGame from './components/AccelerometerGame';
import GyroscopeSensor from './components/GyroscopeSensor';
import GyroscopeGame from './components/GyroscopeGame';
import MagnetometerSensor from './components/MagnetometerSensor';
import MagnetometerGame from './components/MagnetometerGame';
import PedometerSensor from './components/PedometerSensor';

export default function App() {
  return (
    <View style={styles.container}>
      <PedometerSensor/>
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
