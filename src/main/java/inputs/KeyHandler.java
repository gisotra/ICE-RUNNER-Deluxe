package inputs;

import global.Universal;
import structure.GameCanvas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GameCanvas gc;

    public KeyHandler(GameCanvas gc){
        this.gc = gc;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_U:{
                Universal.showGrid = !Universal.showGrid;
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

}
