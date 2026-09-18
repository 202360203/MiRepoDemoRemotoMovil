import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View } from 'react-native';

export default function App() {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(()=>{
    fetch("http://localhost:4000/movies")
    .then((res)=>res.json())
    .then((data)=> {
      setMovies(data);
      setLoading(false);
    })
    .catch((error)=>console.error(error));
  },[]);

  if(loading){
    return(
      <View>
        <ActivityIndicator size="large" color="#007aff"/>
      </View>
    );
  }

  const renderItem = ({item}) => (
    <View>
      {item.poster ? (
        <Image source={{uri:item.poster}}/>  
      ):(
        <View>
          <Text>No Image</Text>
        </View>
      )}
      <View>
        <Text>{item.title}</Text>
        <Text>{item.fullplot || "Sin descripción"}</Text>
      </View>
    </View>
  );

  return (
    <FlatList
      data={movies}
      keyExtractor={(item)=>item._id}
      renderItem={renderItem}
    />
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
