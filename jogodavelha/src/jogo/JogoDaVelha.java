package jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame implements ActionListener {
    private JButton[][] botoes;
    private char[][] tabuleiro;
    private char jogadorAtual;
    private JLabel mensagem;

    public JogoDaVelha() {
        setTitle("Jogo da Velha");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(3, 3));
        botoes = new JButton[3][3];
        tabuleiro = new char[3][3];
        jogadorAtual = 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j] = new JButton();
                botoes[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                botoes[i][j].addActionListener(this);
                painelTabuleiro.add(botoes[i][j]);
            }
        }

        mensagem = new JLabel("Jogador " + jogadorAtual + " é sua vez.");
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelTabuleiro, BorderLayout.CENTER);
        getContentPane().add(mensagem, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (botao == botoes[i][j] && tabuleiro[i][j] == 0) {
                    tabuleiro[i][j] = jogadorAtual;
                    botao.setText(Character.toString(jogadorAtual));
                    if (verificarVencedor(i, j)) {
                        mensagem.setText("Jogador " + jogadorAtual + " venceu!");
                        desabilitarBotoes();
                    } else if (tabuleiroCompleto()) {
                        mensagem.setText("Empate!");
                        desabilitarBotoes();
                    } else {
                        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
                        mensagem.setText("Jogador " + jogadorAtual + " é sua vez.");
                    }
                    return;
                }
            }
        }
    }

    private boolean verificarVencedor(int linha, int coluna) {
        return (tabuleiro[linha][0] == jogadorAtual && tabuleiro[linha][1] == jogadorAtual && tabuleiro[linha][2] == jogadorAtual) ||
                (tabuleiro[0][coluna] == jogadorAtual && tabuleiro[1][coluna] == jogadorAtual && tabuleiro[2][coluna] == jogadorAtual) ||
                (tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual && linha == coluna) ||
                (tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual && linha + coluna == 2);
    }

    private boolean tabuleiroCompleto() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desabilitarBotoes() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new JogoDaVelha();
    }
}
