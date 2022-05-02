package model;

public class Pessoa {
    private String nome;
    private String email;
    private String senha;
    private boolean e_lojista;

    public Pessoa(){
    }

    public boolean getElojista() {
        return e_lojista;
    }

    public void setElojista(boolean e_lojista) {
        this.e_lojista = e_lojista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
