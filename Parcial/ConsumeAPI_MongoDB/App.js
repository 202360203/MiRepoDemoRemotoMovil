import React, { useState, useEffect } from 'react';
import {
  ActivityIndicator,
  FlatList,
  Image,
  Modal,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

export default function App() {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);

  // Estados para la sesión y el modal
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [selectedMovie, setSelectedMovie] = useState(null);

  useEffect(() => {
    fetch("http://localhost:4000/movies")
      .then((res) => res.json())
      .then((data) => {
        setMovies(data);
        setLoading(false);
      })
      .catch((error) => console.error(error));
  }, []);

  const handleLogin = () => {
    if (email.trim() !== '' && password.trim() !== '') {
      setIsLoggedIn(true);
    } else {
      alert("Por favor ingresa un correo y contraseña");
    }
  };

  if (loading) {
    return (
      <View style={styles.loader}>
        <ActivityIndicator size="large" color="#007aff" />
      </View>
    );
  }

  // Si no está autenticado, mostramos la pantalla de Login
  if (!isLoggedIn) {
    return (
      <View style={styles.loginContainer}>
        <Text style={styles.loginTitle}>Iniciar Sesión</Text>
        <TextInput
          style={styles.input}
          placeholder="Correo electrónico"
          value={email}
          onChangeText={setEmail}
          keyboardType="email-address"
          autoCapitalize="none"
        />
        <TextInput
          style={styles.input}
          placeholder="Contraseña"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
        />
        <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
          <Text style={styles.loginButtonText}>Ingresar</Text>
        </TouchableOpacity>
      </View>
    );
  }

  // Render de cada tarjeta de película
  const renderItem = ({ item }) => (
    <View style={styles.card}>
      {/* Al presionar el póster, abrimos el modal pasándole la película activa */}
      <TouchableOpacity onPress={() => setSelectedMovie(item)}>
        {item.poster ? (
          <Image source={{ uri: item.poster }} style={styles.poster} />
        ) : (
          <View style={[styles.poster, styles.noposter]}>
            <Text style={styles.noposterText}>No Image</Text>
          </View>
        )}
      </TouchableOpacity>
      <View style={styles.info}>
        <Text style={styles.title}>{item.title}</Text>
        <Text style={styles.plot} numberOfLines={3}>
          {item.fullplot || "Sin descripción"}
        </Text>
      </View>
    </View>
  );

  return (
    <View style={styles.container}>
      {/* Botón superior para cerrar sesión opcional */}
      <View style={styles.header}>
        <TouchableOpacity onPress={() => setIsLoggedIn(false)}>
          <Text style={styles.logoutText}>Cerrar Sesión</Text>
        </TouchableOpacity>
      </View>

      <FlatList
        data={movies}
        keyExtractor={(item) => item._id}
        renderItem={renderItem}
      />

      {/* Modal con detalles del póster */}
      <Modal
        visible={selectedMovie !== null}
        animationType="slide"
        transparent={true}
        onRequestClose={() => setSelectedMovie(null)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            {selectedMovie?.poster && (
              <Image source={{ uri: selectedMovie.poster }} style={styles.modalPoster} />
            )}
            <Text style={styles.modalTitle}>{selectedMovie?.title}</Text>
            <Text style={styles.modalPlot}>{selectedMovie?.fullplot || "Sin descripción disponible."}</Text>

            <TouchableOpacity style={styles.closeButton} onPress={() => setSelectedMovie(null)}>
              <Text style={styles.closeButtonText}>Cerrar</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 40,
    backgroundColor: '#f2f2f2',
  },
  loader: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center"
  },
  header: {
    alignItems: 'flex-end',
    paddingHorizontal: 20,
    paddingBottom: 10,
  },
  logoutText: {
    color: '#007aff',
    fontWeight: 'bold',
  },
  // Login Styles
  loginContainer: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 30,
    backgroundColor: '#ffffff',
  },
  loginTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    height: 50,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    marginBottom: 15,
  },
  loginButton: {
    backgroundColor: '#007aff',
    height: 50,
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
  },
  loginButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
  // Card Styles
  card: {
    flexDirection: "row",
    padding: 15,
    marginHorizontal: 15,
    marginVertical: 8,
    backgroundColor: "#ffffff",
    borderRadius: 10,
    elevation: 2, // Sombra para Android
  },
  poster: {
    width: 90,
    height: 130,
    borderRadius: 8,
  },
  noposter: {
    backgroundColor: "#ddd",
    justifyContent: "center",
    alignItems: "center",
  },
  noposterText: {
    fontSize: 12,
    color: "#666",
  },
  info: {
    flex: 1,
    marginLeft: 15,
  },
  title: {
    fontSize: 16,
    fontWeight: "bold",
    marginBottom: 6
  },
  plot: {
    fontSize: 12,
    color: "gray"
  },
  // Modal Styles
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.6)',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  modalContent: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 20,
    width: '90%',
    alignItems: 'center',
    maxHeight: '80%',
  },
  modalPoster: {
    width: 150,
    height: 220,
    borderRadius: 8,
    marginBottom: 15,
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  modalPlot: {
    fontSize: 14,
    color: '#444',
    textAlign: 'center',
    marginBottom: 20,
  },
  closeButton: {
    backgroundColor: '#007aff',
    paddingHorizontal: 25,
    paddingVertical: 10,
    borderRadius: 6,
  },
  closeButtonText: {
    color: '#fff',
    fontWeight: 'bold',
  },
});