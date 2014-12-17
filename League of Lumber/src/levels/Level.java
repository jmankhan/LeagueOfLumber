package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import objectives.Tree;
import tiles.Tile;

public class Level {
	
	private int[][] tiles;
	private int w, h;
	
	private ArrayList<Tree> trees;
	private Rectangle[] abilityHolder;
	
	public Level(BufferedImage levelImage){
		w = levelImage.getWidth();
		h = levelImage.getHeight();
		
		trees = new ArrayList<Tree>();
		loadLevel(levelImage);
		
		abilityHolder = Game.getAbilityHolder();
	}
	
	public void loadLevel(BufferedImage levelImage){
		tiles = new int[levelImage.getWidth()][levelImage.getHeight()];
		for(int y = 0;y < levelImage.getHeight();y++){
			for(int x = 0;x < levelImage.getWidth();x++){
				Color c = new Color(levelImage.getRGB(x, y));
				String h = String.format("%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
				
				switch(h){
				case "00ff00"://GRASS TILE - 1
					tiles[x][y] = 1;
					break;
				case "000000"://ROCK TILE - 2
					tiles[x][y] = 2;
					break;
				case "ff0000"://TREE TILE - 3
					tiles[x][y] = 3;
					trees.add(new Tree(x,y));
					break;
				case "ff0100"://TREETRUNK TILE - 4
					tiles[x][y] = 4;
					break;
				case "0000ff"://TREEFENCE TILE - 5
					tiles[x][y] = 5;
					break;
				default:
					tiles[x][y] = 1;
					break;
				}
			}
		}
	}
	
	public void tick() {
		for(Tree t:trees) {
			t.tick();
		}
	}
	public void render(Graphics gr){
		Graphics2D g = (Graphics2D) gr;
		
		int scale = Game.TILESIZE*Game.SCALE;
		
		int xo = Game.getPlayer().getXo();
		int yo = Game.getPlayer().getYo();
		
		int x0 = Math.max(xo / (scale), 0);
		int y0 = Math.max(yo / (scale), 0);
		int x1 = Math.min((xo + Game.WIDTH * Game.SCALE) / (scale) + 1, w);
		int y1 = Math.min((yo + Game.HEIGHT * Game.SCALE) / (scale) + 1, h);

		//map
		for(int y = y0;y < y1;y++){
			for(int x = x0;x < x1;x++){
				getTile(x, y).render(g, x * scale - xo, y * scale - yo);
			}
		}
		
		//trees
		for(Tree t:trees) {
			t.render(g);
		}
		
		//hud
		for(int i=0;i<4;i++) {
			g.drawImage(Game.getPlayer().getAbilities().getIcon(i),(int) abilityHolder[i].getX(), 
					(int)abilityHolder[i].getY(), (int)abilityHolder[i].getWidth(), 
					(int)abilityHolder[i].getHeight(), null);
			g.draw(abilityHolder[i]);
		}
		
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= w || y >= h)
			return Tile.grass;
		
		switch(tiles[x][y]){
		case 1:
			return Tile.grass;
		case 2:
			return Tile.rock;
		case 3:
			//Tile.treeTile handled by Tree class, will return as grass
		case 4:
			//Tile.treeTrunk handled by Tree class, will return as grass
		case 5:
			return Tile.treeFence;
		default: return Tile.grass;
		}
	}

	public ArrayList<Tree> getTrees() {
		return trees;
	}
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
}