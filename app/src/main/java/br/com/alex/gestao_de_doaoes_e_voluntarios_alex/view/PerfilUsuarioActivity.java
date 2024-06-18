package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Voluntario;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Doadores;

public class PerfilUsuarioActivity extends AppCompatActivity {
    private TextView tvNome, tvEmail, tvTipo, tvHabilidades, tvDisponibilidade;
    private Button btnEditarPerfil, btnVerTarefasMissoes, btnAdicionarDoacao, btnVerNotificacoes, btnVerRelatorios, btnVoltar;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String userId, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        tvNome = findViewById(R.id.tvNome);
        tvEmail = findViewById(R.id.tvEmail);
        tvTipo = findViewById(R.id.tvTipo);
        tvHabilidades = findViewById(R.id.tvHabilidades);
        tvDisponibilidade = findViewById(R.id.tvDisponibilidade);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        btnVerTarefasMissoes = findViewById(R.id.btnVerTarefasMissoes);
        btnAdicionarDoacao = findViewById(R.id.btnAdicionarDoacao);
        btnVerNotificacoes = findViewById(R.id.btnVerNotificacoes);
        btnVerRelatorios = findViewById(R.id.btnVerRelatorios);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            loadUserProfile();
        }

        btnEditarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUsuarioActivity.this, EditarPerfilActivity.class);
            intent.putExtra("userType", userType);
            startActivity(intent);
        });

        btnVerTarefasMissoes.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUsuarioActivity.this, MissionsTasksActivity.class);
            startActivity(intent);
        });

        btnAdicionarDoacao.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUsuarioActivity.this, DonationsActivity.class);
            startActivity(intent);
        });

        btnVerNotificacoes.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUsuarioActivity.this, NotificationsActivity.class);
            startActivity(intent);
        });

        btnVerRelatorios.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUsuarioActivity.this, ReportsActivity.class);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void loadUserProfile() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("habilidades")) {
                        Voluntario voluntario = dataSnapshot.getValue(Voluntario.class);
                        if (voluntario != null) {
                            tvNome.setText("Nome: " + voluntario.getNome());
                            tvEmail.setText("E-mail: " + voluntario.getEmail());
                            tvTipo.setText("Tipo: Voluntário");
                            tvHabilidades.setText("Habilidades: " + voluntario.getHabilidades());
                            tvDisponibilidade.setText("Disponibilidade: " + voluntario.getDisponibilidade());
                            userType = "voluntario";
                            tvHabilidades.setVisibility(View.VISIBLE);
                            tvDisponibilidade.setVisibility(View.VISIBLE);
                            btnVerTarefasMissoes.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Doadores doador = dataSnapshot.getValue(Doadores.class);
                        if (doador != null) {
                            tvNome.setText("Nome: " + doador.getNome());
                            tvEmail.setText("E-mail: " + doador.getEmail());
                            tvTipo.setText("Tipo: Doador");
                            userType = "doador";
                            btnAdicionarDoacao.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PerfilUsuarioActivity.this, "Erro ao carregar perfil.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
