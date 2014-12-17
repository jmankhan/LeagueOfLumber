package players;

import java.awt.image.BufferedImage;

public class Abilities {
	public static final int NUM_ABILITIES=4;
	
	private BufferedImage[] icons;
	
	private double[] cooldowns;
	private double[] durations;
	
	public Abilities() {
		icons = new BufferedImage[NUM_ABILITIES];
		cooldowns = new double[NUM_ABILITIES];
		durations = new double[NUM_ABILITIES];
	}
	
	public void setIcon(int index, BufferedImage icon) {
		icons[index] = icon;
	}
	
	public BufferedImage getIcon(int index) {
		return icons[index];
	}
	
	public double getCD(int index) {
		return cooldowns[index];
	}
	
	public void setCD(int index, double _cd) {
		cooldowns[index] = _cd;
	}
	
	public double getDuration(int index) {
		return durations[index];
	}
	
	public void setDuration(int index, double duration) {
		durations[index] = duration;
	}
}
