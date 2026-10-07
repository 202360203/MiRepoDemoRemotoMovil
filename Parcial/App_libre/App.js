import { useEffect, useRef, useState } from 'react';

import {
  StyleSheet,
  Animated,
  Text,
  View,
  Modal,
  FlatList,
  TouchableOpacity,
  ScrollView,
  Image,
  TextInput,
  Alert,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';

import { StatusBar } from 'expo-status-bar';

import AsyncStorage from '@react-native-async-storage/async-storage';
import * as ImagePicker from 'expo-image-picker';

import { NavigationContainer } from '@react-navigation/native';
import { createDrawerNavigator } from '@react-navigation/drawer';


// --------------------------------------------------
// CONFIGURACIÓN
// --------------------------------------------------

const Drawer = createDrawerNavigator();

const STORAGE_KEY = '@mi_recetario_recetas';

const colores = {
  fondo: '#F8F5EE',
  tarjeta: '#FFFFFF',
  principal: '#527653',
  oscuro: '#283B2B',
  texto: '#393D35',
  secundario: '#77796F',
  borde: '#E4E3D9',
  peligro: '#B94A48',
};


// --------------------------------------------------
// PANTALLA DE CARGA
// --------------------------------------------------

function LoadingScreen() {
  const escala = useRef(new Animated.Value(0.7)).current;
  const opacidad = useRef(new Animated.Value(0)).current;
  const rotacion = useRef(new Animated.Value(0)).current;

  useEffect(() => {

    // Animación de escala
    Animated.loop(
      Animated.sequence([
        Animated.timing(escala, {
          toValue: 1,
          duration: 800,
          useNativeDriver: true,
        }),

        Animated.timing(escala, {
          toValue: 0.7,
          duration: 800,
          useNativeDriver: true,
        }),
      ])
    ).start();


    // Aparición
    Animated.timing(opacidad, {
      toValue: 1,
      duration: 1000,
      useNativeDriver: true,
    }).start();


    // Rotación
    Animated.loop(
      Animated.timing(rotacion, {
        toValue: 1,
        duration: 2500,
        useNativeDriver: true,
      })
    ).start();

  }, []);


  const giro = rotacion.interpolate({
    inputRange: [0, 1],
    outputRange: ['0deg', '360deg'],
  });


  return (
    <View style={styles.loadingContainer}>

      <Animated.View
        style={{
          opacity: opacidad,
          transform: [
            { scale: escala },
            { rotate: giro },
          ],
        }}
      >
        <Text style={styles.loadingEmoji}>
          🍲
        </Text>
      </Animated.View>


      <Animated.Text
        style={[
          styles.loadingTitle,
          {
            opacity: opacidad,
          },
        ]}
      >
        Mi Recetario
      </Animated.Text>


      <Animated.Text
        style={[
          styles.loadingText,
          {
            opacity: opacidad,
          },
        ]}
      >
        Preparando tus recetas...
      </Animated.Text>

    </View>
  );
}


// --------------------------------------------------
// APP PRINCIPAL
// --------------------------------------------------

export default function App() {

  const [recetas, setRecetas] = useState([]);

  const [cargando, setCargando] = useState(true);

  const [recetaSeleccionada, setRecetaSeleccionada] =
    useState(null);

  const [modalVisible, setModalVisible] =
    useState(false);


  // Cargar recetas al iniciar
  useEffect(() => {
    cargarRecetas();
  }, []);


  async function cargarRecetas() {

    try {

      const datos =
        await AsyncStorage.getItem(STORAGE_KEY);

      if (datos) {
        setRecetas(JSON.parse(datos));
      }

    } catch (error) {

      Alert.alert(
        'Error',
        'No se pudieron cargar las recetas.'
      );

    } finally {

      setCargando(false);

    }
  }


  // --------------------------------------------------
  // GUARDAR RECETAS
  // --------------------------------------------------

  async function guardarRecetas(nuevasRecetas) {

    try {

      await AsyncStorage.setItem(
        STORAGE_KEY,
        JSON.stringify(nuevasRecetas)
      );

      setRecetas(nuevasRecetas);

      return true;

    } catch (error) {

      Alert.alert(
        'Error',
        'No se pudo guardar la receta.'
      );

      return false;
    }
  }


  // --------------------------------------------------
  // AGREGAR RECETA
  // --------------------------------------------------

  async function agregarReceta(
    receta,
    navigation
  ) {

    const nuevasRecetas = [
      receta,
      ...recetas,
    ];

    const guardada =
      await guardarRecetas(nuevasRecetas);


    if (guardada) {

      Alert.alert(
        '¡Receta guardada!',
        'La receta se agregó correctamente a tu recetario.'
      );

      navigation.navigate('Inicio');
    }
  }


  // --------------------------------------------------
  // ELIMINAR RECETA
  // --------------------------------------------------

  function eliminarReceta(id) {

    Alert.alert(
      'Eliminar receta',
      '¿Seguro que quieres eliminar esta receta?',

      [
        {
          text: 'Cancelar',
          style: 'cancel',
        },

        {
          text: 'Eliminar',
          style: 'destructive',

          onPress: async () => {

            const nuevasRecetas =
              recetas.filter(
                (receta) =>
                  receta.id !== id
              );

            const eliminada =
              await guardarRecetas(nuevasRecetas);


            if (eliminada) {

              setModalVisible(false);

              setRecetaSeleccionada(null);
            }
          },
        },
      ]
    );
  }


  // --------------------------------------------------
  // ABRIR RECETA
  // --------------------------------------------------

  function abrirReceta(receta) {

    setRecetaSeleccionada(receta);

    setModalVisible(true);
  }


  // --------------------------------------------------
  // PANTALLA DE CARGA
  // --------------------------------------------------

  if (cargando) {
    return <LoadingScreen />;
  }


  // --------------------------------------------------
  // INTERFAZ PRINCIPAL
  // --------------------------------------------------

  return (

    <NavigationContainer>

      <StatusBar style="dark" />


      <Drawer.Navigator
        screenOptions={{

          headerStyle: {
            backgroundColor: colores.fondo,
          },

          headerTintColor:
            colores.oscuro,

          drawerStyle: {
            backgroundColor:
              colores.fondo,
          },

          drawerActiveTintColor:
            colores.principal,

          drawerInactiveTintColor:
            colores.texto,

          sceneStyle: {
            backgroundColor:
              colores.fondo,
          },

        }}
      >

        <Drawer.Screen name="Inicio">

          {(props) => (

            <InicioScreen
              {...props}

              recetas={recetas}

              abrirReceta={abrirReceta}
            />

          )}

        </Drawer.Screen>


        <Drawer.Screen name="Nueva receta">

          {(props) => (

            <NuevaRecetaScreen
              {...props}

              onGuardar={agregarReceta}
            />

          )}

        </Drawer.Screen>

      </Drawer.Navigator>


      {/* -------------------------------------------
          MODAL DETALLE DE RECETA
      -------------------------------------------- */}

      <Modal
        visible={modalVisible}
        animationType="slide"
        onRequestClose={() =>
          setModalVisible(false)
        }
      >

        <View style={styles.modal}>

          <ScrollView>

            <TouchableOpacity
              style={styles.botonVolver}

              onPress={() =>
                setModalVisible(false)
              }
            >

              <Text
                style={styles.textoBotonVolver}
              >
                ← Volver al recetario
              </Text>

            </TouchableOpacity>


            {recetaSeleccionada && (

              <>

                {/* FOTO */}

                {recetaSeleccionada.foto ? (

                  <Image
                    source={{
                      uri: recetaSeleccionada.foto,
                    }}

                    style={styles.fotoDetalle}
                  />

                ) : null}


                {/* NOMBRE */}

                <Text
                  style={styles.tituloDetalle}
                >
                  {recetaSeleccionada.nombre}
                </Text>


                {/* DESCRIPCIÓN */}

                {recetaSeleccionada.descripcion ? (

                  <Text
                    style={styles.descripcion}
                  >
                    {recetaSeleccionada.descripcion}
                  </Text>

                ) : null}


                {/* INGREDIENTES */}

                <Text
                  style={styles.subtitulo}
                >
                  🥕 Ingredientes
                </Text>


                {recetaSeleccionada.ingredientes.map(
                  (ingrediente, indice) => (

                    <Text
                      key={indice}
                      style={styles.elementoLista}
                    >
                      • {ingrediente}
                    </Text>

                  )
                )}


                {/* PREPARACIÓN */}

                <Text
                  style={styles.subtitulo}
                >
                  👨‍🍳 Preparación
                </Text>


                {recetaSeleccionada.pasos.map(
                  (paso, indice) => (

                    <View
                      key={indice}
                      style={styles.pasoDetalle}
                    >

                      <Text
                        style={styles.numeroPaso}
                      >
                        {indice + 1}
                      </Text>


                      <Text
                        style={styles.textoPaso}
                      >
                        {paso}
                      </Text>

                    </View>

                  )
                )}


                {/* ELIMINAR */}

                <TouchableOpacity
                  style={styles.botonEliminar}

                  onPress={() =>
                    eliminarReceta(
                      recetaSeleccionada.id
                    )
                  }
                >

                  <Text
                    style={styles.textoBoton}
                  >
                    Eliminar receta
                  </Text>

                </TouchableOpacity>

              </>

            )}

          </ScrollView>

        </View>

      </Modal>

    </NavigationContainer>
  );
}


// ==================================================
// PANTALLA INICIO
// ==================================================

function InicioScreen({
  navigation,
  recetas,
  abrirReceta,
}) {

  return (

    <View style={styles.contenedor}>

      <Text style={styles.etiqueta}>
        TU LIBRO DE COCINA
      </Text>


      <Text style={styles.titulo}>
        Mis recetas 🍃
      </Text>


      <Text style={styles.descripcion}>
        Guarda tus platillos favoritos y
        vuelve a prepararlos cuando quieras.
      </Text>


      {/* BOTÓN NUEVA RECETA */}

      <TouchableOpacity
        style={styles.botonPrincipal}

        onPress={() =>
          navigation.navigate('Nueva receta')
        }
      >

        <Text style={styles.textoBoton}>
          + Agregar receta
        </Text>

      </TouchableOpacity>


      {/* CONTADOR */}

      <Text style={styles.subtitulo}>
        Recetas guardadas ({recetas.length})
      </Text>


      {/* LISTA */}

      <FlatList
        data={recetas}

        keyExtractor={(item) =>
          item.id
        }

        contentContainerStyle={
          styles.lista
        }


        ListEmptyComponent={

          <View style={styles.vacio}>

            <Text
              style={styles.emojiVacio}
            >
              🍲
            </Text>


            <Text
              style={styles.textoVacio}
            >
              Todavía no tienes recetas.
            </Text>


            <Text
              style={styles.descripcion}
            >
              ¡Agrega tu primera creación!
            </Text>

          </View>

        }


        renderItem={({ item }) => (

          <TouchableOpacity
            style={styles.tarjeta}

            onPress={() =>
              abrirReceta(item)
            }
          >

            {/* FOTO */}

            {item.foto ? (

              <Image
                source={{
                  uri: item.foto,
                }}

                style={
                  styles.fotoTarjeta
                }
              />

            ) : (

              <View
                style={styles.fotoVacia}
              >

                <Text
                  style={{
                    fontSize: 32,
                  }}
                >
                  🍽️
                </Text>

              </View>

            )}


            {/* INFORMACIÓN */}

            <View
              style={styles.infoTarjeta}
            >

              <Text
                style={
                  styles.nombreTarjeta
                }
              >
                {item.nombre}
              </Text>


              <Text
                style={
                  styles.descripcionTarjeta
                }

                numberOfLines={2}
              >
                {item.descripcion ||
                  'Una receta para tu colección'}
              </Text>


              <Text
                style={styles.enlace}
              >
                Ver receta →
              </Text>

            </View>

          </TouchableOpacity>

        )}

      />

    </View>
  );
}


// ==================================================
// NUEVA RECETA
// ==================================================

function NuevaRecetaScreen({
  navigation,
  onGuardar,
}) {

  const [nombre, setNombre] =
    useState('');

  const [descripcion, setDescripcion] =
    useState('');

  const [ingredientes, setIngredientes] =
    useState(['']);

  const [pasos, setPasos] =
    useState(['']);

  const [foto, setFoto] =
    useState(null);


  // --------------------------------------------------
  // TOMAR FOTO
  // --------------------------------------------------

  async function tomarFoto() {

    const permiso =
      await ImagePicker
        .requestCameraPermissionsAsync();


    if (!permiso.granted) {

      Alert.alert(
        'Permiso necesario',
        'Necesitas permitir el acceso a la cámara.'
      );

      return;
    }


    const resultado =
      await ImagePicker.launchCameraAsync({

        mediaTypes: ['images'],

        allowsEditing: true,

        quality: 0.7,

      });


    if (!resultado.canceled) {

      setFoto(
        resultado.assets[0].uri
      );
    }
  }


  // --------------------------------------------------
  // GALERÍA
  // --------------------------------------------------

  async function elegirFoto() {

    const resultado =
      await ImagePicker
        .launchImageLibraryAsync({

          mediaTypes: ['images'],

          allowsEditing: true,

          quality: 0.7,

        });


    if (!resultado.canceled) {

      setFoto(
        resultado.assets[0].uri
      );
    }
  }


  // --------------------------------------------------
  // ACTUALIZAR INGREDIENTES / PASOS
  // --------------------------------------------------

  function actualizarElemento(
    lista,
    setLista,
    indice,
    valor
  ) {

    setLista(

      lista.map(
        (elemento, i) =>
          i === indice
            ? valor
            : elemento
      )

    );
  }


  // --------------------------------------------------
  // GUARDAR
  // --------------------------------------------------

  async function guardar() {

    const ingredientesValidos =
      ingredientes
        .map((item) =>
          item.trim()
        )
        .filter(Boolean);


    const pasosValidos =
      pasos
        .map((paso) =>
          paso.trim()
        )
        .filter(Boolean);


    // Validación nombre

    if (!nombre.trim()) {

      Alert.alert(
        'Falta el nombre',
        'Escribe el nombre de la receta.'
      );

      return;
    }


    // Validación ingredientes y pasos

    if (
      ingredientesValidos.length === 0 ||
      pasosValidos.length === 0
    ) {

      Alert.alert(
        'Receta incompleta',
        'Agrega al menos un ingrediente y un paso de preparación.'
      );

      return;
    }


    // Crear receta

    const receta = {

      id:
        `${Date.now()}-${Math.random()
          .toString(36)
          .slice(2, 8)}`,

      nombre:
        nombre.trim(),

      descripcion:
        descripcion.trim(),

      ingredientes:
        ingredientesValidos,

      pasos:
        pasosValidos,

      foto,

      fecha:
        new Date().toISOString(),

    };


    await onGuardar(
      receta,
      navigation
    );
  }


  // --------------------------------------------------
  // INTERFAZ
  // --------------------------------------------------

  return (

    <KeyboardAvoidingView
      style={{ flex: 1 }}

      behavior={
        Platform.OS === 'ios'
          ? 'padding'
          : undefined
      }
    >

      <ScrollView
        style={styles.contenedor}

        keyboardShouldPersistTaps="handled"
      >

        <Text style={styles.etiqueta}>
          NUEVA CREACIÓN
        </Text>


        <Text style={styles.titulo}>
          Mi nueva receta ✍️
        </Text>


        <Text style={styles.descripcion}>
          Anota los ingredientes, describe
          la preparación y agrega una foto.
        </Text>


        {/* NOMBRE */}

        <Text
          style={styles.etiquetaCampo}
        >
          Nombre de la receta *
        </Text>


        <TextInput
          style={styles.input}

          placeholder="Ej. Pasta a la boloñesa"

          placeholderTextColor="#999999"

          value={nombre}

          onChangeText={setNombre}
        />


        {/* DESCRIPCIÓN */}

        <Text
          style={styles.etiquetaCampo}
        >
          Descripción
        </Text>


        <TextInput
          style={[
            styles.input,
            styles.inputMultilinea,
          ]}

          placeholder="Una breve descripción del platillo..."

          placeholderTextColor="#999999"

          value={descripcion}

          onChangeText={setDescripcion}

          multiline
        />


        {/* INGREDIENTES */}

        <Text
          style={styles.subtitulo}
        >
          🥕 Ingredientes
        </Text>


        {ingredientes.map(
          (ingrediente, indice) => (

            <View
              key={indice}
              style={styles.fila}
            >

              <TextInput
                style={[
                  styles.input,
                  styles.inputFlexible,
                ]}

                placeholder={
                  `Ingrediente ${indice + 1} ` +
                  `(ej. 2 tomates)`
                }

                placeholderTextColor="#999999"

                value={ingrediente}

                onChangeText={(valor) =>
                  actualizarElemento(
                    ingredientes,
                    setIngredientes,
                    indice,
                    valor
                  )
                }
              />


              {ingredientes.length > 1 && (

                <TouchableOpacity
                  onPress={() =>
                    setIngredientes(
                      ingredientes.filter(
                        (_, i) =>
                          i !== indice
                      )
                    )
                  }
                >

                  <Text
                    style={styles.quitar}
                  >
                    ✕
                  </Text>

                </TouchableOpacity>

              )}

            </View>

          )
        )}


        <TouchableOpacity
          style={styles.botonSecundario}

          onPress={() =>
            setIngredientes([
              ...ingredientes,
              '',
            ])
          }
        >

          <Text
            style={styles.textoSecundario}
          >
            + Añadir ingrediente
          </Text>

        </TouchableOpacity>


        {/* PASOS */}

        <Text
          style={styles.subtitulo}
        >
          👨‍🍳 Preparación paso a paso
        </Text>


        {pasos.map(
          (paso, indice) => (

            <View
              key={indice}
              style={styles.filaPaso}
            >

              <Text
                style={styles.numeroPaso}
              >
                {indice + 1}.
              </Text>


              <TextInput
                style={[
                  styles.input,
                  styles.inputFlexible,
                  styles.inputMultilinea,
                ]}

                placeholder={
                  `Describe el paso ${indice + 1}...`
                }

                placeholderTextColor="#999999"

                value={paso}

                onChangeText={(valor) =>
                  actualizarElemento(
                    pasos,
                    setPasos,
                    indice,
                    valor
                  )
                }

                multiline
              />


              {pasos.length > 1 && (

                <TouchableOpacity
                  onPress={() =>
                    setPasos(
                      pasos.filter(
                        (_, i) =>
                          i !== indice
                      )
                    )
                  }
                >

                  <Text
                    style={styles.quitar}
                  >
                    ✕
                  </Text>

                </TouchableOpacity>

              )}

            </View>

          )
        )}


        <TouchableOpacity
          style={styles.botonSecundario}

          onPress={() =>
            setPasos([
              ...pasos,
              '',
            ])
          }
        >

          <Text
            style={styles.textoSecundario}
          >
            + Añadir paso
          </Text>

        </TouchableOpacity>


        {/* FOTOGRAFÍA */}

        <Text
          style={styles.subtitulo}
        >
          📸 Fotografía del platillo
        </Text>


        {foto ? (

          <Image
            source={{
              uri: foto,
            }}

            style={
              styles.fotoFormulario
            }
          />

        ) : (

          <View
            style={styles.areaFoto}
          >

            <Text
              style={{
                fontSize: 36,
              }}
            >
              🍽️
            </Text>


            <Text
              style={styles.descripcion}
            >
              Todavía no has agregado
              una foto.
            </Text>

          </View>

        )}


        {/* CÁMARA */}

        <TouchableOpacity
          style={styles.botonSecundario}

          onPress={tomarFoto}
        >

          <Text
            style={styles.textoSecundario}
          >
            📷 Tomar fotografía
          </Text>

        </TouchableOpacity>


        {/* GALERÍA */}

        <TouchableOpacity
          style={styles.botonSecundario}

          onPress={elegirFoto}
        >

          <Text
            style={styles.textoSecundario}
          >
            🖼️ Elegir de la galería
          </Text>

        </TouchableOpacity>


        {/* QUITAR FOTO */}

        {foto && (

          <TouchableOpacity
            onPress={() =>
              setFoto(null)
            }
          >

            <Text
              style={styles.quitarFoto}
            >
              Quitar fotografía
            </Text>

          </TouchableOpacity>

        )}


        {/* GUARDAR */}

        <TouchableOpacity
          style={styles.botonPrincipal}

          onPress={guardar}
        >

          <Text
            style={styles.textoBoton}
          >
            Guardar receta
          </Text>

        </TouchableOpacity>


        <View
          style={{
            height: 35,
          }}
        />

      </ScrollView>

    </KeyboardAvoidingView>
  );
}


// ==================================================
// ESTILOS
// ==================================================

const styles = StyleSheet.create({

  // ------------------------------------------------
  // LOADING
  // ------------------------------------------------

  loadingContainer: {
    flex: 1,

    backgroundColor:
      colores.fondo,

    alignItems: 'center',

    justifyContent: 'center',
  },


  loadingEmoji: {
    fontSize: 80,
  },


  loadingTitle: {
    color: colores.oscuro,

    fontSize: 30,

    fontWeight: '800',

    marginTop: 20,
  },


  loadingText: {
    color: colores.secundario,

    fontSize: 14,

    marginTop: 8,
  },


  // ------------------------------------------------
  // GENERAL
  // ------------------------------------------------

  contenedor: {
    flex: 1,

    backgroundColor:
      colores.fondo,

    padding: 20,
  },


  modal: {
    flex: 1,

    backgroundColor:
      colores.fondo,

    padding: 20,

    paddingTop: 45,
  },


  etiqueta: {
    color: colores.principal,

    fontSize: 11,

    fontWeight: '800',

    letterSpacing: 2,

    marginTop: 10,

    marginBottom: 8,
  },


  titulo: {
    color: colores.oscuro,

    fontSize: 30,

    fontWeight: '800',

    marginBottom: 8,
  },


  tituloDetalle: {
    color: colores.oscuro,

    fontSize: 30,

    fontWeight: '800',

    marginVertical: 15,
  },


  descripcion: {
    color: colores.secundario,

    fontSize: 14,

    lineHeight: 21,

    marginBottom: 18,
  },


  // ------------------------------------------------
  // BOTONES
  // ------------------------------------------------

  botonPrincipal: {
    backgroundColor:
      colores.principal,

    padding: 16,

    borderRadius: 14,

    alignItems: 'center',

    marginVertical: 15,
  },


  textoBoton: {
    color: '#FFFFFF',

    fontWeight: '700',

    fontSize: 15,
  },


  botonSecundario: {
    backgroundColor:
      '#E9EEE4',

    borderRadius: 12,

    padding: 14,

    alignItems: 'center',

    marginVertical: 5,
  },


  textoSecundario: {
    color:
      colores.principal,

    fontWeight: '700',
  },


  botonVolver: {
    paddingVertical: 12,
  },


  textoBotonVolver: {
    color:
      colores.principal,

    fontSize: 15,

    fontWeight: '700',
  },


  botonEliminar: {
    backgroundColor:
      colores.peligro,

    padding: 15,

    borderRadius: 12,

    alignItems: 'center',

    marginTop: 30,

    marginBottom: 25,
  },


  // ------------------------------------------------
  // TÍTULOS
  // ------------------------------------------------

  subtitulo: {
    color: colores.oscuro,

    fontSize: 20,

    fontWeight: '800',

    marginTop: 22,

    marginBottom: 12,
  },


  etiquetaCampo: {
    fontSize: 14,

    fontWeight: '700',

    color: colores.oscuro,

    marginTop: 14,

    marginBottom: 8,
  },


  // ------------------------------------------------
  // LISTA
  // ------------------------------------------------

  lista: {
    paddingBottom: 25,
  },


  tarjeta: {
    backgroundColor:
      colores.tarjeta,

    borderRadius: 16,

    overflow: 'hidden',

    marginBottom: 15,

    borderWidth: 1,

    borderColor:
      colores.borde,
  },


  fotoTarjeta: {
    width: '100%',

    height: 185,

    backgroundColor:
      colores.borde,
  },


  fotoVacia: {
    height: 120,

    backgroundColor:
      '#EAEDE3',

    alignItems: 'center',

    justifyContent: 'center',
  },


  infoTarjeta: {
    padding: 16,
  },


  nombreTarjeta: {
    fontSize: 19,

    color: colores.oscuro,

    fontWeight: '800',

    marginBottom: 6,
  },


  descripcionTarjeta: {
    color:
      colores.secundario,

    fontSize: 13,

    lineHeight: 19,
  },


  enlace: {
    color:
      colores.principal,

    fontWeight: '700',

    marginTop: 12,
  },


  // ------------------------------------------------
  // LISTA VACÍA
  // ------------------------------------------------

  vacio: {
    alignItems: 'center',

    paddingVertical: 45,
  },


  emojiVacio: {
    fontSize: 55,

    marginBottom: 15,
  },


  textoVacio: {
    color:
      colores.oscuro,

    fontWeight: '700',

    fontSize: 18,

    marginBottom: 8,
  },


  // ------------------------------------------------
  // INPUTS
  // ------------------------------------------------

  input: {
    backgroundColor:
      colores.tarjeta,

    borderWidth: 1,

    borderColor:
      colores.borde,

    borderRadius: 12,

    padding: 13,

    fontSize: 14,

    color:
      colores.texto,

    minHeight: 48,
  },


  inputFlexible: {
    flex: 1,

    minWidth: 0,
  },


  inputMultilinea: {
    minHeight: 75,

    textAlignVertical:
      'top',
  },


  fila: {
    flexDirection: 'row',

    alignItems: 'center',

    gap: 10,

    marginBottom: 10,
  },


  filaPaso: {
    flexDirection: 'row',

    alignItems: 'flex-start',

    gap: 8,

    marginBottom: 10,
  },


  quitar: {
    color:
      colores.peligro,

    fontSize: 19,

    fontWeight: '700',

    padding: 5,
  },


  // ------------------------------------------------
  // PASOS
  // ------------------------------------------------

  numeroPaso: {
    color:
      colores.principal,

    fontWeight: '800',

    fontSize: 17,

    marginTop: 12,

    minWidth: 25,
  },


  pasoDetalle: {
    flexDirection: 'row',

    alignItems: 'flex-start',

    marginBottom: 15,

    gap: 10,
  },


  textoPaso: {
    color:
      colores.texto,

    fontSize: 15,

    lineHeight: 23,

    flex: 1,
  },


  elementoLista: {
    color:
      colores.texto,

    fontSize: 16,

    lineHeight: 27,
  },


  // ------------------------------------------------
  // FOTOGRAFÍAS
  // ------------------------------------------------

  areaFoto: {
    backgroundColor:
      '#EAEDE3',

    borderRadius: 14,

    alignItems: 'center',

    padding: 25,

    marginBottom: 10,
  },


  fotoFormulario: {
    width: '100%',

    height: 240,

    borderRadius: 14,

    marginBottom: 10,
  },


  fotoDetalle: {
    width: '100%',

    height: 260,

    borderRadius: 16,
  },


  quitarFoto: {
    color:
      colores.peligro,

    textAlign: 'center',

    padding: 12,

    fontWeight: '600',
  },

});
