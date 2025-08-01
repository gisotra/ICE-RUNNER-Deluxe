package structure;

import global.Universal;
import inputs.KeyHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameCanvas extends Canvas {
    private GameEngine engine;
    private Thread t;

    public GameCanvas(){
        setPreferredSize(new Dimension(Universal.GAME_WIDTH, Universal.GAME_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyHandler(this));
    }

    public void update(float dt){

    }

    public void render(){
        /*Inicializo os buffers*/
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            return;
        }

        /*Inicializo meu pincel*/
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

        /*Desenho tudo na minha tela*/
        try{
            /*RESPONSIVIDADE*/
            int canvasW = getWidth();
            int canvasH = getHeight();

            double scaleX = canvasW / (double) Universal.GAME_HEIGHT;

            // Limpa o fundo para evitar artefatos de frames anteriores
            g2d.setColor(new Color(13, 30, 168));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            if (Universal.showGrid) {
                drawGrid(g2d);
            }
        } finally {
            /*libero o conteúdo do meu pincel*/
            g2d.dispose();
        }

        /*apresento o que foi desenhado no buffer*/
        bs.show();

        /*VSync*/
        Toolkit.getDefaultToolkit().sync();
    }

    /*Inicializa triple-buffering*/
    public void initCanvas() {
        createBufferStrategy(3); // você pode usar 2 ou 3 buffers
    }

    /*Inicializa a thread principal*/
    public void initGame(){
        this.engine = new GameEngine(this);
        this.t = new Thread(engine);
        t.start();
    }

    /*Grid para debug visual*/
    public void drawGrid(Graphics2D g2D) {
        g2D.setColor(Color.WHITE); // Cor preta para o grid
        for (int x = 0; x < Universal.GAME_WIDTH; x += Universal.TILES_SIZE) {
            for (int y = 0; y < Universal.GAME_HEIGHT; y += Universal.TILES_SIZE) {
                g2D.drawRect(x, y, Universal.TILES_SIZE, Universal.TILES_SIZE);
            }
        }
    }

    /*Getters*/
    public GameEngine getEngine() {
        return engine;
    }
}
