package structure;

import gameobjects.GameObject;
import gameobjects.entities.Player;
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
    //private Player player2;

    /*Bioma*/

    public GameScreen(GameCanvas gc){
        this.gc = gc;
        initList();
    }

    public void initList(){
        player1 = new Player(1);
        ObjectsOnScreen.add(player1);
    }

    public void update(float deltaTime){
        for(GameObject obj : ObjectsOnScreen){
            if(obj instanceof Movable)
                ((Movable)obj).update(deltaTime);
        }
    }

    public void render(Graphics2D g2d){
        for(GameObject obj : ObjectsOnScreen){
            if(obj instanceof Renderable){
                ((Renderable)obj).render(g2d);
            }
        }
    }

}
