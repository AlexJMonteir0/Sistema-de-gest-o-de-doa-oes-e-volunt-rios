package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Missao;

public class MissionsTasksAdapter extends RecyclerView.Adapter<MissionsTasksAdapter.MissionViewHolder> {
    private List<Missao> missionsList;

    public MissionsTasksAdapter(List<Missao> missionsList) {
        this.missionsList = missionsList;
    }

    @NonNull
    @Override
    public MissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mission_task, parent, false);
        return new MissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionViewHolder holder, int position) {
        Missao missao = missionsList.get(position);
        holder.tvNome.setText(missao.getNome());
        holder.tvDescricao.setText(missao.getDescricao());
        holder.tvStatus.setText(missao.getStatus());
        holder.tvDataInicio.setText("Data de Início: " + missao.getDataInicio());
        holder.tvDataFim.setText("Data de Fim: " + missao.getDataFim());
    }

    @Override
    public int getItemCount() {
        return missionsList.size();
    }

    public static class MissionViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvDescricao, tvStatus, tvDataInicio, tvDataFim;

        public MissionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDataInicio = itemView.findViewById(R.id.tvDataInicio);
            tvDataFim = itemView.findViewById(R.id.tvDataFim);
        }
    }
}
