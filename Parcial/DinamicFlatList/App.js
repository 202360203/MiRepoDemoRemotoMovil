import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, FlatList, TouchableOpacity } from 'react-native';
import CustomModal from './componentes/CustomModal';
import { useState } from 'react';

const cursos=[
  {id:'1', titulo:'Aplicaciones Moviles', duracion:'20 horas', rating:'5.0'},
  {id:'2', titulo:'Base de Datos', duracion:'30 horas', rating:'4.0'},
  {id:'3', titulo:'Redes de Computadoras', duracion:'40 horas', rating:'3.0'},
  {id:'4', titulo:'Desarrollo de Aplicaciones Web', duracion:'20 horas', rating:'2.0'},
  {id:'5', titulo:'Ciberseguridad', duracion:'30 horas', rating:'4.0'},
]

export default function App() {

  const [modalVisible, setModalVisible] = useState(false);
  const [cursoSeleccionado, setCursoSeleccionado] = useState(null);
  const manejaPresionCurso = (tituloCurso) => {
    setCursoSeleccionado({valor:tituloCurso});
    setModalVisible(true);
  }

  const renderCard = ({item}) =>(
    <TouchableOpacity
      style={styles.card}
      onPress={()=>manejaPresionCurso(item.titulo)}
      activeOpacity={0.7}
    >
      <View>
        <Text style={styles.title}>{item.titulo}</Text>
        <Text style={styles.subtitulo}>{item.duracion} | {item.rating}</Text>
      </View>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <Text> Mi lista de Cursos </Text>
      <FlatList
        data={cursos}
        renderItem={renderCard}
        keyExtractor={item=>item.id}
      />
      <CustomModal
        visible={modalVisible}
        onClose={()=>setModalVisible(false)}
        contenido={cursoSeleccionado}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  listContainer:{
    paddingHorizontal: 16,
    paddingBottom: 16,
  },
  header:{
    fontSize: 24,
    fontWeight: 'bold',
    padding: 16,
    color: '#1a1a1a',
  },
  card:{
    backgroundColor: '#ddd',
    padding: 16,
    marginVertical: 8,
    borderRadius:12,
  },
  title:{
    fontSize: 18,
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 4,
  },
  subtitulo:{
    fontSize: 14,
    color: '#0c2b91',
  },
});
