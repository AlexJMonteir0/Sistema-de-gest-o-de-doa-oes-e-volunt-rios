package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.R;
import br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model.Notificacao;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private List<Notificacao> notificationList;

    public NotificationsAdapter(List<Notificacao> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notificacao notification = notificationList.get(position);
        holder.tvMessage.setText(notification.getMensagem());
        holder.tvType.setText(notification.getTipo());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage, tvType;

        public NotificationViewHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tvMessage);
            tvType = view.findViewById(R.id.tvType);
        }
    }
}
