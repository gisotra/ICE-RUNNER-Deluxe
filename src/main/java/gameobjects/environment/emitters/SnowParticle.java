package gameobjects.environment.emitters;

import gameobjects.GameObject;
import gameobjects.entities.Player;
import global.Universal;
import sprites.ImageLoader;
import sprites.Sprite;
import system.AnimationType;
import system.Movable;
import system.Renderable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class SnowParticle extends GameObject implements Movable, Renderable {
    private Random r = new Random();
    private float verticalSpeed;
    private float horizontalSpeed;
    private Sprite<SnowAnimation> snowSprite;
    private float spawnPointX;
    private float spawnPointY;

    private float rotation = 0;
    private float rotationSpeed;

    public SnowParticle() { //nao preciso passar nada no construtor pq vai ser tudo aleatorio
        this.x = r.nextFloat() * Universal.GAME_WIDTH + 2 * Universal.TILES_SIZE;
        this.y = r.nextFloat() * (-150f) - 70f;
        this.horizontalSpeed = -190f;
        this.verticalSpeed = r.nextFloat() * 190.0f + 165f;
        float minRotationSpeed = 3f;
        float maxRotationSpeed = 10f;
        float randomSpeed = minRotationSpeed + r.nextFloat() * (maxRotationSpeed - minRotationSpeed);
        float direction = r.nextBoolean() ? 1f : -1f;


        this.rotationSpeed = randomSpeed * direction;
        initSprite();
        spawnPointX = x;
        spawnPointY = y;
    }

    @Override
    public void initSprite(){
        int randomParticle = r.nextInt(5) + 1;
        int randomScale = r.nextInt(2) + 1;

        snowSprite = new Sprite(ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png"),
            ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png").getHeight(),
                ImageLoader.getImage("particles/snow/snowP"+ String.valueOf(randomParticle) +".png").getWidth(),
                    SnowAnimation.class,
                1,
                    randomScale);
    }

    /*métodos*/
    @Override
    public void update(float deltaTime){
        this.rotation += rotationSpeed * deltaTime;
        setX(getX() + horizontalSpeed * deltaTime );
        setY(getY() + verticalSpeed * deltaTime);
        //cada um vai ter uma velocidade aleatoria

    }

    @Override
    public void render(Graphics2D g2d){
        snowSprite.render(g2d, (int) x, (int) y, rotation);
    }

    @Override
    public void reposition(){
        setX(spawnPointX);
        setY(spawnPointY);
    }

    /*--------------- Getters e Setters ---------------*/

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return verticalSpeed;
    }

    public void setSpeed(float speed) {
        this.verticalSpeed = speed;
    }

    public float getSpawnPointY() {
        return spawnPointY;
    }

    public float getSpawnPointX() {
        return spawnPointX;
    }

    public Random getR() {
        return r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    /*Enum de animação*/
    public enum SnowAnimation implements AnimationType{
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
