package weapons;

import graphics.ImageManager;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import objectives.Tree;

public abstract class Weapon {
	public int beginx, beginy;
	
	protected ImageManager imageManager;
	protected BufferedImage image;
	protected int x, y, ax, ay, att;
	protected Rectangle hitbox;
	protected boolean isAtt;
	
	protected int damage;
	protected double ats;
	
	public Weapon() {
		imageManager = Game.getImageManager();
		image = null;

		//spawn weapon in front of player
		beginx = x = Game.WIDTH/Game.SCALE+(Game.TILESIZE*Game.SCALE);
		beginy = y = Game.HEIGHT/Game.SCALE;
		hitbox = new Rectangle(-100,-100,Game.TILESIZE*Game.SCALE, Game.TILESIZE*Game.SCALE); //one tile size by default
		ax = 0;
		ay = 0;
		att = 0;
		
		//100 base damage
		damage = 100;
		ats = 5.0;
		
		//is not attacking by default
		isAtt = false;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public boolean checkHit() {
		ArrayList<Tree> trees = Game.getLevel().getTrees();
		for(Tree t: trees) {
			if(hitbox.intersects(t.getHitbox())) {
				if(t.canDamage()) {
					t.setHealth(-damage);
					t.setDamageState(false);
				}
				
				return true;
			} else t.setDamageState(true);
		}
		return false;
	}
	
	public void setAttackState(boolean b) {
		isAtt = b;
	}
	
	public void setAttackState(boolean b, int att) {
		isAtt=b;
		this.att=att;
	}
	
	public void setLocation(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
