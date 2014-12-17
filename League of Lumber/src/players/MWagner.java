package players;

import graphics.ImageManager;

import java.awt.Graphics;

import weapons.MWagnerWeapon;

public class MWagner extends Player
{
	public MWagner(int x, int y, ImageManager im) {
		super(x, y, im);
		weapon = new MWagnerWeapon();
		abilities = new Abilities();
		
		interval = abilities.getCD(0);
		setupAbilities();
	}

	public void tick()
	{
		super.tick();
	}

	public void render(Graphics g)
	{
		super.render(g);
	}
	
	private void setupAbilities() {
		abilities.setIcon	 (0, im.playerAbilities[0]);
		abilities.setDuration(0, 3000);
		abilities.setCD		 (0, 10000);
		
		abilities.setIcon	 (1, im.playerAbilities[1]);
		abilities.setDuration(1, 500);
		abilities.setCD		 (1, 5000);
		
		abilities.setIcon	 (2, im.playerAbilities[2]);
		abilities.setDuration(2, 1000);
		abilities.setCD		 (2, 10000);
		
		abilities.setIcon	 (3, im.playerAbilities[3]);
		abilities.setDuration(3, 2000);
		abilities.setCD		 (3, 30000);
	}
	
	@Override
	public void doAbility(int i) {
		if( i==0 ) {
			weapon.setAttackState(true, 0);
		}
		else if( i==1 ) {
			speed = 20;
		}
		else if( i==2 ) {
			weapon.setAttackState(true, 2);
		}
	}
	
	@Override
	public void endAbility(int i) {
		if( i==0 ) {
			weapon.setAttackState(false);
		}
		else if ( i==1 ) {
			speed = 10;
		}
		else if( i== 2) {
			weapon.setAttackState(false);
			weapon.setLocation(weapon.beginx, weapon.beginy);
		}
	}
}
