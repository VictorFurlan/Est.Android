package br.com.shutappandroid.com.krampus.shutapp.model;

public class Contato {

    private String IdContato;
    private String email;
    private String nome;

    public Contato() {
    }

    public String getIdContato() {
        return IdContato;
    }

    public void setIdContato(String idContato) {
        IdContato = idContato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
