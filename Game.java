import java.awt.Canvas;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;



/**
 * @author cut05430
 *
 */
public class Game extends Canvas implements Runnable{


    /**
     *
     */
    private static final long serialVersionUID  = -1300776496297261616L;


    // height is 960
    public static final float WIDTH = 905, HEIGHT = 930;

    //temp

    private transient Thread thread;
    private boolean running = false;
    

    private Window window;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private Textures tex;
    private boolean startSelect;
    private String currentState;
    private ArrayList<ArrayList<Square>> board;
    private Random rand;
    private ArrayList<ArrayList<Integer>> squareDefiner;
    private boolean firstTurn;
    Scanner scan;
    private int uncoverCount;

    public Game() {
    	uncoverCount=0;
		scan=new Scanner(System.in);
    	keyInput = new KeyInput(this);
    	mouseInput = new MouseInput(this);

        this.addKeyListener(keyInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        window = new Window(WIDTH, HEIGHT, "Sweep my Mine", this);
        currentState="Menu";
        startSelect=false;
        board=new ArrayList<ArrayList<Square>>();
        rand=new Random();
        
        for(int i = 0; i<9; i++){
        	ArrayList<Square> temp = new ArrayList<Square>(); 
        	for(int j = 0; j<9; j++){
        		temp.add(new Square(false,0,this));
        	}
        	board.add(temp);
        }
        assignBombs();
        assignNums();
        firstTurn=true;
        printBombNums();


    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }



    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {

        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(running) {
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
            }
        }
        stop();
    }


    private void tick() {
        try{
            if(uncoverCount>=83)
            	currentState="end";

        }catch(Exception e){
            e.printStackTrace();
        }






    }

   

	private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();


        // renders the board
        Graphics2D g2d = (Graphics2D)g;
    	
    	
    	tex=new Textures();
    	
    	
    	if(currentState.equals("Menu")){
    		g.drawImage(tex.Menu,0,0,900,900,null);
        	
        	
        	g.setColor(Color.black);
        	g.drawRect(365, 700, 256, 96);
        	g.drawRect(366,701,256,96);
        	g.drawRect(367,703,256,96);
        	g.drawRect(367,703,256,96);
        	
        	
        	if(startSelect){
        		g.setColor(Color.red);
        		g.fillRect(365, 700, 256, 96);
        	}
        	g.setColor(Color.black);
        	
        	
        	g.setFont(new Font("Serif", Font.BOLD, 45));
        	g.drawString("Start",444, 755);
    	}else if(currentState.equals("game")){
    		
    		//////////////////////////////////////////////////////////////
    		//////////////////////////////////////////////////////////////
    		//////////////////////////////////////////////////////////////
    		
    		// rendering the board
    		
    		g.setColor(Color.black);
    		g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);
    		for(int i = 0; i<board.size(); i++){
    			for(int j = 0; j<board.get(0).size(); j++){
    				
    				board.get(i).get(j).render(g,99*i+(i*1),99*j+(j+1));
    			}
    		}
    		// end of board rendering
    		
    		
    	}else if(currentState.equals("end")){
			g.setColor(Color.black);
			g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);
			g.setColor(Color.white);
			g.setFont(new Font("Serif", Font.BOLD, 45));

			if(uncoverCount>=83)
				g.drawString("You won!!!",370,400);
			else
				g.drawString("You lost!!!", 370, 400);
		}
    	
    	
        	
    	 g.dispose();
         bs.show();
        	
        	
        	
        }


       

	

    

    
	
	

	public static void main(String[] args) {
        new Game();

    }

	public KeyInput getKeyInput() {
		return keyInput;
	}

	public void setKeyInput(KeyInput keyInput) {
		this.keyInput = keyInput;
	}

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public void setMouseInput(MouseInput mouseInput) {
		this.mouseInput = mouseInput;
	}

	public boolean isStartSelect() {
		return startSelect;
	}

	public void setStartSelect(boolean startSelect) {
		this.startSelect = startSelect;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
	public void assignBombs(){


		for(int i = 0; i<10; i++){
			int random = rand.nextInt(9);
			int random2 = rand.nextInt(9);
			if(!board.get(random).get(random2).isBomb() && !(random==9 && random2==9)){
				board.get(random).get(random2).makeBomb();
			}else{
				i--;
			}
			
		}
	}
	
	public void assignNums(){

		for(int i = 0; i<board.size(); i++){
			for(int j = 0; j<board.get(0).size(); j++){
				if(!board.get(i).get(j).isBomb()){
					// checks for bombs
					int bombCount=0;
					try{
						if(board.get(i-1).get(j).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i-1).get(j+1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i).get(j+1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i+1).get(j+1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i+1).get(j).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i+1).get(j-1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i).get(j-1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					try{
						if(board.get(i-1).get(j-1).isBomb()){
							bombCount++;
						}
					}catch(Exception e){
					}
					
					board.get(i).get(j).setBombNum(bombCount);
					
				}
				if(board.get(i).get(j).isBomb())
					board.get(i).get(j).setBombNum(-1);
			}
		}
	}
	
	public void printBombNums(){

		for(int i = 0; i<board.size(); i++){
			for(int j = 0; j<board.get(0).size(); j++){
				if(board.get(i).get(j).isBomb())
					System.out.print("B");
				else
					System.out.print(board.get(i).get(j).getBombNum());
			}
			System.out.println();
		}
	}

	public ArrayList<ArrayList<Square>> getBoard() {
		return board;
	}

	public void setBoard(ArrayList<ArrayList<Square>> board) {
		this.board = board;
	}
	public boolean isFirstTurn() {
		return firstTurn;
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}
	
	public void firstTurnException(int column, int row, Square selected){
		if(selected.getBombNum()==0){
			FLOOD(column, row);
		}

		ArrayList<ArrayList<Square>> temp = new ArrayList<>();


		for(int i = 0; i<9; i++)
			temp.add(new ArrayList<Square>());

		for(int i = 0; i<board.size(); i++){
			for(int j = 0; j<board.get(0).size(); j++){
				if(board.get(i).get(j).isUncovered())
					temp.get(i).add(board.get(i).get(j));
				else
					temp.get(i).add(new Square());
			}
		}

		for(int i = 0; i<board.size(); i++){
			for(int j = 0; j<board.get(0).size(); j++){
				if(temp.get(i).get(j).isUncovered()){

					if(i+1 < 9){
						if(!board.get(i+1).get(j).isUncovered())
							board.get(i+1).get(j).uncover();
					}

					if(i-1 > -1)
						if(!board.get(i-1).get(j).isUncovered())

							board.get(i-1).get(j).uncover();
					if(j+1 < 9)
						if(!board.get(i).get(j+1).isUncovered())

							board.get(i).get(j+1).uncover();
					if(j-1 > -1)
						if(!board.get(i).get(j-1).isUncovered())

							board.get(i).get(j-1).uncover();


				}

			}
		}




	}
	
	public void FLOOD(int column, int row){

		System.out.println("call");
		if(board.get(column).get(row).isUncovered()){
			System.out.println("jaja");
			return;
		
		}else if(board.get(column).get(row).getBombNum()!=0){
			System.out.println("jaja part 23");
			return;
		

		}else{
			board.get(column).get(row).uncover();

		}

		System.out.println("here");
		if((column+1 > -1 && column+1 < 9 && row >-1 && row < 9)){

			FLOOD(column+1, row);


		}
		if((column-1 > -1 && column-1 < 9 && row >-1 && row < 9)){
			FLOOD(column-1, row);


		}
		if((column > -1 && column < 9 && row+1 >-1 && row+1 < 9)){

			FLOOD(column, row+1);


		}
		if((column > -1 && column < 9 && row-1 >-1 && row-1 < 9)){

			FLOOD(column, row-1 );


		}
	}

	public void addUncover(){
    	uncoverCount++;
	}
	
	
	

}