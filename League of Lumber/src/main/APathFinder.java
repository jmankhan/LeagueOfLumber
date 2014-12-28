package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class APathFinder extends JPanel {
	private Point current, goal;
	private Point[][] map;
	private int[][] costs;
	private ArrayList<Point> path;
	
	private int dx,dy;
	public APathFinder(Point c, Point g) {
		current = c;
		goal	= g;
		
		dx = goal.x-current.x;
		dy = goal.y-current.y;
		
		map  = new Point [Math.abs(dx)][Math.abs(dy)];
		costs= new int	 [Math.abs(dx)][Math.abs(dy)];

		int maxX, maxY;
		if(dx>0)
			maxX=current.x+1;
		else 
			maxX=current.x-1;
		if(dy>0)
			maxY=current.y+1;
		else
			maxY=current.y-1;
	
		for(int x=0;x<dx;x++) {
			for(int y=0;y<dy;y++) {
				costs[x][y] = Math.max(x, y);
				map[x][y] = new Point(x+current.x,y+current.y);
			}
		}
		
		path = new ArrayList<Point>();
		
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int x=0;x<dx;x++) {
			for(int y=0;y<dy;y++) {
				g.drawRect((current.x+x)*20, (current.y+y)*20, 20, 20);
				g.drawString(""+costs[x][y], ((current.x+x)*20)+10, (current.y+y)*20+20);
			}
		}
		
	}
	public static void main(String args[]) {
		JFrame f = new JFrame();
		f.add(new APathFinder(new Point(10,10), new Point(15,20)));
		
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
