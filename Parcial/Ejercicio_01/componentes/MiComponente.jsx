import { View, Text } from "react-native";
import { StyleSheet } from "react-native";

const MiComponente = () =>{
    return(
        <View>
            <Text style={styles.color_texto}>Hola desde mi componente</Text>
        </View>
    );
}

const styles=StyleSheet.create({
    color_texto:{
        color:'red',
    },
});

export default MiComponente