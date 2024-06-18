package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;

public class NotificationPreferencesActivity extends AppCompatActivity {
    private CheckBox cbEmail, cbPush;
    private Button btnSalvarPreferencias, btnVoltar;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_preferences);

        cbEmail = findViewById(R.id.cbEmail);
        cbPush = findViewById(R.id.cbPush);
        btnSalvarPreferencias = findViewById(R.id.btnSalvarPreferencias);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            usersRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
            loadPreferences();
        }

        btnSalvarPreferencias.setOnClickListener(v -> savePreferences());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void loadPreferences() {
        usersRef.child("notificationPreferences").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Boolean emailPref = dataSnapshot.child("email").getValue(Boolean.class);
                    Boolean pushPref = dataSnapshot.child("push").getValue(Boolean.class);

                    cbEmail.setChecked(emailPref != null && emailPref);
                    cbPush.setChecked(pushPref != null && pushPref);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotificationPreferencesActivity.this, "Erro ao carregar preferências.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePreferences() {
        boolean emailPref = cbEmail.isChecked();
        boolean pushPref = cbPush.isChecked();

        if (usersRef != null) {
            usersRef.child("notificationPreferences").child("email").setValue(emailPref);
            usersRef.child("notificationPreferences").child("push").setValue(pushPref)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(NotificationPreferencesActivity.this, "Preferências salvas com sucesso.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NotificationPreferencesActivity.this, "Erro ao salvar preferências.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
