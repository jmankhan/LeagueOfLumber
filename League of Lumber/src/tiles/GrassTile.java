package tiles;

import graphics.ImageManager;

import java.awt.Graphics;

import main.Game;

public class GrassTile extends Tile{

	public GrassTile(ImageManager im) {
		super(im);
	}

	public void tick() {
		
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(im.grassTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
	}

}
