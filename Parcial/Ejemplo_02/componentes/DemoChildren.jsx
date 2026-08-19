import { View, Text } from "react-native";

export default function DemoChildren({children}){
    return(
        <View>
            <Text>{titulo}</Text>
            <Text>Muestra Children:</Text> {children}
        </View>
    );
}