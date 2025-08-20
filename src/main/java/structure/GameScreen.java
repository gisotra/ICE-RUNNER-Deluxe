package structure;

import gameloop.WorldTheme;
import gameloop.screenstates.LevelScreen;
import gameloop.screenstates.MenuScreen;
import gameobjects.GameObject;
import gameobjects.entities.Player;
import gameobjects.environment.emitters.SandEmitter;
import gameobjects.environment.emitters.SnowEmitter;
import gameobjects.obstacles.Obstacle;
import gamestates.BiomeMachine;
import gamestates.StateMachine;
import global.Universal;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScreen {
    /*Referencia ao Canvas principal*/
    private GameCanvas gc;

    /*Arraylist principal, contendo todos os componentes do jogo*/
    public static List<GameObject> backLayerElements = new ArrayList<>();
    public static List<GameObject> midLayerElements = new ArrayList<>();
    public static List<GameObject> frontLayerElements = new ArrayList<>();

    /*Diferentes telas do jogo, com botões, update e render próprios*/
    private LevelScreen ls;
    private MenuScreen ms;
    //private AboutScreen as;
    //private

    /*Componentes do arrayList da camada anterior*/
    /*snow*/
    //private Layer snowLayer0;
    //private Layer snowLayer1;

    /*desert*/
    //private Layer desertLayer0;
    //private Layer desertLayer1;

    /*volcano*/
    //private Layer volcanoLayer0;
    //private Layer volcanoLayer1;

    /*Componentes do arraylist do meio*/
    /*Player*/
    private Player player1;
    private Player player2;

    /*Obstáculos*/

    /*Power Ups*/

    /*Coins*/

    /*Componentes do arraylist da frente*/
    /*Camadas frontais*/
    //private Layer snowFrontLayer;
    //private Layer desertFrontLayer;
    //private Layer volcanoFrontLayer;


    /*Emmiters*/
    private SnowEmitter snowEmitter;
    private SandEmitter sandEmitter;

    public GameScreen(GameCanvas gc){
        this.gc = gc;
        initList(); /*Adiciono tudo*/
    }


    /*update*/
    public void update(float deltaTime){
        switch (StateMachine.gamesstate){
/*========================== MENU DE INICIO ==========================*/
            case MENU:{

            }break;
/*========================== LOOP DENTRO DA FASE ==========================*/
            case PLAYING:{
                /*Camada anterior*/

                /*Camada do meio*/
                for(GameObject obj : midLayerElements){
                    if(obj instanceof Movable)
                        ((Movable)obj).update(deltaTime);
                }

                //colocar um sort dos elementos da camada do meio com base no Y

                /*Emissores*/
                switch(BiomeMachine.currentBiome){
                    case SNOW:{
                        snowEmitter.update(deltaTime);
                    }break;
                    case DESERT:{
                        sandEmitter.update(deltaTime);
                    }break;
                    case VOLCANO:{

                    }break;
                }

            }break;
        }
    }

    /*render*/
    public void render(Graphics2D g2d){
        switch (StateMachine.gamesstate){
/*========================== MENU DE INICIO ==========================*/
            case MENU:{

            }break;
/*========================== LOOP DENTRO DA FASE ==========================*/
            case PLAYING:{
                /*Camada anterior*/

                /*Camada do meio*/
                for(GameObject obj : midLayerElements){
                    if(obj instanceof Renderable){
                        ((Renderable)obj).render(g2d);
                    }
                }

                /*Emissores*/
                switch(BiomeMachine.currentBiome){
                    case SNOW:{
                        snowEmitter.render(g2d);
                    }break;
                    case DESERT:{
                        sandEmitter.render(g2d);
                    }break;
                    case VOLCANO:{

                    }break;
                }

                /*Camada da frente*/

            }break;
/*========================== LOOP DENTRO DA FASE ==========================*/
            case TUTORIAL:{

            }

        }
    }

    /*----------------------------- PRINCIPAIS MÉTODOS DE CONTROLE DO ARRAYLIST -----------------------------*/
    /*Iniciar o arraylist (no construtor)*/
    public void initList(){
        player1 = new Player(1);
        midLayerElements.add(player1);
        player2 = new Player(2);
        midLayerElements.add(player2);
        snowEmitter = new SnowEmitter(70);
        sandEmitter = new SandEmitter(70);
    }

    /*Desativar todo o conteúdo do arraylist (Para quando o player trocar de state, por exemplo)*/
    public void deactivateAll(){
        for(GameObject object : midLayerElements){
            object.setActive(false);
        }
    }

    /*vai receber os valores de cada fase, e filtrar de acordo*/
    public void startGame(WorldTheme theme, int levelIndex){
        for(GameObject object : midLayerElements){
            /*filtrar os obstáculos*/
            if(object instanceof Obstacle){
                if(((Obstacle) object).getTheme() == theme){
                    //object.reposition();
                    //spawner.allowObstacle(object)
                }
            }
        }
        player1.setActive(true);
        if(Universal.bothPlaying){
            player2.setActive(true);
        }
    }

    /*Getters e Setters*/

}
