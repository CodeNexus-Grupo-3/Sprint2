package school.sptech.model;

public class Jogador {

    private String nome;
    private String posicao;
    private String email;
    private String senha;

    public Jogador(String nome, String posicao, String email, String senha) {
        this.nome = nome;
        this.posicao = posicao;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
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
