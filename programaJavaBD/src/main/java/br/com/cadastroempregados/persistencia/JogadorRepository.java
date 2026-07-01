package br.com.cadastroempregados.persistencia;

import br.com.cadastroempregados.modelo.Jogador;

import java.util.List;

public interface JogadorRepository { 
    
    void inserir(Jogador jogador);

    List<Jogador> listar();

    boolean existeIdJogador(int idjogador);
}