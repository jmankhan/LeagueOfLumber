package tiles;

import graphics.ImageManager;

import java.awt.Graphics;

import main.Game;

public class TreeTrunk extends Tile {

	public TreeTrunk(ImageManager im) {
		super(im);
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(im.treeTrunk, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
	}
	
	public boolean isSolid(){
		return true;
	}
}