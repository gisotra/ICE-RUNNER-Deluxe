package gameloop;

public class WorldMap {
    public static MapLevel levelMap[][];

    public WorldMap(){
        initMap();
    }

    public void initMap(){
        levelMap = new MapLevel[3][6];
        for(int i = 0 ; i < 3; i++){
            for(int j = 0; j < 6; j++){
                WorldTheme wt = null;

                if(i == 0){
                    wt = WorldTheme.SNOW;
                } else if(i == 1){
                    wt = WorldTheme.DESERT;
                } else if(i == 0){
                    wt = WorldTheme.VOLCANO;
                }

                levelMap[i][i] = new MapLevel(wt, j+1);
            }
        }
    }

    /*As fases do jogo serão definidas através dessa matriz
    NEVE    [0] [1] [2] [3] [4] [5]
    DESERTO [0] [1] [2] [3] [4] [5]
    VULCÃO  [0] [1] [2] [3] [4] [5]

    cada fase tem encapsulado o tema daquele mundo e um numero inteiro de 1 a 6
    na hora de iniciar a partida na Screen, eu vou verificar qual o tema e qual o inteiro
    dessa fase.

    O inteiro vai ser usado para calcular a speed base dos obstaculos daquela fase
    */
}
