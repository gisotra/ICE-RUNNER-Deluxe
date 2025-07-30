package global;

public class Universal {
    /*configuração de fps*/
    public static final int FPS_SET = 60;

    /*Configurações de resolução da tela*/
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 3.0f;
    public final static int TILES_IN_WIDTH = 10;  //512px de COMPRIMENTO
    public final static int TILES_IN_HEIGHT = 7;  //288px ALTURA
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public final static int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

    /*debug visual*/
    public static boolean showGrid = false;
}
