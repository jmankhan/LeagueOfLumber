package players;

import graphics.Animation;
import graphics.ImageManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.TimerTask;

import main.Game;
import main.PathFinder;
import weapons.Weapon;

public class Player {

	protected int x, y, xo, yo, xs, ys, speed, angle[];
	protected ImageManager im;
	public boolean up = false, dn = false, lt = false, rt = false;
	protected final int SIZE = Game.TILESIZE;
	protected double interval;
	protected long timer, now, lastTime;

	protected Polygon[] radialIndicator, tempIndicator;

	protected Rectangle[] tempAbilityHolder;
	protected Rectangle[] tempItemHolder;
	protected Rectangle tempIconHolder;

	protected Animation upAnimation, downAnimation, leftAnimation, rightAnimation;
	protected Weapon weapon;
	protected Abilities abilities;

	protected boolean[] att = new boolean[Abilities.NUM_ABILITIES];
	protected boolean[] attCD = new boolean[Abilities.NUM_ABILITIES];
	protected boolean[] cdStart = new boolean[Abilities.NUM_ABILITIES];

	public Player(int x, int y, ImageManager im){
		this.x = x/Game.SCALE;
		this.y = y/Game.SCALE;
		xo = 1000;
		yo = 7200;
		xs = 0;
		ys = 0;
		speed = 10;
		this.im = im;

		interval = 0;
		timer=0;
		now=0;
		lastTime = System.currentTimeMillis();

		tempAbilityHolder = Game.getHUD().getAbilityHolder();
		tempItemHolder = Game.getHUD().getItemHolder();
		tempIconHolder = Game.getHUD().getPlayerIconHolder();

		radialIndicator = new Polygon[Abilities.NUM_ABILITIES];
		tempIndicator = new Polygon[Abilities.NUM_ABILITIES];
		angle = new int[Abilities.NUM_ABILITIES];

		for(int i=0;i<radialIndicator.length;i++) {
			radialIndicator[i] = new Polygon();
			tempIndicator[i] = new Polygon();
			angle[i]=0;

			for(int j=0;j<360;j+=3) {
				int toX = (int)(tempAbilityHolder[i].getCenterX()+Math.cos(Math.toRadians(j-90))*Game.TILESIZE*Game.SCALE);
				int toY = (int)(tempAbilityHolder[i].getCenterY()+Math.sin(Math.toRadians(j-90))*Game.TILESIZE*Game.SCALE);
				radialIndicator[i].addPoint(toX, toY);
			}
		}

		upAnimation		= new Animation(im.playerUp,	500);
		downAnimation	= new Animation(im.playerDown,	500);
		leftAnimation	= new Animation(im.playerLeft,	500);
		rightAnimation	= new Animation(im.playerRight, 500);

		for(int i=0;i<Abilities.NUM_ABILITIES;i++) {
			att[i]=false;
			attCD[i]=false;
			cdStart[i]=false;
		}
	}

	public void tick(){
		//Calculate
		xs = 0;
		ys = 0;
		if(up){
			ys -= speed;
		}else if(dn){
			ys += speed;
		}
		if(lt){
			xs -= speed;
		}else if(rt){
			xs += speed;
		}
		//Actually Move
		move(xs, ys);

		upAnimation.tick();
		downAnimation.tick();
		leftAnimation.tick();
		rightAnimation.tick();

		if(weapon!=null)
			weapon.tick();

		for(int i=0;i<Abilities.NUM_ABILITIES;i++) {
			//if attack key is pressed and player is not currently attacking
			if(att[i]&&!attCD[i]) {
				attCD[i]=true; //do not attack next loop
				doAbility(i);

				final int increment = i;
				java.util.Timer timer1 = new java.util.Timer();
				timer1.schedule(new TimerTask() {
					public void run() {
						att[increment]=false;
						cdStart[increment]=true;
						endAbility(increment);
					}
				}, (int)abilities.getDuration(i));
			}

			//if ability is on cooldown
			if(cdStart[i]) {
				//start radial cooldown indicator
				now = System.currentTimeMillis();
				timer += now-lastTime;
				lastTime = now;
				if(timer >= interval/radialIndicator[i].npoints) {
					timer = 0;
					angle[i]++;
				}
				//if end of cd reached
				if(angle[i]>=120) {
					attCD[i]		 = false;			//attack is off cd
					cdStart[i]		 = false;			//ability is off cd
					tempIndicator[i] = new Polygon();	//new radial indicator
					angle[i]		 = 0;				//reset angles
					timer			 = 0;
				}
			}
		}
	}

	public void move(int xs, int ys){
		if(!collision(xs, 0)){
			xo += xs;
		}
		if(!collision(0, ys)){
			yo += ys;
		}
	}

	private boolean collision(int xs, int ys){
		if(Game.getLevel().getTile((xo + xs + x) / (Game.TILESIZE * Game.SCALE), (yo + ys + y) / (Game.TILESIZE * Game.SCALE)).isSolid())
			return true;
		if(Game.getLevel().getTile((xo + xs + x + SIZE * Game.SCALE - 1) / (Game.TILESIZE * Game.SCALE), (yo + ys + y) / (Game.TILESIZE * Game.SCALE)).isSolid())
			return true;
		if(Game.getLevel().getTile((xo + xs + x) / (Game.TILESIZE * Game.SCALE), (yo + ys + y + SIZE * Game.SCALE - 1) / (Game.TILESIZE * Game.SCALE)).isSolid())
			return true;
		if(Game.getLevel().getTile((xo + xs + x + SIZE * Game.SCALE - 1) / (Game.TILESIZE * Game.SCALE), (yo + ys + y + SIZE * Game.SCALE - 1) / (Game.TILESIZE * Game.SCALE)).isSolid())
			return true;

		return false;
	}

	public void render(Graphics g){
		if(up)
			upAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
		else if(dn)
			downAnimation.render(g, x, y, Game.TILESIZE * Game.SCALE, Game.TILESIZE * Game.SCALE);
		else if(rt)
			rightAnimation.render(g, x, y, Game.TILESIZE*Game.SCALE, Game.TILESIZE*Game.SCALE);
		else if(lt)
			leftAnimation.render(g, x, y, Game.TILESIZE*Game.SCALE, Game.TILESIZE*Game.SCALE);
		else
			g.drawImage(im.playerDown[0], x, y, Game.TILESIZE*Game.SCALE, Game.TILESIZE*Game.SCALE, null);

		if(weapon!=null)
			weapon.render(g);

		//draw radial indicator
		for(int i=0;i<Abilities.NUM_ABILITIES;i++) {
			if(cdStart[i]) {
				Graphics2D gr = (Graphics2D) g;
				gr.setColor(Color.white);

				int[] xpoints = radialIndicator[i].xpoints;
				int[] ypoints = radialIndicator[i].ypoints;
				tempIndicator[i].addPoint(xpoints[(int)angle[i]], ypoints[(int)angle[i]]);

				gr.fill(tempIndicator[i]);

				gr.setColor(Color.red);
				gr.drawString(new DecimalFormat("#.##").format(angle[i]/radialIndicator[i].npoints), tempAbilityHolder[i].x+Game.TILESIZE*Game.SCALE-10,
						tempAbilityHolder[i].y+Game.TILESIZE*Game.SCALE);
			}
		}

		Graphics2D gr = (Graphics2D) g;
		//hud
		//ability holder
		for(int i=0;i<tempAbilityHolder.length;i++) {
			g.drawImage(abilities.getIcon(i),(int) tempAbilityHolder[i].getX(), 
					(int)tempAbilityHolder[i].getY(), (int)tempAbilityHolder[i].getWidth(), 
					(int)tempAbilityHolder[i].getHeight(), null);
			gr.draw(tempAbilityHolder[i]);
		}
		//item holder
		for(int i=0;i<tempItemHolder.length;i++) {
			gr.draw(tempItemHolder[i]);
		}
		//player icon
		g.drawImage(im.playerIcon, tempIconHolder.x, tempIconHolder.y, tempIconHolder.width, tempIconHolder.height, null);
	}

	protected void doAbility(int i) {

	}

	protected void endAbility(int i) {

	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public int getXo(){
		return xo;
	}

	public int getYo(){
		return yo;
	}

	public int getSpeed() {
		return speed;
	}
	public Abilities getAbilities() {
		return abilities;
	}

	public void setAttackState(int index, boolean state) {
		att[index] = state;
	}

	public boolean getAttackState(int index) {
		return att[index];
	}
}