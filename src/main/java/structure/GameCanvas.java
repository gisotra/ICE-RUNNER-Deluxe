package structure;

import global.Universal;
import inputs.KeyHandler;
import inputs.MouseHandler;
import sprites.ImageLoader;
import sprites.Sprite;
import system.AnimationType;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameCanvas extends Canvas {
    private GameEngine engine;
    private Thread t;
    private GameScreen screen;
    private int currentUPS = 0;
    private int currentFPS = 0;

    /*Mouse*/
    private Point mousePoint;
    private Cursor cursor;
    private Sprite<CursorAnimation> spriteMouse;

    public GameCanvas(){
        setPreferredSize(new Dimension(Universal.GAME_WIDTH, Universal.GAME_HEIGHT));
        setFocusable(true);
        requestFocus();
        initMouseSprites();
        addKeyListener(new KeyHandler(this, GameScreen.midLayerElements));
        addMouseListener(new MouseHandler(this));
        this.screen = new GameScreen(this);
    }

    public void update(float dt){
        screen.update(dt);
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
            // Limpa o fundo para evitar artefatos de frames anteriores
            g2d.setColor(new Color(13, 30, 168));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("FPS: " + currentFPS, 20, 30);
            g2d.drawString("UPS: " + currentUPS, 20, 50);
            /*if (Universal.showGrid) {
                drawGrid(g2d);
            }*/
            drawPrototypeGround(g2d);
            drawGrid(g2d);

            screen.render(g2d);
            mousePoint = getMousePosition();
            if(mousePoint != null){
                spriteMouse.render(g2d, (int) mousePoint.getX(), (int)mousePoint.getY());
            }
        } finally {
            /*libero o conteúdo do meu pincel*/
            g2d.dispose();
        }

        /*apresento o que foi desenhado no buffer*/
        bs.show();

        /*VSync*/
       // Toolkit.getDefaultToolkit().sync();
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
        g2D.setColor(new Color(0x6767E5)); // Cor preta para o grid
        for (int x = 0; x < Universal.GAME_WIDTH; x += Universal.TILES_SIZE) {
            for (int y = 0; y < Universal.GAME_HEIGHT; y += Universal.TILES_SIZE) {
                g2D.drawRect(x, y, Universal.TILES_SIZE, Universal.TILES_SIZE);
            }
        }
    }

    public void drawPrototypeGround(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        for (int x = 0; x < Universal.GAME_WIDTH; x += Universal.TILES_SIZE) {
            for (int y = 0; y < Universal.GAME_HEIGHT; y += Universal.TILES_SIZE) {
                if(y >= 6.5f*Universal.TILES_SIZE){
                    g2d.fillRect(x, y, Universal.TILES_SIZE, Universal.TILES_SIZE);
                }
            }
        }
    }

    /*Getters*/
    public GameEngine getEngine() {
        return engine;
    }

    public void writeData(int updates, int frames){
        this.currentFPS = frames;
        this.currentUPS = updates;
    }

    //mudar o sprite do meu cursor
    public void initMouseSprites(){

        this.spriteMouse = new Sprite<>(ImageLoader.getImage("ui/cursor.png") , 32, 32, CursorAnimation.class, 1);

        cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
        setCursor(cursor);

    }

    public GameScreen getGameScreen(){
        return this.screen;
    }

    /*========== Classe interna Para os Sprites ==========*/
    public enum CursorAnimation implements AnimationType {

        STATIC;

        @Override
        public int getIndex(){
            return 0;
        }

        @Override
        public int getFrameCount(){
            return 1;
        }
    }
}
