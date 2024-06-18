package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;



public class Doadores extends Usuario {
    public String tipoDeDoacao;

    public Doadores() {}

    public Doadores(String id, String nome, String email, String senha, String tipo, String tipoDeDoacao) {
        super(id, nome, email, senha, tipo);
        this.tipoDeDoacao = tipoDeDoacao;
    }

    public String getTipoDeDoacao() {
        return tipoDeDoacao;
    }

    public void setTipoDeDoacao(String tipoDeDoacao) {
        this.tipoDeDoacao = tipoDeDoacao;
    }
}
