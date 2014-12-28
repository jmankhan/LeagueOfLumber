package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import players.Player;

/**
 * Me trying to do math and being bad
 * Still, it's kinda cool
 * Quick rundown of how this works:
 * knowns: start node, end node
 * steps: box around start node > find node closest to end node
 * move start node to "closest" node
 * repeat
 * This is a greedy algorithm
 * need to add: obstacle detection
 * @author jmankhan
 *
 */
public class PathFinder {
	private Point current, goal;
	
	private ArrayList<Point> toCheck, path;
	private ArrayList<Double> costs;
	
	private double lastCost = 10000;
	private int lastIndex = 0;
	
	public PathFinder(Point c, Point g) {
		current = c;
		goal = g;

		path = new ArrayList<Point>();
		path.add(current);
		
		toCheck = new ArrayList<Point>();
		toCheck.add(new Point(current.x		, current.y-1));
		toCheck.add(new Point(current.x+1	, current.y-1));
		toCheck.add(new Point(current.x+1	, current.y));
		toCheck.add(new Point(current.x+1	, current.y+1));
		toCheck.add(new Point(current.x		, current.y+1));
		toCheck.add(new Point(current.x-1	, current.y+1));
		toCheck.add(new Point(current.x-1	, current.y));
		toCheck.add(new Point(current.x-1	, current.y-1));
		
		costs = new ArrayList<Double>();
		expand();
	}

	public void expand() {
		for(Point p:toCheck) {
			costs.add(findCost(p));
		}

		for(int i=0;i<costs.size();i++) {
			if(Game.getLevel().getTile(toCheck.get(i).x, toCheck.get(i).y).isSolid())
				costs.set(i, 100000.00);
			if(costs.get(i) < lastCost) {
				lastCost = costs.get(i);
				lastIndex = i;
			}
			else if(costs.get(i) == lastCost && i!=0) {
				tieBreaker(i, i-1);
			}
		}
		move(toCheck.get(lastIndex));
	}
	
	public double findCost(Point toGo) {
		double dx = Math.abs(toGo.x-goal.x);
		double dy = Math.abs(toGo.y-goal.y);
		return dx+dy;
	}
	
	public double findCostX(Point toGo) {
		return Math.abs(toGo.x-goal.x);
	}
	
	public double findCostY(Point toGo) {
		return Math.abs(toGo.y-goal.y);
	}
	
	//favors lateral movement
	public void tieBreaker(int p1, int p2) {
		if(findCostX(toCheck.get(p1)) > findCostX(toCheck.get(p2))) {
			costs.set(p2, costs.get(p2)+.01);
		}
	}
	
	public void move(Point p) {
		path.add(p);
		current = p;
		
		toCheck = new ArrayList<Point>();
		toCheck.add(new Point(current.x		, current.y-1));
		toCheck.add(new Point(current.x+1	, current.y-1));
		toCheck.add(new Point(current.x+1	, current.y))  ;
		toCheck.add(new Point(current.x+1	, current.y+1));
		toCheck.add(new Point(current.x		, current.y+1));
		toCheck.add(new Point(current.x-1	, current.y+1));
		toCheck.add(new Point(current.x-1	, current.y))  ;
		toCheck.add(new Point(current.x-1	, current.y-1));
		
		if(current.equals(goal)) {
			return;
		}
		
		costs.clear();
		expand();
	}
	
	public void tick() {
		int scale = Game.TILESIZE*Game.SCALE;
		int toX = toCheck.get(lastIndex).x*scale;
		int toY = toCheck.get(lastIndex).y*scale;
		int pX = Game.getPlayer().getXo()+Game.getPlayer().getX();
		int pY = Game.getPlayer().getYo()+Game.getPlayer().getY();

		Game.getPlayer().move((toX-pX)/scale, (toY-pY)/scale);
	}
	
	public void render(Graphics g) {
		Graphics2D gr = (Graphics2D) g;
		gr.setColor(Color.WHITE);
	}
}