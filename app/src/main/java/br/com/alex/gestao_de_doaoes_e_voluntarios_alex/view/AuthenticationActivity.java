package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Doadores;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Voluntario;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticationActivity";
    private EditText etEmail, etPassword, etNome, etHabilidades, etDisponibilidade;
    private Spinner spinnerUserType;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        etNome = findViewById(R.id.etNome);
        etHabilidades = findViewById(R.id.etHabilidades);
        etDisponibilidade = findViewById(R.id.etDisponibilidade);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();

        // Configurar o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String userType = (String) parent.getItemAtPosition(position);
                updateUIForUserType(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                Log.e(TAG, "Falha na autenticação: " + task.getException());
                Toast.makeText(AuthenticationActivity.this, "Falha na autenticação.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String userType = spinnerUserType.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userRef = firebaseController.getUsersRef().child(userId);
                    if (userType.equals("Voluntário")) {
                        Voluntario voluntario = new Voluntario(userId, etNome.getText().toString().trim(), email, "", "voluntario", etHabilidades.getText().toString().trim(), etDisponibilidade.getText().toString().trim());
                        userRef.setValue(voluntario).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Log.d(TAG, "Voluntário cadastrado com sucesso.");
                                Toast.makeText(AuthenticationActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            } else {
                                Log.e(TAG, "Falha ao cadastrar voluntário: " + task1.getException());
                                Toast.makeText(AuthenticationActivity.this, "Falha ao cadastrar voluntário.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Doadores doador = new Doadores(userId, etNome.getText().toString().trim(), email, "", "doador", "");
                        userRef.setValue(doador).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Log.d(TAG, "Doador cadastrado com sucesso.");
                                Toast.makeText(AuthenticationActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            } else {
                                Log.e(TAG, "Falha ao cadastrar doador: " + task1.getException());
                                Toast.makeText(AuthenticationActivity.this, "Falha ao cadastrar doador.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            } else {
                Log.e(TAG, "Falha no cadastro: " + task.getException());
                Toast.makeText(AuthenticationActivity.this, "Falha no cadastro.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void clearFields() {
        etEmail.setText("");
        etPassword.setText("");
        etNome.setText("");
        etHabilidades.setText("");
        etDisponibilidade.setText("");
        spinnerUserType.setSelection(0);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, PerfilUsuarioActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateUIForUserType(String userType) {
        if (userType.equals("Voluntário")) {
            etNome.setVisibility(View.VISIBLE);
            etHabilidades.setVisibility(View.VISIBLE);
            etDisponibilidade.setVisibility(View.VISIBLE);
        } else if (userType.equals("Doador")) {
            etNome.setVisibility(View.VISIBLE);
            etHabilidades.setVisibility(View.GONE);
            etDisponibilidade.setVisibility(View.GONE);
        } else {
            etNome.setVisibility(View.GONE);
            etHabilidades.setVisibility(View.GONE);
            etDisponibilidade.setVisibility(View.GONE);
        }
    }
}
