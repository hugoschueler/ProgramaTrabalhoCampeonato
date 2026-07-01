package br.com.cadastroempregados;

import br.com.cadastroempregados.aplicacao.JogadorService;
import br.com.cadastroempregados.persistencia.ConexaoFactory;
import br.com.cadastroempregados.persistencia.JogadorRepositoryPostgres;
import br.com.cadastroempregados.ui.JogadorFrame;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JogadorRepositoryPostgres repository = new JogadorRepositoryPostgres(new ConexaoFactory());
            JogadorService service = new JogadorService(repository);
            JogadorFrame frame = new JogadorFrame(service);
            frame.setVisible(true);
        });
    }
}