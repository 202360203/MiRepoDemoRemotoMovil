package com.example.clonewhatsapp;

import java.util.HashMap;
import java.util.Map;

public class AuthRequest {

    private String email;
    private String password;
    private Map<String, String> data;

    // Para iniciar sesión
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Para registrar usuario con su nombre
    public AuthRequest(String email, String password, String nombre) {
        this.email = email;
        this.password = password;

        this.data = new HashMap<>();
        this.data.put("nombre", nombre);
    }
}
