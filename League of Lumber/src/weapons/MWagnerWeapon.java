package weapons;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;

public class MWagnerWeapon extends Weapon{

	private int angle, interval;
	private long timer, now, lastTime;

	public int beginx, beginy;
	public MWagnerWeapon()
	{
		super();
		image = imageManager.playerWeapon;

		interval = 10;
		angle	 = 0;
		timer 	 = 0;
		now 	 = 0;
		lastTime = System.currentTimeMillis();
	}

	public void tick() {
		//ability 1 spins weapon around player
		if(isAtt&&att==0) {
			int scale = Game.TILESIZE*Game.SCALE;
			ax = (int)(3*scale/4+Math.cos(Math.toRadians(angle))*scale);
			ay = (int)(scale/2+Math.sin(Math.toRadians(angle))*scale);

			now = System.currentTimeMillis();
			timer += now - lastTime;
			lastTime = now;

			if(timer >= interval){
				angle+=ats;
				timer = 0;
			}
			hitbox.setLocation(x-ax, y-ay);
		} 

		//ability 2 does not use weapon
		//ability 3 throws skateboard in direction player is facing for a duration of time
		else if(isAtt&&att==2) {
			ax = ay = 0;
			if(Game.getPlayer().up)
				y-=ats;
			else if(Game.getPlayer().dn) 
				y+=ats;
			if(Game.getPlayer().rt)
				x+=ats;
			else if(Game.getPlayer().lt)
				x-=ats;
			hitbox.setLocation(x-ax,y-ay);
		}
		
		else hitbox.setLocation(-2000,-2000);
		checkHit();
	}

	public void render(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		if(isAtt) {
			int scale = Game.TILESIZE*Game.SCALE;
			g.drawImage(image,x-ax,y-ay, scale, scale, null);
		} else {
			ax=0;
			ay=0;
		}
	}
}
