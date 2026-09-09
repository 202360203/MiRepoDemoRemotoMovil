import React, { useEffect, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Animated,
} from 'react-native';

export default function SplashScreenAnimated() {
  const scale = useRef(
    new Animated.Value(0.5)
  ).current;

  const opacity = useRef(
    new Animated.Value(0)
  ).current;

  const rotate = useRef(
    new Animated.Value(0)
  ).current;

  useEffect(() => {
    Animated.parallel([
      Animated.spring(scale, {
        toValue: 1,
        friction: 5,
        tension: 40,
        useNativeDriver: true,
      }),

      Animated.timing(opacity, {
        toValue: 1,
        duration: 700,
        useNativeDriver: true,
      }),

      Animated.timing(rotate, {
        toValue: 1,
        duration: 1000,
        useNativeDriver: true,
      }),
    ]).start();
  }, []);

  const rotation = rotate.interpolate({
    inputRange: [0, 1],
    outputRange: ['-15deg', '0deg'],
  });

  return (
    <View style={styles.container}>
      <Animated.View
        style={[
          styles.content,
          {
            opacity,
            transform: [
              { scale },
              { rotate: rotation },
            ],
          },
        ]}
      >
        <View style={styles.logo}>
          <Text style={styles.logoEmoji}>
            🎮
          </Text>
        </View>

        <Text style={styles.title}>
          GameHub
        </Text>

        <Text style={styles.subtitle}>
          Diversión en un solo lugar
        </Text>
      </Animated.View>

      <Text style={styles.loading}>
        Cargando...
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#6C63FF',
    justifyContent: 'center',
    alignItems: 'center',
  },

  content: {
    alignItems: 'center',
  },

  logo: {
    width: 120,
    height: 120,
    borderRadius: 32,
    backgroundColor: '#FFFFFF',
    justifyContent: 'center',
    alignItems: 'center',

    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.2,
    shadowRadius: 8,
    elevation: 8,
  },

  logoEmoji: {
    fontSize: 65,
  },

  title: {
    color: '#FFFFFF',
    fontSize: 40,
    fontWeight: 'bold',
    marginTop: 20,
  },

  subtitle: {
    color: '#E8E6FF',
    fontSize: 16,
    marginTop: 8,
  },

  loading: {
    position: 'absolute',
    bottom: 50,
    color: '#E8E6FF',
    fontSize: 14,
  },
});
