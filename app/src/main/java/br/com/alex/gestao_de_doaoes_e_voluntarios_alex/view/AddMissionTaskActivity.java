package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Missao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMissionTaskActivity extends AppCompatActivity {
    private EditText etNome, etDescricao, etStatus, etDataInicio, etDataFim;
    private Button btnSalvar, btnVoltar;
    private FirebaseAuth mAuth;
    private DatabaseReference missionsRef;
    private FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission_task);

        etNome = findViewById(R.id.etNome);
        etDescricao = findViewById(R.id.etDescricao);
        etStatus = findViewById(R.id.etStatus);
        etDataInicio = findViewById(R.id.etDataInicio);
        etDataFim = findViewById(R.id.etDataFim);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        missionsRef = firebaseController.getMissionsRef();

        btnSalvar.setOnClickListener(v -> saveMissionTask());
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(AddMissionTaskActivity.this, MissionsTasksActivity.class);
            startActivity(intent);

        });
    }

    private void saveMissionTask() {
        String nome = etNome.getText().toString().trim();
        String descricao = etDescricao.getText().toString().trim();
        String status = etStatus.getText().toString().trim();
        String dataInicio = etDataInicio.getText().toString().trim();
        String dataFim = etDataFim.getText().toString().trim();

        if (nome.isEmpty() || descricao.isEmpty() || status.isEmpty() || dataInicio.isEmpty() || dataFim.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String missionId = missionsRef.push().getKey();
        if (missionId == null) {
            Toast.makeText(this, "Erro ao gerar ID da missão/tarefa.", Toast.LENGTH_SHORT).show();
            return;
        }

        Missao missao = new Missao(missionId, nome, descricao, status, dataInicio, dataFim);
        missionsRef.child(missionId).setValue(missao).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Missão/Tarefa salva com sucesso.", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Falha ao salvar missão/tarefa.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etNome.setText("");
        etDescricao.setText("");
        etStatus.setText("");
        etDataInicio.setText("");
        etDataFim.setText("");
    }
}
