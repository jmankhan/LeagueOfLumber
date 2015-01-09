package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Game.getChat().setFocused(!Game.getChat().isFocused());
		}
		
		if(!Game.getChat().isFocused()) {
			if(e.getKeyCode() == KeyEvent.VK_UP){
				Game.getPlayer().up = true;
				Game.getPlayer().dn = false;
				Game.getPlayer().lt = false;
				Game.getPlayer().rt = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				Game.getPlayer().up = false;
				Game.getPlayer().dn = true;
				Game.getPlayer().lt = false;
				Game.getPlayer().rt = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				Game.getPlayer().up = false;
				Game.getPlayer().dn = false;
				Game.getPlayer().lt = true;
				Game.getPlayer().rt = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				Game.getPlayer().up = false;
				Game.getPlayer().dn = false;
				Game.getPlayer().lt = false;
				Game.getPlayer().rt = true;
			}

			//do not attack again if player is already attacking
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				if(!Game.getPlayer().getAttackState(0))
					Game.getPlayer().setAttackState(0, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_W) {
				if(!Game.getPlayer().getAttackState(1))
					Game.getPlayer().setAttackState(1, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_E) {
				if(!Game.getPlayer().getAttackState(2))
					Game.getPlayer().setAttackState(2, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_R) {
				if(!Game.getPlayer().getAttackState(3))
					Game.getPlayer().setAttackState(3, true);
			}
		}
		else {
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if(Game.getChat().getMessage().length()>0)
					Game.getChat().setMessage(Game.getChat().getMessage().substring(0, Game.getChat().getMessage().length()-1));
			}
			else
				Game.getChat().appendToMessage(e.getKeyChar());
		}
	}

	public void keyReleased(KeyEvent e) {
		//				if(e.getKeyCode() == KeyEvent.VK_UP){
		//					Game.getPlayer().up = false;
		//				}
		//				else if(e.getKeyCode() == KeyEvent.VK_DOWN){
		//					Game.getPlayer().dn = false;
		//				}
		//				else if(e.getKeyCode() == KeyEvent.VK_LEFT){
		//					Game.getPlayer().lt = false;
		//				}
		//				else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
		//					Game.getPlayer().rt = false;
		//				}
	}

	public void keyTyped(KeyEvent e) {
	}

}
