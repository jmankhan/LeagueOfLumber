package tiles;

import graphics.ImageManager;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import objectives.Tree;

public class TreeTile extends Tile{

	public TreeTile(ImageManager im) {
		super(im);
	}
	
	public void tick() {
	}
	
	public void render(Graphics g, int x, int y){
		for(Tree t:Game.getLevel().getTrees())
			if(t.getHealth()<=0)
				g.drawImage(im.deadTree, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
		g.drawImage(im.treeTile, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE, null);
	}
	
	public boolean isSolid(){
		return true;
	}
}
