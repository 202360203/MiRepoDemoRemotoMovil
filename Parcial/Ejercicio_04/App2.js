import { StatusBar } from 'expo-status-bar';
import { useState } from 'react';
import { Button } from 'react-native';
import { Modal } from 'react-native';
import { StyleSheet, Text, View } from 'react-native';

export default function App2() {
  const [modal, setModal]=useState(false);
  return (
    <View style={styles.container}>
      <Modal
        animationType='slide'
        transparent={true}
        visible={modal}
      >
        <View style={styles.containerModal}>
          <View style={styles.viewModal}>
            <Text>Esto esta dentro del modal</Text>
            <Button
              title="Cerrar Modal"
              onPress={()=>setModal(!modal)}
            />
          </View>
        </View>
      </Modal>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Text>Este texto esta fuera del modal</Text>
      <Button
        title="Mostrar Modal"
        onPress={()=>setModal(!modal)}
      />
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
  containerModal:{
    flex:1,
    alignItems:'stretch',
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.5)'
  },
  viewModal: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    marginHorizontal: 20,
    marginVertical: 30,
  },
});
