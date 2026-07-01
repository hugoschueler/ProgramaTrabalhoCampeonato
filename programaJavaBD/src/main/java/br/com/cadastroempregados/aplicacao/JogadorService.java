package br.com.cadastroempregados.aplicacao;

import br.com.cadastroempregados.modelo.Jogador;
import br.com.cadastroempregados.persistencia.JogadorRepository;

import java.util.List;

public class JogadorService {
    private final JogadorRepository repository;

    public JogadorService(JogadorRepository repository) {
        this.repository = repository;
    }

    public void inserir(String nickname, String idJogadorTexto, String idInscricaoTexto) {
        if (estaVazio(nickname) || estaVazio(idJogadorTexto) || estaVazio(idInscricaoTexto)) {
            throw new IllegalArgumentException("Preencha nickname, ID Jogador e ID Inscrição.");
        }

        int idJogador = converterInteiro(idJogadorTexto, "ID Jogador");
        int idInscricao = converterInteiro(idInscricaoTexto, "ID Inscrição");

        if (idJogador <= 0 || idInscricao <= 0) {
            throw new IllegalArgumentException("Os IDs devem ser maiores que zero.");
        }

        if (repository.existeIdJogador(idJogador)) {
            throw new IllegalArgumentException("Já existe um jogador com este ID.");
        }

        Jogador jogador = new Jogador(idJogador, nickname.trim(), idInscricao);
        repository.inserir(jogador);
    }

    public List<Jogador> listar() {
        return repository.listar();
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private int converterInteiro(String texto, String nomeCampo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um " + nomeCampo + " numérico válido.");
        }
    }
}