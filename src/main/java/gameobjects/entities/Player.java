package gameobjects.entities;

import gameobjects.GameObject;
import global.Universal;
import math.LinearInterpolation;
import sprites.EffectDisplayer;
import sprites.ImageLoader;
import sprites.Sprite;
import system.AnimationType;
import system.Movable;
import system.Renderable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject implements Movable, Renderable {
    /*Player 1 ou 2*/
    private int playerIndex;

    /*Update*/
    private Movement movement;
    //private Collider collider;

    /*Controle de Dash*/
    private long lastDash = 0;
    private long dashCooldown = 250; //evita spam

    /*Flags booleanas de Movimento*/
    public boolean right = false;
    public boolean left = false;
    public boolean up = false;
    public boolean down = false;
    public boolean dead = false;
    public boolean dash = false;
    public boolean jump = false;

    /*Sprites*/
    private Sprite<PlayerAnimation> sprite;
    private Sprite<LandingAnimation> landingSprite;
    //private Sprite<ShadowAnimation> shadowSprite;
    //private Sprite<MarkAnimation> markSprite;
    //private Sprite<ChargeAnimation> chargeSprite;
    //private Sprite<SwordAnimation> swordSprite;

    /*Actions*/
    public PlayerAnimation playerAction = PlayerAnimation.IDLE;
    public LandingAnimation smokeAction = LandingAnimation.SMOKE;

    //public ChargeAnimation chargeAction = ChargeAnimation.STATIC;

    /*Cachecol*/
    private ScarfRope scarf1, scarf2;

    /*Displayer de efeitos visuais*/
    private EffectDisplayer edLANDING;

    public Player(int index){
        this.playerIndex = index;
        movement = new Movement(this);
        movement.setIsJumping(true);
        initSprite();
        setX(400);
        setY(400);
        scarf1 = new ScarfRope(this, 1.2f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP1.png"), -5, 3, 3, 9);
        scarf2 = new ScarfRope(this, 0.8f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP1v2.png"), -18, 1, 2f, 12);
        setActive(true);
    }

    /*GameObjects*/
    @Override
    public void initSprite(){
        this.sprite = new Sprite<>(ImageLoader.getImage("player/player.png"), 32, 32, PlayerAnimation.class, 15);
        this.landingSprite = new Sprite<>(ImageLoader.getImage("particles/effects/smoke_landing.png"), 32, 32, LandingAnimation.class, 12);
    }

    /*Movable*/
    @Override
    public void update(float deltaTime){
        if(active){ /*Filtragem dentro de cada classe*/
            long currentTime = System.currentTimeMillis();
            if(dash
                    && movement.isCanDash()
                    && !movement.isIsDashing()
                    && currentTime - lastDash >= dashCooldown){
                movement.Dash();
                dash = false;
                lastDash = currentTime;
            }
            movement.updateMovement(deltaTime);
            scarf1.update(deltaTime);
            scarf2.update(deltaTime);
        }
    }

    /*Renderable*/
    @Override
    public void render(Graphics2D g2d){
        if(active){ /*Filtragem dentro de cada classe*/
            sprite.setAction(playerAction);
            landingSprite.setAction(smokeAction);
            sprite.update();
            landingSprite.update();
            scarf2.render(g2d);
            sprite.render(g2d, (int)getX(), (int)getY());
            scarf1.render(g2d);
            landingSprite.render(g2d, (int)getX(), (int)getY());

        }
    }

    /*Segmento do meu cachecol*/
    public class ScarfSegment{

        private Player player;
        private BufferedImage scarfSegSprite;
        private BufferedImage scarfSegSpriteSCALED;
        private int largura = 7;
        private int altura = 7;
        private float x;
        private float y;
        private float lastXPosition;
        private float lastYPosition;
        private float gravity = 0.5f;
        private float wind = -2.0f;
        private float time;
        private float scale;

        public ScarfSegment(float x, float y, Player player, float scale, BufferedImage scarfSegSprite) {
            this.x = x;
            this.y = y;
            lastXPosition = x;
            lastYPosition = y;
            this.scale = scale;
            this.player = player;
            this.scarfSegSprite = scarfSegSprite;
            initSprite();
        }

        public void initSprite() {
            int larguraEscalonada = largura * (int)scale;
            int alturaEscalonada = altura * (int)scale;
            scarfSegSpriteSCALED = new BufferedImage(larguraEscalonada, alturaEscalonada, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scarfSegSpriteSCALED.createGraphics();
            g2d.drawImage(this.scarfSegSprite, 0, 0, larguraEscalonada, alturaEscalonada, null);
            g2d.dispose();
        }

        public void render(Graphics2D g2d){
            int drawX = (int) x;
            int drawY = (int) y;
            int centerX = drawX + scarfSegSpriteSCALED.getWidth() / 2;
            int centerY = drawY + scarfSegSpriteSCALED.getHeight() / 2;
            g2d.drawImage(scarfSegSpriteSCALED, centerX-scarfSegSpriteSCALED.getWidth() / 2, centerY-scarfSegSpriteSCALED.getHeight() / 2, null);
        }

        public void applyConstraint(ScarfSegment segmentoAncora, float distanciaIDEALEntreSegmentos, float deltatime){ //limita os meus segmentos de forma que eles pareçam conectados entre si
            time += deltatime;
            float t = (float) ((Math.sin(time * Math.PI) + 0.5) / 2.0);
            float linearY = LinearInterpolation.lerp(-0.7f, 0.7f, t);
            gravity = linearY + 0.5f;

            float deltaX = x - segmentoAncora.getX();
            float deltaY = y - segmentoAncora.getY();

            //aplico teorema de pitágoras para pegar a distância entre esses dois pontos
            // D = √(x² + y²)
            float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            float diferenca = distanciaIDEALEntreSegmentos - dist;

            float percent = diferenca / dist / 2;
            //calcula o tanto que precisa mudar a posição dos dois pontos para se aproximar da distancia ideal
            //se estiver muito perto, afastar metade da distancia cada um
            //se estiver muito longe, juntar e por aí vai

            float OffSetX = deltaX * percent;
            float OffSetY = deltaY * percent;

            setX(getX() + OffSetX + wind);
            setY(getY() + OffSetY + gravity);
        }

        /*Getters*/
        public float getX() {
            return x;
        }
        public float getY() {
            return y;
        }
        public float getLastXPosition() {
            return lastXPosition;
        }
        public float getLastYPosition() {
            return lastYPosition;
        }

        /*Setters*/
        public void setX(float x) {
            this.x = x;
        }
        public void setY(float y) {
            this.y = y;
        }
        public void setLastXPosition(int lastXPosition) {
            this.lastXPosition = lastXPosition;
        }
        public void setLastYPosition(int lastYPosition) {
            this.lastYPosition = lastYPosition;
        }

    }

    /*O cachecol em si, composto de segmentos controlados pela integração de verlet*/
    public class ScarfRope{
        private Player player;
        private ScarfSegment[] scarf;
        private float distanceX;
        private float distanceY;
        private float offsetX;
        private float offsetY;
        private float scale;
        private int length;

        public ScarfRope(Player player, float distanceBetween, BufferedImage scarfsprite, float offsetX, float offsetY, float scale, int length) {
            this.player = player;
            this.length = length;
            this.scarf = new ScarfSegment[length];
            this.distanceX = distanceBetween;
            this.distanceY = distanceBetween;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.scale = scale;
            initScarf(scarfsprite);
        }

        public void initScarf(BufferedImage scarfsprite){
            scarf[0] = new ScarfSegment(player.getX() + offsetX, player.getY() + offsetY, this.player, this.scale, scarfsprite);
            for (int i = 1; i < scarf.length ; i++) {
                scarf[i] = new ScarfSegment(scarf[i - 1].getX(), scarf[i - 1].getY() + distanceY, this.player, this.scale, scarfsprite);
            }

        }

        public void update(float deltaTime) {
            // âncora segue o jogador
            scarf[0].setX(player.getX() + offsetX);
            scarf[0].setY(player.getY() + offsetY);
            for (int i = 1; i < scarf.length; i++) {
                scarf[i].applyConstraint(scarf[i - 1], distanceX, deltaTime);
            }
        }

        public void render(Graphics2D g2d){
            for(int i = 0; i < scarf.length; i++){
                scarf[i].render(g2d);
            }
        }
    }

    /*Enums com as sprites*/

    /*Player*/
    public enum PlayerAnimation implements AnimationType{
        IDLE(0, 2),
        RUNNING(0, 2),
        JUMP(1, 3),
        FALLING(2, 1),
        DEAD(3, 1);
        //DASH(4, 2);

        private final int index;
        private final int frameCount;

        PlayerAnimation(int index, int frameCount){
            this.index = index;
            this.frameCount = frameCount;
        }

        @Override
        public int getIndex(){
            return index;
        }

        @Override
        public int getFrameCount(){
            return frameCount;
        }
    }

    /*Fumaça ao cair*/
    public enum LandingAnimation implements AnimationType{
        SMOKE(0, 7);

        private final int index;
        private final int frameCount;

        LandingAnimation(int index, int frameCount){
            this.index = index;
            this.frameCount = frameCount;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public int getFrameCount() {
            return frameCount;
        }

    }

    /*Getters*/
    public Movement getMovement(){
        return movement;
    }
    public int getPlayerIndex(){
        return playerIndex;
    }

    public EffectDisplayer getEdLANDING() {
        return edLANDING;
    }

    public Sprite<LandingAnimation> getLandingSprite() {
        return landingSprite;
    }
}
