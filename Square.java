import java.awt.Color;
import java.awt.Graphics;


public class Square {

	/**
	 * @param args
	 */
	private boolean isBomb;
	private int num;
	private boolean uncovered;
	private Game game;
	
	public Square(){
		uncovered=false;
		isBomb=false;
		num=0;
	}
	
	public Square(boolean isBomb, int num, Game game){
		this.isBomb=isBomb;
		this.num=num;
		this.game=game;
	}
	
	public boolean isBomb(){
		return isBomb;
	}
	public void makeBomb(){
		isBomb=true;
	}
	public void destroyBomb(){
		isBomb=false;
	}
	public void setBombNum(int num){
		this.num=num;
	}
	public int getBombNum(){
		return num;
	}
	public void render(Graphics g, float x, float y){
		if(isBomb && uncovered){
			g.setColor(Color.blue.brighter().brighter().brighter());
			g.fillRect((int)x, (int)y, 99, 99);
		}else if(!isBomb && !uncovered){
			g.setColor(Color.gray);
			g.fillRect((int)x, (int)y, 99, 99);
		}else if(!isBomb){
			g.setColor(Color.gray.darker().darker());
			g.fillRect((int)x, (int)y, 99, 99);
			g.setColor(Color.red);
			if(num!=0)
				g.drawString(""+num, (int)x+50, (int)y+50);
		}else{
			g.setColor(Color.gray);
			g.fillRect((int)x, (int)y, 99, 99);
		}
		g.setColor(Color.red);



	
		
	}
	public void uncover(){
		uncovered=true;
		game.addUncover();
	}
	public boolean isUncovered(){
		return uncovered;
	}
	public void cover(){
		uncovered=false;
	}
	

}
