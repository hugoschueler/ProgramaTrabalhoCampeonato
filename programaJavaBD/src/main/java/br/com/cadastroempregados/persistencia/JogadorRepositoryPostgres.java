package br.com.cadastroempregados.persistencia; 

import br.com.cadastroempregados.modelo.Jogador; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JogadorRepositoryPostgres implements JogadorRepository {
    private final ConexaoFactory conexaoFactory;

    public JogadorRepositoryPostgres(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
        criarTabelaSeNaoExistir();
    }

    @Override
    public void inserir(Jogador jogador) {
        String sql = "insert into jogador (id_jogador, nickname, id_inscricao) values (?, ?, ?)";

        try (Connection conexao = conexaoFactory.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setInt(1, jogador.getIdJogador());
            comando.setString(2, jogador.getNickname());
            comando.setInt(3, jogador.getIdInscricao());
            
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel inserir o jogador.", e);
        }
    }

    @Override
    public List<Jogador> listar() {
        String sql = "select id_jogador, nickname, id_inscricao from jogador order by nickname";
        List<Jogador> jogadores = new ArrayList<>();

        try (Connection conexao = conexaoFactory.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            
            while (resultado.next()) {
                Jogador jogador = new Jogador(
                        resultado.getInt("id_jogador"),
                        resultado.getString("nickname"),
                        resultado.getInt("id_inscricao")
                );
                jogadores.add(jogador);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel listar os jogadores.", e);
        }

        return jogadores;
    }

    @Override
    public boolean existeIdJogador(int idJogador) {
        String sql = "select 1 from jogador where id_jogador = ?";

        try (Connection conexao = conexaoFactory.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idJogador);

            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel consultar o ID do jogador.", e);
        }
    }

    private void criarTabelaSeNaoExistir() {
        String sql = """
                create table if not exists jogador (
                    id_jogador INTEGER PRIMARY KEY,
                    nickname VARCHAR(30),
                    id_inscricao INTEGER
                )
                """;

        try (Connection conexao = conexaoFactory.conectar();
             Statement comando = conexao.createStatement()) {
            comando.executeUpdate(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Nao foi possivel criar a tabela de jogadores.", e);
        }
    }
}