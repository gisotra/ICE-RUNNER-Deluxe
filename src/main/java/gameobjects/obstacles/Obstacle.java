package gameobjects.obstacles;

import gameloop.WorldTheme;
import gameobjects.GameObject;

public abstract class Obstacle extends GameObject {
    protected WorldTheme theme;


    /*Getters*/
    public WorldTheme getTheme() {
        return theme;
    }

}
