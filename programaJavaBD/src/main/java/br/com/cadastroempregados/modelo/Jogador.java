package br.com.cadastroempregados.modelo;

public class Jogador {
    private String nickname;
    private int idJogador;
    private int idInscricao;

    public Jogador(int idJogador, String nickname, int idInscricao) {
        this.idJogador = idJogador;
        this.nickname = nickname;
        this.idInscricao = idInscricao;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdInscricao() {
        return idInscricao;
    }

    public void setIdInscricao(int idInscricao) {
        this.idInscricao = idInscricao;
    }
}