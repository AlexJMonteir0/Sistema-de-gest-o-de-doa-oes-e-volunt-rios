package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportViewHolder> {
    private List<String> reportData;

    public ReportsAdapter(List<String> reportData) {
        this.reportData = reportData;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        String report = reportData.get(position);
        holder.tvReport.setText(report);
    }

    @Override
    public int getItemCount() {
        return reportData.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        public TextView tvReport;

        public ReportViewHolder(View itemView) {
            super(itemView);
            tvReport = itemView.findViewById(R.id.tvReport);
        }
    }
}
