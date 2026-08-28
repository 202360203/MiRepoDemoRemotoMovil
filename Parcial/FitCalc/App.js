import React, { useState } from "react";
import { Text, StyleSheet, Button, View, SafeAreaView, TextInput } from "react-native";
import CustomModal from "./componentes/CustomModal";

export default function App(){
    const [modalVisible, setModalVisible]=useState(false);
    const [peso, setPeso] = useState("")
    const [altura, setAltura] = useState("")
    const IMC = peso / (altura*altura)
    const Presionado = () => {
        setModalVisible(true)
    }
    let mensaje = ""
    if (IMC < 18.5) {
        mensaje = "Estas desnutrido"
      } else if (IMC == 18.5 && IMC <= 24.9) {
          mensaje = "Estas bien"                  
        } else if (IMC == 25 && IMC <= 29.9) {
          mensaje = "Estas gordito"
        } else if (IMC == 30 && IMC <= 34.9) {
          mensaje = "Estas gordito 1"
        } else if (IMC == 35 && IMC <= 39.9) {
          mensaje = "Estas gordito 2"
        } else if (IMC >= 40) {
          mensaje = "Estas gordito 3"
        }
    return(
        <SafeAreaView style={styles.container}>
            <View>
                <CustomModal
                    visible={modalVisible}
                    onClose={()=>setModalVisible(false)}
                    contenido={IMC}
                    objetocontenido={mensaje}
                />
                <TextInput
                    placeholder="Peso en kg"
                    value = {peso}
                    onChangeText={ t => setPeso(t)}
                />
                <TextInput
                    placeholder="Altura en metros"
                    value = {altura}
                    onChangeText={ t => setAltura(t)}
                />
                <Button 
                    title="Calcular IMC"
                    onPress={ Presionado }
                />
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
