package com.tutorial.mario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Input.KeyInput;
import Input.MouseInput;

import com.tutorial.mario.entity.Entity;
import com.tutorial.mario.gfx.Sprite;
import com.tutorial.mario.gfx.SpriteSheet;
import com.tutorial.mario.gfx.gui.Launcher;


public class Game extends Canvas implements Runnable {// runnable allows thread
														// to work/ it is an
														// interface
	/**
	 * 
	 */
	private static final long serialVersionUID = -7096069556968696872L;
	// we extend our game to canvas so we can play
	// with screen variable such as display
	// Height and width

	public static final int WIDTH = 200, HEIGHT = WIDTH / 14 * 10;

	public static final int SCALE = 4;
	public static final String TITLE = "MARIO";

	private Thread thread;
	private boolean running = false;
	private BufferedImage image;
	
	private static int  playerX, playerY;
	
	public static int coins = 0;
	public static int  lives = 5;
	public static int deathScreenTime=0;
	
	public static boolean showDeathScreen = true;
	public static boolean gameOver = false;
	public static boolean playing = false;
	
	public static MouseInput mouse;
	

	public static Handler handler;
	public static SpriteSheet sheet;
	public static Camera cam;
	public static Launcher launcher;

	public static Sprite grass;
	public static Sprite powerUp;
	public static Sprite usedPowerUp;
	public static Sprite mushroom;
	public static Sprite upMushroom;
	public static Sprite coin;
	
    public static Sprite player[]= new Sprite[10];
  
    public static Sprite[] goomba=new Sprite[10];

	public Game() {// Constructor for our game this is where your code goes to

		// be executed
		// so when you type Game game = new Game(); it will take all
		// the code
		// from this method/constructor

		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);// this is
																		// setting
																		// the
																		// dimension
																		// for
																		// our
																		// display
																		// screen

		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);

	}

	private void init() {// initilaizing object here
		handler = new Handler();
		sheet = new SpriteSheet("/sprite_sheet.png");
		cam = new Camera();
		launcher = new Launcher();
		mouse = new MouseInput();
		
		addKeyListener(new KeyInput());// listen to the key inputs 
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		grass = new Sprite(sheet,1,1);
		powerUp = new Sprite(sheet,3,1);
		usedPowerUp = new Sprite(sheet,4,1);
		
		mushroom=new Sprite(sheet,2,1);
		upMushroom=new Sprite(sheet,6,1);
		coin=new Sprite(sheet,5,1);
		
for(int i=0;i<player.length;i++){
			
			goomba[i]= new Sprite(sheet,i+1,15);
		}
		
		for(int i=0;i<player.length;i++){
			
			player[i]= new Sprite(sheet,i+1,16);
		}
		
		try {
			image=ImageIO.read(getClass().getResource("/level.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	

		
	}

	public synchronized void start() {// synchronized means it protects the
										// thread from memory error and other
										// thread interference
		if (running)
			return;// if it is running get out this method
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();

	}

	public synchronized void stop() {
		if (!running)
			return;// if it is not running get out this method
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int ticks = 0;

		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;

			}

			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				System.out.println(frames + " Frames Per Second " + ticks
						+ " Update Per Second");
				frames = 0;

				ticks = 0;

			}

		}
		stop();
	}

	public void render() {// displaying everything on the screen means render

		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {

			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(!showDeathScreen){
			g.drawImage(Game.coin.getBufferedImage(),50, 20, 75, 75,null);
			g.setColor(Color.blue);
			g.setFont(new Font("Courier",Font.BOLD,50));
			g.drawString("x"+coins, 130, 90);
		}
		if(showDeathScreen){
			if(!gameOver){
			g.drawImage(Game.player[0].getBufferedImage(),300, 300, 100, 100,null);	
			g.setColor(Color.red);
			g.setFont(new Font("Courier",Font.BOLD,50));
			g.drawString("x"+lives+" lives", 400, 400);	
			}else{
				g.setColor(Color.red);
				g.setFont(new Font("Courier",Font.BOLD,50));
				
				g.drawString("Game Over", 250, 300);	
			}
		
		}
		
		if(playing)g.translate(cam.getX(),cam.getY());
		if(!showDeathScreen&&playing)handler.render(g);
		else if(!playing) launcher.render(g);
		g.dispose();
		bs.show();
	}

	public void tick() {// this is basically the something as updating the game
		if(playing)handler.tick();
		
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity e = handler.entity.get(i);
			if(e.getId()==ID.player){
				
				if(!e.goingDownPipe){
					
					cam.tick(e);
					};
				
				
			}
			
		}
		
		if(showDeathScreen&&!gameOver&&playing)deathScreenTime++;
		if(deathScreenTime>=180){
			if(!gameOver){
				showDeathScreen=false;
			deathScreenTime=0;
			handler.clearLevel();
			handler.createLevel(image);
			}else if(gameOver){
				
				showDeathScreen=false;
				deathScreenTime=0;
				playing=false;
				gameOver=false;
			}
			
		}
		
	}
	
	public static int getFrameWidth(){
		return WIDTH*SCALE;
	}
	public static int getFrameHeight(){
		return HEIGHT*SCALE;
	}

	public static Rectangle getVisibleArea(){
		for(int i=0;i<handler.entity.size();i++){
			Entity e = handler.entity.get(i);
			if(e.getId()==ID.player){
				if(!e.goingDownPipe){
							playerX=e.getX();
				playerY=e.getY();
				
				return new Rectangle(playerX-(getFrameWidth()/2-60),playerY-(getFrameHeight()/2-5),getFrameWidth()+10,getFrameHeight()+10);
				}
		
			}
		}
		return new Rectangle(playerX-(getFrameWidth()/2-60),playerY-(getFrameHeight()/2-5),getFrameWidth()+10,getFrameHeight()+10);
		
	}
	public static void main(String args[]) {

		Game game = new Game(); // adding content of our class Game() inside a
								// varibale call game

		JFrame frame = new JFrame(TITLE);// Creating a jfram window for our gamw
											// with a title

		frame.add(game);// grab all the content of our game and add it to our
						// frame / Jframe

		frame.pack();// pack everything together

		frame.setResizable(false);

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		game.start();

	}

}
