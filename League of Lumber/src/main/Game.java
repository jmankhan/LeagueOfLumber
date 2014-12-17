package main;

import graphics.ImageLoader;
import graphics.ImageManager;
import graphics.SpriteSheet;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import players.MWagner;
import players.Player;
import levels.Level;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 400, HEIGHT = 400, SCALE = 2, TILESIZE = 32;
	public static double FPS = 60D;
	public static boolean running = false;
	public Thread gameThread;

	private BufferedImage spriteSheet;
	private static ImageManager im;

	private static Player player;
	private static Level level;
	private static ClickManager clickManager;

	private static Rectangle[] abilityHolder;
	
	public void init(){

		ImageLoader loader = new ImageLoader();
		spriteSheet = loader.load("/sprites.png");
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		im = new ImageManager(ss);

		createAbilityHolder();
		player = new MWagner(WIDTH * SCALE / 2, HEIGHT * SCALE / 2 - 2*TILESIZE, im);
		clickManager = new ClickManager();
		level = new Level(loader.load("/level.png"));
		
		addKeyListener(new KeyManager());
		addMouseListener(clickManager);
		
	}

	public synchronized void start(){
		if(running)return;
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	public synchronized void stop(){
		if(!running)return;
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = FPS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				delta--;
			}
			render();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}
	public void tick(){
		player.tick();
		level.tick();
	}
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs ==  null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//RENDER HERE
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		level.render(g);
		player.render(g);
		clickManager.render(g);
		//END RENDER
		g.dispose();
		bs.show();
	}

	public static void main(String[] args){
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		JFrame frame = new JFrame("Tile RPG");
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.setIgnoreRepaint(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("res/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(0, 0), "img");
		game.setCursor (c);

		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(frame);
		game.setGameWidth(device.getDefaultConfiguration().getBounds().width);
		game.setGameHeight(device.getDefaultConfiguration().getBounds().height);
		game.start();
		game.requestFocusInWindow();
	}
	
	public void createAbilityHolder() {
		int scale = Game.TILESIZE * Game.SCALE;
		abilityHolder = new Rectangle[4];
		abilityHolder[0] = new Rectangle(Game.WIDTH/2 - 3*scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[1] = new Rectangle(Game.WIDTH/2 - scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[2] = new Rectangle(Game.WIDTH/2 + scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[3] = new Rectangle(Game.WIDTH/2 + 3*scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
	}

	public static Level getLevel(){
		return level;
	}

	public static Player getPlayer(){
		return player;
	}

	public static ImageManager getImageManager(){
		return im;
	}

	public void setGameWidth(int w) {
		WIDTH = w;
	}

	public void setGameHeight(int h) {
		HEIGHT = h;
	}

	public static ClickManager getClickManager() {
		return clickManager;
	}
	public static Rectangle[] getAbilityHolder() {
		return abilityHolder;
	}
}