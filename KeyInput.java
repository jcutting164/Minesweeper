import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;


public class KeyInput extends KeyAdapter implements Serializable {


	private Game game;


    public KeyInput(Game game) {
    	this.game=game;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);

        if(key==KeyEvent.VK_ESCAPE)
            System.exit(0);
     
  	
        }
        
        
        
    




    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();




    }



}