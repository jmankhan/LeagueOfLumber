package tiles;

import java.awt.Graphics;

import main.Game;
import graphics.ImageManager;

public class TreeFence extends Tile
{

	public TreeFence(ImageManager im) {
		super(im);
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(im.treeFence, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
	}
	
	public boolean isSolid(){
		return true;
	}
}
