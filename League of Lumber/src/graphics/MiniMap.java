package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;
import players.Player;

public class MiniMap {
	private final int scale = Game.TILESIZE*Game.SCALE;
	private final int WIDTH = 2*scale, HEIGHT = 2*scale;
	
	private int viewX, viewY, mapX, mapY;
	private Player player;
	
	public MiniMap(int placeX, int placeY) {
		mapX=placeX;
		mapY=placeY;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.cyan);
		g.fillRect(1800, 900, WIDTH, HEIGHT);
	}
}
