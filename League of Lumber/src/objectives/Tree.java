package objectives;

import graphics.ImageManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Tree {
	private int health = 1000;
	private Rectangle hitbox;
	private ImageManager im;
	
	private boolean canDamage;
	private int x, y;

	public Tree(int x, int y) {
		this.x=x;
		this.y=y;

		canDamage = true;
		int scale = Game.TILESIZE*Game.SCALE;
		hitbox = new Rectangle(x*scale-Game.getPlayer().getXo(), y*scale+Game.getPlayer().getYo()
				, scale, 2*scale);
		
		im = Game.getImageManager();
	}

	public void tick() {
		hitbox.setLocation(x*Game.TILESIZE*Game.SCALE-Game.getPlayer().getXo(), y*Game.TILESIZE*Game.SCALE-Game.getPlayer().getYo());
	}

	public void render(Graphics g) {
		//draw tree
		int scale = Game.TILESIZE*Game.SCALE;
		if(health > 0) {
			//draw hp bar
			g.setColor(Color.red);
			int toX = x*scale-Game.getPlayer().getXo();
			int toY = y*scale-Game.getPlayer().getYo()-scale/4;

			g.fillRect(toX, toY, 4*health/scale, scale/4);

			g.setColor(Color.white);
			g.drawString(""+health, toX,toY);
			
			//draw tree top to bottom
			g.drawImage(im.treeTile,  x*scale-Game.getPlayer().getXo(), y*scale-Game.getPlayer().getYo(), scale, scale, null);
			g.drawImage(im.treeTrunk, x*scale-Game.getPlayer().getXo(), y*scale-Game.getPlayer().getYo()+scale, scale, scale, null);
		}
		//if tree dies
		else  {
			g.drawImage(im.grassTile, x*scale-Game.getPlayer().getXo(), y*scale-Game.getPlayer().getYo(), scale, scale, null);
			g.drawImage(im.deadTree,  x*scale-Game.getPlayer().getXo(), y*scale-Game.getPlayer().getYo()+scale, scale, scale, null);
		}
	}

	public void setHealth(int delta) {
		health += delta;
	}

	public int getHealth() {
		return health;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public boolean canDamage() {
		return canDamage;
	}

	public void setDamageState(boolean state) {
		canDamage = state;
	}
}