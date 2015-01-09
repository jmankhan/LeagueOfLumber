package graphics;

import java.awt.image.BufferedImage;

import main.Game;

public class ImageManager {
	private final int PLAYER_ROW = 2, RES_ROW = 0, SYS_ROW = 1;
	
	public BufferedImage map;
	
	public BufferedImage grassTile, rockTile, treeTile, treeTrunk, treeFence, deadTree;
	public BufferedImage[] playerUp, playerDown, playerLeft, playerRight, playerAbilities;
	
	public BufferedImage clickIcon, playerWeapon, playerIcon;

	public ImageManager(SpriteSheet ss){
		playerUp = new BufferedImage[3];
			playerUp[0] = ss.crop(3, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerUp[1] = ss.crop(4, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerUp[2] = ss.crop(5, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerDown = new BufferedImage[3];
			playerDown[0] = ss.crop(0, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerDown[1] = ss.crop(1, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerDown[2] = ss.crop(2, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerLeft = new BufferedImage[1];
			playerLeft[0] = ss.crop(6, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerRight = new BufferedImage[1];
			playerRight[0] = ss.crop(7, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerAbilities = new BufferedImage[4];
			playerAbilities[0] = ss.crop(8, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerAbilities[1] = ss.crop(9, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerAbilities[2] = ss.crop(10, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
			playerAbilities[3] = ss.crop(11, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerIcon = ss.crop(12, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		playerWeapon = ss.crop(8, PLAYER_ROW, Game.TILESIZE, Game.TILESIZE);
		
		grassTile	= ss.crop(0, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		rockTile	= ss.crop(1, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		treeTile	= ss.crop(2, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		treeTrunk	= ss.crop(3, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		treeFence	= ss.crop(5, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		deadTree	= ss.crop(6, RES_ROW, Game.TILESIZE, Game.TILESIZE);
		clickIcon	= ss.crop(0, SYS_ROW, Game.TILESIZE, Game.TILESIZE);
	}
	
}
