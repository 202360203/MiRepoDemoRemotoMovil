import { useState, useEffect } from "react";
import { Text, View, StyleSheet } from "react-native";
import { Accelerometer } from "expo-sensors";

export default function AccelerometerGame() {
    const [dado1, setDado1] = useState(1);
    const [dado2, setDado2] = useState(1);

    useEffect(() => {
        const suscripcion = Accelerometer.addListener(({ x, y, z }) => {
            const movimiento =
                Math.abs(x) + Math.abs(y) + Math.abs(z);

            // Detectar cuando se sacude el celular
            if (movimiento > 2.5) {
                setDado1(Math.floor(Math.random() * 6) + 1);
                setDado2(Math.floor(Math.random() * 6) + 1);
            }
        });

        Accelerometer.setUpdateInterval(200);

        return () => {
            suscripcion.remove();
        };
    }, []);

    return (
        <View style={styles.container}>
            <Text style={styles.title}>
                🎲 Dados
            </Text>

            <Text style={styles.instructions}>
                ¡Sacude tu celular!
            </Text>

            <View style={styles.dados}>
                <View style={styles.dado}>
                    <Text style={styles.numero}>
                        {dado1}
                    </Text>
                </View>

                <View style={styles.dado}>
                    <Text style={styles.numero}>
                        {dado2}
                    </Text>
                </View>
            </View>

            <Text style={styles.resultado}>
                Resultado: {dado1 + dado2}
            </Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f6f6f6",
    },

    title: {
        fontSize: 32,
        fontWeight: "bold",
        color: "#1e344f",
    },

    instructions: {
        fontSize: 18,
        marginTop: 10,
        marginBottom: 40,
        color: "#555",
    },

    dados: {
        flexDirection: "row",
        gap: 20,
    },

    dado: {
        width: 100,
        height: 100,
        backgroundColor: "#fff",
        borderRadius: 15,
        justifyContent: "center",
        alignItems: "center",
        borderWidth: 2,
        borderColor: "#2563eb",
    },

    numero: {
        fontSize: 50,
        fontWeight: "bold",
        color: "#2563eb",
    },

    resultado: {
        fontSize: 24,
        fontWeight: "bold",
        marginTop: 40,
        color: "#1e344f",
    },
});
