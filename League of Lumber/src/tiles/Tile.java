package tiles;

import graphics.ImageManager;

import java.awt.Graphics;

import main.Game;

public class Tile {

	protected ImageManager im;
	
	public static Tile grass = new GrassTile(Game.getImageManager());
	public static Tile rock = new RockTile(Game.getImageManager());
	public static Tile tree = new TreeTile(Game.getImageManager());
	public static Tile treeTrunk = new TreeTrunk(Game.getImageManager());
	public static Tile treeFence = new TreeFence(Game.getImageManager());
	
	public Tile(ImageManager im){
		this.im = im;
	}
	
	public void tick(){
		
	}
	public void render(Graphics g, int x, int y){
		
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public boolean isTree() {
		return false;
	}
}











//ISSOLID/ISBREAKABLE