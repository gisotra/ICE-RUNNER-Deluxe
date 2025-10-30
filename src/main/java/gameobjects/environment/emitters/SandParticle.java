package gameobjects.environment.emitters;

import gameobjects.GameObject;
import global.Universal;
import math.LinearInterpolation;
import sprites.ImageLoader;
import sprites.Sprite;
import system.AnimationType;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.util.Random;

public class SandParticle extends GameObject implements Movable, Renderable {
    private Random r = new Random();
    private float horizontalSpeed; //aleatoria
    private Sprite<SandAnimation> sandSprite;
    private float minY, maxY;
    private float verticalPoint;
    private float time = 0;
    private float spawnPointX;
    private float spawnPointY;

    public SandParticle(){
        this.x = r.nextFloat() * 8 * Universal.TILES_SIZE + Universal.GAME_WIDTH;
        this.y = r.nextFloat() * Universal.GAME_HEIGHT;
        this.horizontalSpeed = r.nextFloat() * -190.0f - 165f;
        int distance = r.nextInt(3) + 1;
        this.maxY = this.y + distance * 6;
        this.minY = this.y - distance * 6;
        initSprite();
        spawnPointX = x;
        spawnPointY = y;
    }

    /*GameObject methods*/

    @Override
    public void initSprite(){
        /*int randomParticle = r.nextInt(4) + 1;
        int randomScale = r.nextInt(3) + 1;

        sandSprite = new Sprite(ImageLoader.getImage("particles/sand/sand"+ String.valueOf(randomParticle) +".png"),
                ImageLoader.getImage("particles/sand/sand"+ String.valueOf(randomParticle) +".png").getHeight(),
                ImageLoader.getImage("particles/sand/sand"+ String.valueOf(randomParticle) +".png").getWidth(),
                SandAnimation.class,
                1,
                randomScale);*/
        int randomParticle = r.nextInt(5) + 1;
        int randomScale = r.nextInt(2) + 1;

        sandSprite = new Sprite(ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png"),
                ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png").getHeight(),
                ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png").getWidth(),
                SnowParticle.SnowAnimation.class,
                1,
                randomScale);
    }

    @Override
    public void reposition() {
        setX(spawnPointX);
        setY(spawnPointY);
    }

    /*Movable*/
    @Override
    public void update(float deltaTime){
        time += deltaTime;
        setX(getX() + horizontalSpeed * deltaTime);
        float t = (float)((Math.sin(time * Math.PI) + 0.5) / 2.0);
        float linearY = LinearInterpolation.lerp(minY, maxY, t);
        setY(linearY + 1f);
        //System.out.println("X: " + getX() + "| Y: "+ getY()); DEBUG
    }

    /*Renderable*/
    @Override
    public void render(Graphics2D g2d){
        sandSprite.render(g2d, (int)getX(), (int)getY());
    }

    public void respawn() {
        this.y = r.nextFloat() * Universal.GAME_HEIGHT;
        int distance = r.nextInt(2) + 1;
        this.maxY = this.y + distance * 6;
        this.minY = this.y - distance * 6;
        this.time = 0; // resetar o oscilador
        this.spawnPointY = this.y;
    }

    public Random getR() {
        return r;
    }

    public float getSpawnPointX() {
        return spawnPointX;
    }

    public float getSpawnPointY() {
        return spawnPointY;
    }

    public enum SandAnimation implements AnimationType {
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
