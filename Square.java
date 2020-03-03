import java.awt.Color;
import java.awt.Graphics;


public class Square {

	/**
	 * @param args
	 */
	private boolean isBomb;
	private int num;
	
	public Square(){
		isBomb=false;
		num=0;
	}
	
	public Square(boolean isBomb, int num){
		this.isBomb=isBomb;
		this.num=num;
	}
	
	public boolean isBomb(){
		return isBomb;
	}
	public void makeBomb(){
		isBomb=true;
	}
	public void setBombNum(int num){
		this.num=num;
	}
	public void render(Graphics g, float x, float y){
		g.setColor(Color.gray);

		if(isBomb)
			g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, 99, 99);
		
	}
	

}
