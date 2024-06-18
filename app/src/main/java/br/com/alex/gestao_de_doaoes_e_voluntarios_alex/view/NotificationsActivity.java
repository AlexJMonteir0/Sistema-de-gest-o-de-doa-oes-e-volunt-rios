package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter.NotificationsAdapter;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Notificacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView rvNotifications;
    private Button btnPreferences, btnVoltar;
    private FirebaseAuth mAuth;
    private FirebaseController firebaseController;
    private DatabaseReference notificationsRef;
    private NotificationsAdapter notificationsAdapter;
    private List<Notificacao> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        rvNotifications = findViewById(R.id.rvNotifications);
        btnPreferences = findViewById(R.id.btnPreferences);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        notificationsRef = firebaseController.getNotificationsRef();
        notificationList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(notificationList);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(notificationsAdapter);

        btnPreferences.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationsActivity.this, NotificationPreferencesActivity.class);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> finish());

        loadNotifications();
    }

    private void loadNotifications() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        notificationsRef.orderByChild("destinatarioId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notificacao notification = snapshot.getValue(Notificacao.class);
                    notificationList.add(notification);
                }
                notificationsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotificationsActivity.this, "Erro ao carregar notificações.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
