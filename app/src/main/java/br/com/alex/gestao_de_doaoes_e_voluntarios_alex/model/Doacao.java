package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;

public class Doacao {
    private String id;
    private String tipo;
    private int quantidade;
    private String dataEntrada;
    private String dataSaida;
    private String userId;

    public Doacao() {
        // Necessário para o Firebase
    }

    public Doacao(String id, String tipo, int quantidade, String dataEntrada, String dataSaida, String userId) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
