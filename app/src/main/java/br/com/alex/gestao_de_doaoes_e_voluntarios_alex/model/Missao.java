package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;


public class Missao {
    public String id;
    public String nome;
    public String descricao;
    public String status;
    public String dataInicio;
    public String dataFim;

    public Missao() {}

    public Missao(String id, String nome, String descricao, String status, String dataInicio, String dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }
}
