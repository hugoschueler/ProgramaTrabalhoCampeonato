package br.com.cadastroempregados.ui;

import br.com.cadastroempregados.aplicacao.JogadorService;
import br.com.cadastroempregados.modelo.Jogador;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JogadorFrame extends JFrame {
    private final JogadorService service;
    private final JTextField nicknameField = new JTextField(20);
    private final JTextField idJogadorField = new JTextField(14);
    private final JTextField idInscricaoField = new JTextField(10);
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nickname", "ID Jogador", "ID Inscrição"}, 0);

    public JogadorFrame(JogadorService service) {
        this.service = service;

        setTitle("Cadastro de Jogadores");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(criarFormulario(), BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        atualizarTabela();
    }

    private JPanel criarFormulario() {
        JPanel camposPanel = new JPanel(new GridLayout(3, 2, 4, 4));
        camposPanel.add(new JLabel("Nickname:"));
        camposPanel.add(nicknameField);
        camposPanel.add(new JLabel("ID Jogador:"));
        camposPanel.add(idJogadorField);
        camposPanel.add(new JLabel("ID Inscrição:"));
        camposPanel.add(idInscricaoField);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(event -> salvarJogador());

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.add(salvarButton);

        JPanel formularioPanel = new JPanel(new BorderLayout(8, 8));
        formularioPanel.add(camposPanel, BorderLayout.CENTER);
        formularioPanel.add(botoesPanel, BorderLayout.SOUTH);

        return formularioPanel;
    }

    private void salvarJogador() {
        try {
            service.inserir(nicknameField.getText(), idJogadorField.getText(), idInscricaoField.getText());
            limparCampos();
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        nicknameField.setText("");
        idJogadorField.setText("");
        idInscricaoField.setText("");
        nicknameField.requestFocus();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);

        List<Jogador> jogadores = service.listar();
        for (Jogador jogador : jogadores) {
            tableModel.addRow(new Object[]{
                    jogador.getNickname(),
                    jogador.getIdJogador(),
                    jogador.getIdInscricao()
            });
        }
    }
}