package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Button;

import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter.ReportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowReportsActivity extends AppCompatActivity {
    private RecyclerView rvReports;
    private Button btnVoltar;
    private List<String> reportData;
    private ReportsAdapter reportsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);

        rvReports = findViewById(R.id.rvReports);
        btnVoltar = findViewById(R.id.btnVoltar);

        reportData = getIntent().getStringArrayListExtra("reportData");
        if (reportData == null) {
            reportData = new ArrayList<>();
        }

        reportsAdapter = new ReportsAdapter(reportData);

        rvReports.setLayoutManager(new LinearLayoutManager(this));
        rvReports.setAdapter(reportsAdapter);

        btnVoltar.setOnClickListener(v -> finish());
    }
}
