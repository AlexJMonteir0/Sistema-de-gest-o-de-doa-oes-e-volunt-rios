package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller.FirebaseController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {
    private Button btnGenerateDonationReport, btnGenerateMissionReport, btnVoltar, btnViewReport;
    private FirebaseController firebaseController;
    private List<String> reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        btnGenerateDonationReport = findViewById(R.id.btnGenerateDonationReport);
        btnGenerateMissionReport = findViewById(R.id.btnGenerateMissionReport);

        btnViewReport = findViewById(R.id.btnViewReport);
        btnVoltar = findViewById(R.id.btnVoltar);

        firebaseController = new FirebaseController();
        reportData = new ArrayList<>();

        btnGenerateDonationReport.setOnClickListener(v -> generateDonationReport());
        btnGenerateMissionReport.setOnClickListener(v -> generateMissionReport());
        btnViewReport.setOnClickListener(v -> viewReport());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void generateDonationReport() {
        DatabaseReference donationsRef = firebaseController.getDonationsRef();
        donationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tipo = snapshot.child("tipo").getValue(String.class);
                    int quantidade = snapshot.child("quantidade").getValue(Integer.class);
                    String dataEntrada = snapshot.child("dataEntrada").getValue(String.class);
                    String dataSaida = snapshot.child("dataSaida").getValue(String.class);
                    reportData.add("Tipo: " + tipo + ", Quantidade: " + quantidade + ", Entrada: " + dataEntrada + ", Saída: " + dataSaida);
                }
                Toast.makeText(ReportsActivity.this, "Relatório de Doações Gerado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReportsActivity.this, "Erro ao gerar relatório de doações.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateMissionReport() {
        DatabaseReference missionsRef = firebaseController.getMissionsRef();
        missionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nome = snapshot.child("nome").getValue(String.class);
                    String descricao = snapshot.child("descricao").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String dataInicio = snapshot.child("dataInicio").getValue(String.class);
                    String dataFim = snapshot.child("dataFim").getValue(String.class);
                    reportData.add("Nome: " + nome + ", Descrição: " + descricao + ", Status: " + status + ", Início: " + dataInicio + ", Fim: " + dataFim);
                }
                Toast.makeText(ReportsActivity.this, "Relatório de Missões Gerado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReportsActivity.this, "Erro ao gerar relatório de missões.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateVolunteerActivityReport() {
        DatabaseReference tasksRef = firebaseController.getTasksRef();
        tasksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String descricao = snapshot.child("descricao").getValue(String.class);
                    String data = snapshot.child("data").getValue(String.class);
                    reportData.add("Descrição: " + descricao + ", Data: " + data);
                }
                Toast.makeText(ReportsActivity.this, "Relatório de Atividades de Voluntários Gerado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ReportsActivity.this, "Erro ao gerar relatório de atividades de voluntários.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exportReportToPDF() {
        if (reportData.isEmpty()) {
            Toast.makeText(this, "Não há dados para exportar.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput("relatorio.pdf", MODE_PRIVATE);
            for (String line : reportData) {
                fos.write((line + "\n").getBytes());
            }
            fos.close();
            Toast.makeText(this, "Relatório exportado como PDF.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao exportar relatório.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void viewReport() {
        if (reportData.isEmpty()) {
            Toast.makeText(this, "Não há dados para exibir.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(ReportsActivity.this, ShowReportsActivity.class);
        intent.putStringArrayListExtra("reportData", (ArrayList<String>) reportData);
        startActivity(intent);
    }
}
