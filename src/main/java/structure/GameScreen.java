package structure;

import gameloop.WorldTheme;
import gameobjects.GameObject;
import gameobjects.entities.Player;
import gameobjects.environment.emitters.SnowEmitter;
import gameobjects.obstacles.Obstacle;
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
    public static List<GameObject> ObjectsOnScreen = new ArrayList<>();

    /*Componentes do arraylist*/
    /*Player*/
    private Player player1;
    private Player player2;
    private SnowEmitter snowEmitter;

    /*Bioma*/
    public GameScreen(GameCanvas gc){
        this.gc = gc;
        initList(); /*Adiciono tudo*/
    }


    /*update*/
    public void update(float deltaTime){
        switch (StateMachine.gamesstate){
            case PLAYING:{
                for(GameObject obj : ObjectsOnScreen){
                    if(obj instanceof Movable)
                        ((Movable)obj).update(deltaTime);
                }
                snowEmitter.update(deltaTime);
            }break;
        }
    }

    /*render*/
    public void render(Graphics2D g2d){
        switch (StateMachine.gamesstate){
            case PLAYING:{
                for(GameObject obj : ObjectsOnScreen){
                    if(obj instanceof Renderable){
                        ((Renderable)obj).render(g2d);
                    }
                }
                snowEmitter.render(g2d);
            }break;
        }
    }

    /*----------------------------- PRINCIPAIS MÉTODOS DE CONTROLE DO ARRAYLIST -----------------------------*/
    /*Iniciar o arraylist (no construtor)*/
    public void initList(){
        player1 = new Player(1);
        ObjectsOnScreen.add(player1);
        player2 = new Player(2);
        ObjectsOnScreen.add(player2);
        snowEmitter = new SnowEmitter(70);
    }

    /*Desativar todo o conteúdo do arraylist (Para quando o player trocar de state, por exemplo)*/
    public void deactivateAll(){
        for(GameObject object : ObjectsOnScreen){
            object.setActive(false);
        }
    }

    /*vai receber os valores de cada fase, e filtrar de acordo*/
    public void startGame(WorldTheme theme, int levelIndex){
        for(GameObject object : ObjectsOnScreen){
            /*filtrar os obstáculos*/
            if(object instanceof Obstacle){
                if(((Obstacle) object).getTheme() == theme){
                    //object.reposition();
                    //spawner.allowObstacle(object)
                }
            }
        }

        switch (theme){
            case SNOW:{
                //snowEmmiter.startEmmiting();
            }break;
            case DESERT:{
                //sandEmmiter.startEmmiting();
            }break;
            case VOLCANO:{
                //ashEmmiter.startEmmiting();
            }break;
        }

        player1.setActive(true);
        if(Universal.bothPlaying){
            player2.setActive(true);
        }
    }
}
