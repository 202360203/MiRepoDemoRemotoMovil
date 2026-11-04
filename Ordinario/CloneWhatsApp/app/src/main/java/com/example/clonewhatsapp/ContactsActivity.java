package com.example.clonewhatsapp;

import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView rvUsers;
    private EditText etSearch;

    private UserAdapter adapter;
    private UsersRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rvUsers = findViewById(R.id.rvUsers);
        etSearch = findViewById(R.id.etSearch);

        repository = new UsersRepository(this);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(user -> seleccionarUsuario(user));

        rvUsers.setAdapter(adapter);

        consultarUsuarios();

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                buscarUsuarios(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void consultarUsuarios() {

        repository.consultarUsuarios(new Callback<List<User>>() {

            @Override
            public void onResponse(
                    Call<List<User>> call,
                    Response<List<User>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    adapter.setUsers(response.body());

                } else {
                    Toast.makeText(
                            ContactsActivity.this,
                            "No se pudieron consultar los usuarios",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<User>> call,
                    Throwable t) {

                Toast.makeText(
                        ContactsActivity.this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void buscarUsuarios(String texto) {

        if (texto.trim().isEmpty()) {
            consultarUsuarios();
            return;
        }

        repository.buscarUsuarios(texto,
                new Callback<List<User>>() {

                    @Override
                    public void onResponse(
                            Call<List<User>> call,
                            Response<List<User>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            adapter.setUsers(response.body());
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<User>> call,
                            Throwable t) {
                        Toast.makeText(
                                ContactsActivity.this,
                                "Error al buscar usuarios",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void seleccionarUsuario(User user) {

        Intent intent = new Intent(
                ContactsActivity.this,
                MainActivity.class
        );

        intent.putExtra("recipient_id", user.id);
        intent.putExtra("recipient_name", user.name);
        intent.putExtra("recipient_avatar", user.avatar_url);

        startActivity(intent);
    }
}
