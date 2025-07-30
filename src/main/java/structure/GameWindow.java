package structure;

import global.Universal;

import javax.swing.*;

public class GameWindow extends JFrame {
    private GameCanvas gc;

    public GameWindow(){
        gc = new GameCanvas();
        setUndecorated(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Universal.GAME_WIDTH, Universal.GAME_HEIGHT);
        setResizable(true);
        gc.setBounds(0, 0, Universal.GAME_WIDTH, Universal.GAME_HEIGHT);

        add(gc);

        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true); // Mostra tudo

        gc.initCanvas();
        gc.initGame();
    }
}
