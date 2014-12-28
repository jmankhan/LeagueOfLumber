package net;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.Game;

public class Chat {
	private int x, y;
	private String message;
	private Font font;

	private Rectangle recievedMessages;
	private Rectangle sendMessage;

	private boolean sendMessageFocused;

	public Chat(int _x, int _y) {
		x=_x;
		y=_y;

		message="Press Enter to chat";
		font = new Font("Arial", Font.PLAIN, 14);

		recievedMessages = new Rectangle(x, y, 150, 400);
		sendMessage = new Rectangle(x, y+recievedMessages.height, recievedMessages.width, 25);

		sendMessageFocused=false;
	}

	public void tick() {}
	public void render(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.white);
		g.draw(recievedMessages);

		g.setFont(font);
		g.fill(sendMessage);
		g.setColor(Color.cyan);
		g.fill(recievedMessages);
		
		//if focused, highlight sendMessage box
		if(sendMessageFocused) {
			g.setColor(Color.DARK_GRAY);
			g.draw(sendMessage);
		}
		//draw players sendMessage
		g.setColor(Color.black);
		g.drawString(message, sendMessage.x+5, sendMessage.y+sendMessage.height-8);

		int lineCount=0;
		//draw messages recieved by server, adding line height as it goes
		for(String line:Game.getClient().getRecievedMessages().split("\n")) {
			int lineX = recievedMessages.x + 5;
			int lineY = recievedMessages.y + g.getFontMetrics().getHeight()*++lineCount;
			g.drawString(line, lineX, lineY);
		}
	}

	public void setMessage(String s) {
		message=s;
	}

	public String getMessage() {
		return message;
	}

	public void appendToMessage(char c) {
		message += c;
	}

	public boolean isFocused() {
		return sendMessageFocused;
	}

	public void setFocused(boolean focus) {
		sendMessageFocused = focus;
		if(!focus) {
			Game.getClient().sendData((message).getBytes());
			message = "Press Enter to chat";
		}
		else
			message = "";
	}
}
