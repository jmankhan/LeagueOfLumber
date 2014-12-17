package main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class ClickManager implements MouseListener
{
	private int x, y;
	private Timer timer;
	private PathFinder pathFinder;
	
	public ClickManager() {
		x=-100;
		y=-100;
	}

	public void tick() {
		if(pathFinder!=null) {
			pathFinder.tick();
		}
	}
	public void render(Graphics g) {
		if(pathFinder!=null)
			pathFinder.render(g);
		int scale = Game.TILESIZE*Game.SCALE;
		g.drawImage(Game.getImageManager().clickIcon, x+(Game.getPlayer().getXo())/scale,
				y+(Game.getPlayer().getYo())/scale, null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		x =e.getX();
		y =e.getY();
		
		if(timer==null)
		{
			timer = new Timer(1500,new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					x=-100;
					y=-100;
					timer = null;
				}
			});
			timer.start();
		}
		
		int scale = Game.TILESIZE*Game.SCALE;
		int playerX = Game.getPlayer().getXo()+Game.getPlayer().getX();
		int playerY = Game.getPlayer().getYo()+Game.getPlayer().getY();

		Point playerPos = new Point(playerX/scale, playerY/scale);
		Point mousePos  = new Point((x+Game.getPlayer().getXo())/scale, (y+Game.getPlayer().getYo())/scale);
		
		pathFinder = new PathFinder(playerPos, mousePos);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
