import { useState, useEffect } from "react";
import { Text, View, StyleSheet } from "react-native";
import { Magnetometer } from "expo-sensors";

export default function MagnetometerGame() {
    const [angulo, setAngulo] = useState(0);

    useEffect(() => {
        const suscripcion = Magnetometer.addListener(({ x, y }) => {
            // Calcular el ángulo respecto al norte
            let nuevoAngulo = Math.atan2(y, x) * (180 / Math.PI);

            // Convertir el ángulo a 0 - 360 grados
            nuevoAngulo = nuevoAngulo >= 0
                ? nuevoAngulo
                : nuevoAngulo + 360;

            setAngulo(nuevoAngulo);
        });

        Magnetometer.setUpdateInterval(100);

        return () => {
            suscripcion.remove();
        };
    }, []);

    return (
        <View style={styles.container}>
            <Text style={styles.title}>
                🧭 Brújula
            </Text>

            <View style={styles.compass}>
                {/* Puntos cardinales */}
                <Text style={[styles.direction, styles.norte]}>
                    N
                </Text>

                <Text style={[styles.direction, styles.este]}>
                    E
                </Text>

                <Text style={[styles.direction, styles.sur]}>
                    S
                </Text>

                <Text style={[styles.direction, styles.oeste]}>
                    O
                </Text>

                {/* Aguja */}
                <View
                    style={[
                        styles.aguja,
                        {
                            transform: [
                                { rotate: `${angulo}deg` }
                            ],
                        },
                    ]}
                >
                    <View style={styles.punta} />
                </View>

                {/* Centro */}
                <View style={styles.centro} />
            </View>

            <Text style={styles.grados}>
                {angulo.toFixed(0)}°
            </Text>

            <Text style={styles.direccion}>
                {getDireccion(angulo)}
            </Text>
        </View>
    );
}

function getDireccion(angulo) {
    if (angulo >= 337.5 || angulo < 22.5) {
        return "Norte";
    }

    if (angulo >= 22.5 && angulo < 67.5) {
        return "Noreste";
    }

    if (angulo >= 67.5 && angulo < 112.5) {
        return "Este";
    }

    if (angulo >= 112.5 && angulo < 157.5) {
        return "Sureste";
    }

    if (angulo >= 157.5 && angulo < 202.5) {
        return "Sur";
    }

    if (angulo >= 202.5 && angulo < 247.5) {
        return "Suroeste";
    }

    if (angulo >= 247.5 && angulo < 292.5) {
        return "Oeste";
    }

    return "Noroeste";
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f6f6f6",
    },

    title: {
        fontSize: 30,
        fontWeight: "bold",
        color: "#1e344f",
        marginBottom: 30,
    },

    compass: {
        width: 280,
        height: 280,
        borderRadius: 140,
        backgroundColor: "#fff",
        borderWidth: 5,
        borderColor: "#2563eb",
        justifyContent: "center",
        alignItems: "center",
        position: "relative",
    },

    direction: {
        position: "absolute",
        fontSize: 24,
        fontWeight: "bold",
        color: "#1e344f",
    },

    norte: {
        top: 15,
    },

    este: {
        right: 20,
    },

    sur: {
        bottom: 15,
    },

    oeste: {
        left: 20,
    },

    aguja: {
        width: 8,
        height: 210,
        position: "absolute",
        justifyContent: "flex-start",
        alignItems: "center",
    },

    punta: {
        width: 0,
        height: 0,
        borderLeftWidth: 12,
        borderRightWidth: 12,
        borderBottomWidth: 70,
        borderLeftColor: "transparent",
        borderRightColor: "transparent",
        borderBottomColor: "#ef4444",
    },

    centro: {
        width: 20,
        height: 20,
        borderRadius: 10,
        backgroundColor: "#1e344f",
        position: "absolute",
    },

    grados: {
        fontSize: 35,
        fontWeight: "bold",
        color: "#2563eb",
        marginTop: 30,
    },

    direccion: {
        fontSize: 22,
        fontWeight: "bold",
        color: "#1e344f",
        marginTop: 5,
    },
});
