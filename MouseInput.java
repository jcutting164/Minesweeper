import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;



public class MouseInput implements MouseListener, MouseMotionListener{
        //where initialization occurs:
        //Register for mouse events on blankArea and the panel.
     


	private Game game;
	private int gameRow=-1, gameColumn=-1;


	public MouseInput(Game game){
		this.game=game;
	}
	




	public void mousePressed(MouseEvent e) {
		if(game.getCurrentState().equals("Menu")){
			
			if(new Rectangle(365, 700, 256, 96).intersects(new Rectangle(e.getX(),e.getY(),1,1))){
				game.setCurrentState("game");
			}
		
		
		}else if(game.getCurrentState().equals("game")){
			gameRow=e.getY() / 99;
			gameColumn=e.getX() / 99;
			game.getBoard().get(gameColumn).get(gameRow).uncover();
			
			if(game.isFirstTurn()){
				game.setFirstTurn(false);
				if(game.getBoard().get(gameColumn).get(gameRow).isBomb()){
					game.getBoard().get(gameColumn).get(gameRow).destroyBomb();
					game.getBoard().get(9).get(9).makeBomb();
					game.assignNums();
				}
			}
			
		}

	}




    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		if(game.getCurrentState().equals("Menu")){
			
			game.setStartSelect(false);
			if(new Rectangle(365, 700, 256, 96).intersects(new Rectangle(e.getX(),e.getY(),1,1))){
				game.setStartSelect(true);
			}
		
		
		}


	
	
	}
	


}