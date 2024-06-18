package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;



import java.util.List;

import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Doacao;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.DonationViewHolder> {

    private List<Doacao> donationsList;

    public DonationsAdapter(List<Doacao> donationsList) {
        this.donationsList = donationsList;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        Doacao donation = donationsList.get(position);
        holder.tvTipo.setText(donation.getTipo());
        holder.tvQuantidade.setText(String.valueOf(donation.getQuantidade()));
        holder.tvDataEntrada.setText(donation.getDataEntrada());
        holder.tvDataSaida.setText(donation.getDataSaida());
    }

    @Override
    public int getItemCount() {
        return donationsList.size();
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvQuantidade, tvDataEntrada, tvDataSaida;

        public DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvDataEntrada = itemView.findViewById(R.id.tvDataEntrada);
            tvDataSaida = itemView.findViewById(R.id.tvDataSaida);
        }
    }
}
