package sprites;

import global.Universal;
import system.AnimationType;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite<T extends Enum<T> & AnimationType> {
    private T currentState;
    private BufferedImage sprite;
    private BufferedImage[][] spriteSCALED;
    private int currentFrame;
    private int frameCount;
    private int frameSpeed; //velocidade da animação, basicamente
    private int frameHeight, frameWidth;
    private int frameHeightSCALED, frameWidthSCALED;
    private float anchorX = 0.5f;
    private float anchorY = 0.5f;

    public Sprite(BufferedImage sprite, int alturaFrame, int larguraFrame, Class<T> enumClass, int frameSpeed){
        this.frameHeight = alturaFrame;
        this.frameWidth = larguraFrame;
        this.frameSpeed = frameSpeed;

        frameHeightSCALED = alturaFrame * (int) Universal.SCALE;
        frameWidthSCALED = larguraFrame * (int)Universal.SCALE;

        T[] actions = enumClass.getEnumConstants();
        spriteSCALED = new BufferedImage[actions.length][];

        /*preencher a minha malha*/
        for(T action : actions){
            int row = action.getIndex();
            int frameCount = action.getFrameCount();
            spriteSCALED[row] = new BufferedImage[frameCount];

            for(int i = 0; i < frameCount; i++){
                /*Recorto o frame original*/
                BufferedImage frame = sprite.getSubimage(
                        i * larguraFrame,
                        row * alturaFrame,
                        larguraFrame,
                        alturaFrame
                );

                /*agora eu vou ESCALONAR esse frame*/
                BufferedImage frameEscalonado = new BufferedImage(frameWidthSCALED, frameHeightSCALED, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = frameEscalonado.createGraphics();
                g2d.drawImage(frame, 0, 0, frameWidthSCALED, frameHeightSCALED, null);
                g2d.dispose();

                spriteSCALED[row][i] = frameEscalonado;
            }
        }
        // Inicializa com a primeira ação por padrão
        currentState = actions[0];
        currentFrame = 0;
        frameCount = 0;
    }

    /*Altero a ação (consequentemente o índice)*/
    public void setAction(T novaAcao){
        if(novaAcao != currentState){
            currentState = novaAcao;
            currentFrame = 0;
            frameCount = 0;
        }
    }

    /*Atualizo o estado de ação atual*/
    public void update(){
        frameCount++;
        if(frameCount >= frameSpeed){
            currentFrame = (currentFrame + 1) % currentState.getFrameCount();
            frameCount = 0;
        }
    }

    /*Reseto a ação*/
    public void resetAction(){
        currentFrame = 0;
        frameCount = 0;
    }

    /*Render*/
    public void render(Graphics2D g2d, int x, int y){
        int renderX = (int)(x - frameWidthSCALED * anchorX);
        int renderY = (int)(y - frameHeightSCALED * anchorY);
        g2d.drawImage(spriteSCALED[currentState.getIndex()][currentFrame],
                renderX, renderY,
                null
        );
        if(Universal.showGrid){
            renderAnchor(g2d, x, y);
        }
    }

    /*Renderizar o tamanho da sprite para debug*/
    public void renderAnchor(Graphics2D g2d, int x, int y){
        g2d.setColor(Color.WHITE);
        int renderX = (int)(x - frameWidthSCALED * anchorX);
        int renderY = (int)(y - frameHeightSCALED * anchorY);
        g2d.drawRect(renderX, renderY, frameWidthSCALED, frameHeightSCALED);

        g2d.setColor(new Color(0x2DFFD7));
        int anchorDiameter = 10;
        int anchorCenterX = x - anchorDiameter / 2;
        int anchorCenterY = y - anchorDiameter / 2;
        g2d.fillOval(anchorCenterX, anchorCenterY, anchorDiameter, anchorDiameter);
    }

    /*Getters*/
    public BufferedImage getFrameAtual() {
        return spriteSCALED[currentState.getIndex()][currentFrame];
    }

    public int getFrameHeightSCALED() {
        return frameHeightSCALED;
    }

    public int getFrameWidthSCALED() {
        return frameWidthSCALED;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getFrameSpeed() {
        return frameSpeed;
    }

    /*Setters*/
    public void setFrameHeightSCALED(int frameHeightSCALED) {
        this.frameHeightSCALED = frameHeightSCALED;
    }

    public void setFrameWidthSCALED(int frameWidthSCALED) {
        this.frameWidthSCALED = frameWidthSCALED;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
