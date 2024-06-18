package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.model;


public class Voluntario extends Usuario {
    public String habilidades;
    public String disponibilidade;


    public Voluntario() {}

    public Voluntario(String id, String nome, String email, String senha, String tipo, String habilidades, String disponibilidade) {
        super(id, nome, email, senha, tipo);
        this.habilidades = habilidades;
        this.disponibilidade = disponibilidade;

    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }


}
