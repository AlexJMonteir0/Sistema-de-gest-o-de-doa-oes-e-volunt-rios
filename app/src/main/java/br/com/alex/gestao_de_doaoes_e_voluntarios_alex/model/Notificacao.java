package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Notificacao extends RecyclerView.Adapter {
    public String id;
    public String mensagem;
    public String tipo;
    public Usuario destinatario;

    public Notificacao(List<Notificacao> notificationList) {}

    public Notificacao(String id, String mensagem, String tipo, Usuario destinatario) {
        this.id = id;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.destinatario = destinatario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void notifyDataSetChange() {
    }
}

