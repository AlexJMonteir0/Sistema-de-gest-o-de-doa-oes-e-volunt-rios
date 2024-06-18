package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Voluntario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {
    private EditText etNome, etEmail, etSenha, etHabilidades, etDisponibilidade;
    private Button btnSalvar, btnVoltar;
    private FirebaseAuth mAuth;
    private FirebaseController firebaseController;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etHabilidades = findViewById(R.id.etHabilidades);
        etDisponibilidade = findViewById(R.id.etDisponibilidade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        usersRef = firebaseController.getUsersRef();

        btnSalvar.setOnClickListener(v -> saveUserProfile());
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, PerfilUsuarioActivity.class);
            startActivity(intent);
        });

        loadUserProfile();
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nome = dataSnapshot.child("nome").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String senha = dataSnapshot.child("senha").getValue(String.class);
                    String tipo = dataSnapshot.child("tipo").getValue(String.class);

                    etNome.setText(nome);
                    etEmail.setText(email);

                    if ("voluntario".equals(tipo)) {
                        String habilidades = dataSnapshot.child("habilidades").getValue(String.class);
                        String disponibilidade = dataSnapshot.child("disponibilidade").getValue(String.class);

                        etHabilidades.setText(habilidades);
                        etDisponibilidade.setText(disponibilidade);

                        etHabilidades.setVisibility(View.VISIBLE);
                        etDisponibilidade.setVisibility(View.VISIBLE);
                    } else {
                        etHabilidades.setVisibility(View.GONE);
                        etDisponibilidade.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditarPerfilActivity.this, "Erro ao carregar perfil.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String habilidades = etHabilidades.getText().toString().trim();
        String disponibilidade = etDisponibilidade.getText().toString().trim();

        usersRef.child(userId).child("nome").setValue(nome);
        usersRef.child(userId).child("email").setValue(email);
        usersRef.child(userId).child("senha").setValue(senha);

        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tipo = dataSnapshot.child("tipo").getValue(String.class);
                if ("voluntario".equals(tipo)) {
                    usersRef.child(userId).child("habilidades").setValue(habilidades);
                    usersRef.child(userId).child("disponibilidade").setValue(disponibilidade);
                }
                Toast.makeText(EditarPerfilActivity.this, "Perfil atualizado com sucesso.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditarPerfilActivity.this, "Erro ao atualizar perfil.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
