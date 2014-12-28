package graphics;

import java.awt.Rectangle;

import main.Game;
import players.Abilities;

public class HUD {
	private final int scale = Game.TILESIZE*Game.SCALE;
	
	private static Rectangle[] abilityHolder, itemHolder;
	private static Rectangle playerIconHolder;
	
	public HUD() {
		abilityHolder 	= new Rectangle[Abilities.NUM_ABILITIES];
		itemHolder		= new Rectangle[3];
		playerIconHolder= new Rectangle();
		
		createAbilityHolder();
		createPlayerIconHolder();
		createItemHolder();
	}
	
	
	public void createAbilityHolder() {
		int scale = Game.TILESIZE * Game.SCALE;
		abilityHolder[0] = new Rectangle(Game.WIDTH/2 - 3*scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[1] = new Rectangle(Game.WIDTH/2 - scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[2] = new Rectangle(Game.WIDTH/2 + scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
		abilityHolder[3] = new Rectangle(Game.WIDTH/2 + 3*scale, Game.HEIGHT-2*scale, 2*scale, 2*scale);
	}
	
	public void createItemHolder() {
		itemHolder[0] = new Rectangle(playerIconHolder.x+playerIconHolder.width, playerIconHolder.y, scale, scale);
		itemHolder[1] = new Rectangle(playerIconHolder.x+playerIconHolder.width+scale, playerIconHolder.y, scale, scale);
		itemHolder[2] = new Rectangle(playerIconHolder.x+playerIconHolder.width+2*scale, playerIconHolder.y, scale, scale);
	}
	
	public void createPlayerIconHolder() {
		
		playerIconHolder = new Rectangle(0, Game.HEIGHT-2*scale-30, 2*scale, 2*scale);
	}
	
	public Rectangle[] getAbilityHolder() {
		return abilityHolder;
	}
	
	public Rectangle[] getItemHolder() {
		return itemHolder;
	}
	
	public Rectangle getPlayerIconHolder() {
		return playerIconHolder;
	}
}
