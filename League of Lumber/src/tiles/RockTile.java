package tiles;

import graphics.ImageManager;

import java.awt.Graphics;

import main.Game;

public class RockTile extends Tile{

	public RockTile(ImageManager im) {
		super(im);
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(im.rockTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
	}
	
	public boolean isSolid(){
		return false;
	}

}
