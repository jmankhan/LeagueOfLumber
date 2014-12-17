package main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class ClickManager implements MouseListener
{
	private int x, y;
	private Timer timer;
	
	public ClickManager() {
		x=-100;
		y=-100;
	}

	public void render(Graphics g) {
		g.drawImage(Game.getImageManager().clickIcon, x, y, null);
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
		x=e.getXOnScreen()-Game.TILESIZE*Game.SCALE;
		y=e.getYOnScreen()-Game.TILESIZE*Game.SCALE;
		if(timer==null)
		{
			timer = new Timer(500,new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					x=-100;
					y=-100;
					timer = null;
				}
			});
			timer.start();
		}
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
