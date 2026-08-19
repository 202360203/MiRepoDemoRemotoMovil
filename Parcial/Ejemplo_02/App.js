import { View, Text, TextInput, Button, ScrollView, StyleSheet, Dimensions } from 'react-native';
import { useState } from "react";
import FlagComponent from './componentes/FlagComponent';

export default function App() {
  const [texto, setTexto] = useState()
  const [enviar, setEnviar] = useState()
  return (
    <View style={styles.container}>
      <View style={styles.panel1}></View>
      <View style={styles.panel2}>
        <ScrollView style={styles.input}>
            <Text>Inicial {enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>{enviar}</Text>
            <Text>Final {enviar}</Text>
        </ScrollView>  
      </View>      
      <View style={styles.panel3}>
        <TextInput
          placeholder="Escribe aqui..."
          onChangeText={t=>setTexto(t)}
        />
        <Button
          title="Enviar"
          onPress={()=>setEnviar(texto)}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#1a74e9',
  },
  panel1:{
    flex: 1,
    backgroundColor:'#f079c2'
  },
  panel2:{
    flex: 1,
    backgroundColor:'#42d325'
  },
  panel3:{
    flex: 1,
    backgroundColor:'#2dffe3'
  },
});
