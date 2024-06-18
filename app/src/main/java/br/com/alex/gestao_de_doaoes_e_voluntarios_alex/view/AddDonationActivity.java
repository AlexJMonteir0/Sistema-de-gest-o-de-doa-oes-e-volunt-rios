package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Doacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDonationActivity extends AppCompatActivity {
    private EditText etTipo, etQuantidade, etDataEntrada, etDataSaida;
    private Button btnSalvar, btnVoltar;
    private FirebaseAuth mAuth;
    private DatabaseReference donationsRef;
    private FirebaseController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);

        etTipo = findViewById(R.id.etTipo);
        etQuantidade = findViewById(R.id.etQuantidade);
        etDataEntrada = findViewById(R.id.etDataEntrada);
        etDataSaida = findViewById(R.id.etDataSaida);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        mAuth = FirebaseAuth.getInstance();
        firebaseController = new FirebaseController();
        donationsRef = firebaseController.getDonationsRef();

        btnSalvar.setOnClickListener(v -> saveDonation());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void saveDonation() {
        String tipo = etTipo.getText().toString().trim();
        String quantidadeStr = etQuantidade.getText().toString().trim();
        String dataEntrada = etDataEntrada.getText().toString().trim();
        String dataSaida = etDataSaida.getText().toString().trim();

        if (tipo.isEmpty() || quantidadeStr.isEmpty() || dataEntrada.isEmpty() || dataSaida.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String donationId = donationsRef.push().getKey();
        if (donationId == null) {
            Toast.makeText(this, "Erro ao gerar ID da doação.", Toast.LENGTH_SHORT).show();
            return;
        }

        Doacao doacao = new Doacao(donationId, tipo, quantidade, dataEntrada, dataSaida, userId);
        donationsRef.child(donationId).setValue(doacao).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Doação salva com sucesso.", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Falha ao salvar doação.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etTipo.setText("");
        etQuantidade.setText("");
        etDataEntrada.setText("");
        etDataSaida.setText("");
    }
}
