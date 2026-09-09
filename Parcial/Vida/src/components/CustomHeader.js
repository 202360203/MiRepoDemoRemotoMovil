import React from 'react';
import {
  View,
  Text,
  StyleSheet,
} from 'react-native';

export default function CustomHeader({
  title,
  subtitle,
}) {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>{title}</Text>

      {subtitle && (
        <Text style={styles.subtitle}>{subtitle}</Text>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingHorizontal: 20,
    paddingTop: 15,
    paddingBottom: 20,
  },

  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#222',
  },

  subtitle: {
    fontSize: 15,
    color: '#777',
    marginTop: 5,
  },
});
