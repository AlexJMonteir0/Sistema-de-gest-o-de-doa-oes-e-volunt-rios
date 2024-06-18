package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter.DonationsAdapter;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Doacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DonationsActivity extends AppCompatActivity {
    private RecyclerView rvDonations;
    private DonationsAdapter donationsAdapter;
    private List<Doacao> donationsList;
    private FirebaseAuth mAuth;
    private DatabaseReference donationsRef;
    private FirebaseController firebaseController;
    private Button btnAddDonation, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        rvDonations = findViewById(R.id.rvDonations);
        btnAddDonation = findViewById(R.id.btnAddDonation);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        donationsRef = firebaseController.getDonationsRef();

        donationsList = new ArrayList<>();
        donationsAdapter = new DonationsAdapter(donationsList);
        rvDonations.setLayoutManager(new LinearLayoutManager(this));
        rvDonations.setAdapter(donationsAdapter);

        btnAddDonation.setOnClickListener(v -> {
            Intent intent = new Intent(DonationsActivity.this, AddDonationActivity.class);
            startActivity(intent);
        });

        btnVoltar.setOnClickListener(v -> finish());

        loadDonations();
    }

    private void loadDonations() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        donationsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                donationsList.clear();
                for (DataSnapshot donationSnapshot : dataSnapshot.getChildren()) {
                    Doacao doacao = donationSnapshot.getValue(Doacao.class);
                    donationsList.add(doacao);
                }
                donationsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DonationsActivity.this, "Erro ao carregar doações.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
