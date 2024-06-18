package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;

public class Tarefa {
    public String id;
    public String descricao;
    public String data;
    public Voluntario voluntario;

    public Tarefa() {}

    public Tarefa(String id, String descricao, String data, Voluntario voluntario) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.voluntario = voluntario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
}
