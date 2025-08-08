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

    /*---------------------- Sprites ----------------------*/
    /*PLAYER*/
    private Sprite<PlayerAnimation> sprite;

    /*EFEITOS VISUAIS*/
    private Sprite<LandingAnimation> landingSprite;
    private Sprite<JumpingAnimation> jumpingSprite;
    private Sprite<DashSmokeAnimation> dashSmokeSprite;

    /*ADEREÇOS*/
    //private Sprite<ShadowAnimation> shadowSprite;
    //private Sprite<MarkAnimation> markSprite;
    //private Sprite<ChargeAnimation> chargeSprite;
    //private Sprite<SwordAnimation> swordSprite;

    /*AÇÕES*/
    public PlayerAnimation playerAction = PlayerAnimation.IDLE;
    public LandingAnimation landingAction = LandingAnimation.SMOKE;
    public JumpingAnimation jumpingAction = JumpingAnimation.JUMPING;
    public DashSmokeAnimation dashSmokeAction = DashSmokeAnimation.DASHRIGHT;
    //public ChargeAnimation chargeAction = ChargeAnimation.STATIC;

    /*Cachecol*/
    private ScarfRope scarf1, scarf2;

    /*Displayer de efeitos visuais*/
    private EffectDisplayer edLANDING;
    private EffectDisplayer edJUMPING;
    private EffectDisplayer edDASHSMOKE;

    public Player(int index){
        this.playerIndex = index;
        movement = new Movement(this);
        movement.setIsJumping(true);
        initSprite();
        setActive(true);
        reposition();
    }

    /*GameObjects*/
    @Override
    public void initSprite(){
        if(this.playerIndex == 1){
            this.sprite = new Sprite<>(ImageLoader.getImage("player/player.png"), 32, 32, PlayerAnimation.class, 15);
            scarf1 = new ScarfRope(this, 1.2f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP1.png"), -5, 3, 3, 9);
            scarf2 = new ScarfRope(this, 0.8f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP1v2.png"), -18, 1, 2f, 12);
        } else {
            this.sprite = new Sprite<>(ImageLoader.getImage("player/player2.png"), 32, 32, PlayerAnimation.class, 15);
            scarf1 = new ScarfRope(this, 1.2f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP2.png"), -5, 3, 3, 9);
            scarf2 = new ScarfRope(this, 0.8f * Universal.SCALE, ImageLoader.getImage("player/scarfSegmentP2v2.png"), -18, 1, 2f, 12);
        }
        /*Animação de cair*/
        this.landingSprite = new Sprite<>(ImageLoader.getImage("particles/effects/smoke_landing.png"), 32, 32, LandingAnimation.class, 9);
        this.edLANDING = new EffectDisplayer(landingSprite);
        /*Animação de pular*/
        this.jumpingSprite = new Sprite<>(ImageLoader.getImage("particles/effects/smoke_jumping.png"), 32, 32, JumpingAnimation.class, 5);
        this.edJUMPING = new EffectDisplayer(jumpingSprite);
        /*Animação de fumaça dash*/
        //substituir por uma spritesheet de fumaça de dash
        this.dashSmokeSprite = new Sprite<>(ImageLoader.getImage("particles/effects/dash_smoke.png"), 32, 32, DashSmokeAnimation.class, 7);
        this.edDASHSMOKE = new EffectDisplayer(dashSmokeSprite);
        /*Animação de morte*/



    }

    @Override
    public void reposition(){
        setX(this.playerIndex * 200);
        setY(350);
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
            /*jumping*/
            if(movement.isJustJumped()){
                edJUMPING.displayEffect(jumpingSprite, getX(), movement.getGroundLvl());
            }edJUMPING.update();

            /*landing*/
            if(movement.isJustLanded()){
                edLANDING.displayEffect(landingSprite, getX(), movement.getGroundLvl());
            }edLANDING.update();

            /*dashing*/
            if(movement.isJustDashed()){
                switch(movement.getLastDashDirection()){
                    case 1:{ // UP
                        edDASHSMOKE.displayEffect(dashSmokeSprite, getX(), getY() + getHeight());
                    }break;
                    case 2:{ //DOWN
                        edDASHSMOKE.displayEffect(dashSmokeSprite, getX(), getY() - getHeight());
                    }break;
                    case 3:{ //RIGHT
                        edDASHSMOKE.displayEffect(dashSmokeSprite, getX() - getWidth(), getY());
                    }break;
                    case 4:{ //LEFT
                        edDASHSMOKE.displayEffect(dashSmokeSprite, getX() + getWidth(), getY());
                    }break;
                    case 5:{ //UPPER LEFT
                        //player.playerAction = Player.PlayerAnimation.DASHUPPERLEFT;
                    }break;
                    case 6:{ //UPPER RIGHT
                        //player.playerAction = Player.PlayerAnimation.DASHUPPERRIGHT;
                    }break;
                    case 7:{ //LOWER LEFT
                        //player.playerAction = Player.PlayerAnimation.DASHLOWERLEFT;
                    }break;
                    case 8:{ //LOWER RIGHT
                        //player.playerAction = Player.PlayerAnimation.DASHLOWERRIGHT;
                    }break;
                    case 0:{ //DEFAULT PARADO
                        edDASHSMOKE.displayEffect(dashSmokeSprite, getX() - getWidth(), getY());
                        break;
                    }
                }

            }edDASHSMOKE.update();

            movement.updateMovement(deltaTime);
            scarf1.update(deltaTime);
            scarf2.update(deltaTime);

        }
    }

    /*Renderable*/
    @Override
    public void render(Graphics2D g2d){
        if(active){ /*Filtragem dentro de cada classe*/
            /*determino as ações a cada frame*/
            sprite.setAction(playerAction);
            landingSprite.setAction(landingAction);
            jumpingSprite.setAction(jumpingAction);
            dashSmokeSprite.setAction(dashSmokeAction);

            /*atualizo cada animação*/
            sprite.update();
            landingSprite.update();
            jumpingSprite.update();
            dashSmokeSprite.update();

            /*renderizo*/
            scarf2.render(g2d);
            sprite.render(g2d, (int)getX(), (int)getY());
            scarf1.render(g2d);
            edLANDING.render(g2d);
            edJUMPING.render(g2d);
            edDASHSMOKE.render(g2d);
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
        SQUASH(3, 1),
        DESPAIR(4, 2),
        DEAD(5, 1);
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
        SMOKE(0, 6);

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

    /*fumaça ao pular*/
    public enum JumpingAnimation implements AnimationType{
        JUMPING(0, 7);

        private final int index;
        private final int frameCount;

        JumpingAnimation(int index, int frameCount){
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

    public enum DashSmokeAnimation implements AnimationType{
        DASHRIGHT(0, 7),
        DASHLEFT(1, 7),
        DASHUP(2, 7),
        DASHDOWN(3, 7),
        DASHGROUNDRIGHT(4, 7),
        DASHGROUNDLEFT(5, 7);
        //DASHUPPERRIGHT(6, 7),
        //DASHUPPERLEFT(7, 7),
        //DASHLOWERRIGHT(8, 7),
        //DASHLOWERLEFT(9, 7);

        private final int index;
        private final int frameCount;

        DashSmokeAnimation(int index, int frameCount){
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
    public Sprite<JumpingAnimation> getJumpingSprite(){ return jumpingSprite; }
}
