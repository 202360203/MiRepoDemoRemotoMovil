import { useState, useEffect } from "react";
import { Text, View, StyleSheet } from "react-native";
import { Gyroscope } from "expo-sensors";

export default function GyroscopeGame() {
    const [bola, setBola] = useState({
        x: 150,
        y: 300,
    });

    useEffect(() => {
        const suscripcion = Gyroscope.addListener(({ x, y }) => {
            setBola(posicion => ({
                x: Math.max(0, Math.min(300, posicion.x + x * 10)),
                y: Math.max(0, Math.min(500, posicion.y + y * 10)),
            }));
        });

        Gyroscope.setUpdateInterval(100);

        return () => {
            suscripcion.remove();
        };
    }, []);

    return (
        <View style={styles.container}>
            <Text style={styles.title}>
                Mueve la bola
            </Text>

            <View style={styles.game}>
                <View
                    style={[
                        styles.bola,
                        {
                            left: bola.x,
                            top: bola.y,
                        },
                    ]}
                />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#f6f6f6",
        padding: 20,
    },

    title: {
        fontSize: 28,
        fontWeight: "bold",
        textAlign: "center",
        marginBottom: 20,
        color: "#1e344f",
    },

    game: {
        flex: 1,
        backgroundColor: "#dbeafe",
        borderRadius: 20,
        position: "relative",
        overflow: "hidden",
    },

    bola: {
        position: "absolute",
        width: 40,
        height: 40,
        borderRadius: 20,
        backgroundColor: "#2564eb",
    },
});
