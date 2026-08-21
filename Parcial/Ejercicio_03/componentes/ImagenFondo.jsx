import { StyleSheet, View, Text, ImageBackground, Dimensions, Image} from "react-native" 

export default function ImagenFondo(){
    return(
        <View>
            <ImageBackground
                style={styles.fondo}
                source={require('../assets/Osito.jpg')}
            >
                <View style={styles.container}>
                    <Text style={styles.texto}>Pescado</Text>
                    <Image
                        source={{uri:'https://http.cat/424'}}
                        style={styles.foto}
                    />
                </View>
            </ImageBackground>
        </View>
    );
}

const styles = StyleSheet.create({
    fondo:{
        width: Dimensions.get("window").width,
        height: Dimensions.get("window").height,
    },
    foto:{
        margin:20,
        width:260,
        height:260,
        borderRadius:16,
        borderWidth:10,
        borderColor:'#bbb4bb',
        shadowColor:'#000',
        shadowOffset:{width: 0, height: 6},
        shadowOpacity:0.5,
        shadowRadius:10,
        elevation:8, 
    },
    container:{
        flex: 1,
        justifyContent: "center",
        alignItems:"center",
        backgroundColor:"rgb(0,0,0,0)",
    },
    texto:{
        color:"#e0e0e0",
        fontSize:90,
        justifyContent: "flex-start",
        alignItems:"center",
        width: Dimensions.get("window").width,
        backgroundColor:"rgb(0,0,0,0.5)",
    }
});