package inputs;

import structure.GameCanvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    private GameCanvas gc;

    public MouseHandler(GameCanvas gc) {
        this.gc = gc;
    }

    @Override
    public void mouseDragged(MouseEvent e){

    }

    @Override
    public void mouseMoved(MouseEvent e){

    }

    @Override
    public void mouseClicked(MouseEvent e){

    }


    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){


    }

    @Override
    public void mouseEntered(MouseEvent e){
        //TODO
    }

    @Override
    public void mouseExited(MouseEvent e){
        //TODO
    }


}
