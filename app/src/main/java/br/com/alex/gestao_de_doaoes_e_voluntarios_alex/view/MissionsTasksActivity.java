package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter.MissionsTasksAdapter;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Missao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class MissionsTasksActivity extends AppCompatActivity {
    private RecyclerView rvMissionsTasks;
    private MissionsTasksAdapter missionsTasksAdapter;
    private List<Missao> missionsTasksList;
    private FirebaseAuth mAuth;
    private DatabaseReference missionsRef;
    private FirebaseController firebaseController;
    private Button btnAddMissionTask, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions_tasks);

        rvMissionsTasks = findViewById(R.id.rvMissionsTasks);
        btnAddMissionTask = findViewById(R.id.btnAddMissionTask);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        missionsRef = firebaseController.getMissionsRef();

        missionsTasksList = new ArrayList<>();
        missionsTasksAdapter = new MissionsTasksAdapter(missionsTasksList);
        rvMissionsTasks.setLayoutManager(new LinearLayoutManager(this));
        rvMissionsTasks.setAdapter(missionsTasksAdapter);

        btnAddMissionTask.setOnClickListener(v -> {
            Intent intent = new Intent(MissionsTasksActivity.this, AddMissionTaskActivity.class);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(MissionsTasksActivity.this, PerfilUsuarioActivity.class);
            startActivity(intent);
        });

        loadMissionsTasks();
    }

    private void loadMissionsTasks() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        missionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                missionsTasksList.clear();
                for (DataSnapshot missionSnapshot : dataSnapshot.getChildren()) {
                    Missao missao = missionSnapshot.getValue(Missao.class);
                    missionsTasksList.add(missao);
                }
                missionsTasksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MissionsTasksActivity.this, "Erro ao carregar missões e tarefas.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
