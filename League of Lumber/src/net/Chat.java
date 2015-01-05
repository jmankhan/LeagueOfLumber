package net;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import main.Game;

public class Chat {
	private int x, y, width, height;
	private String message;
	private Font font;

	private Rectangle sendMessage;
	private LumberTextBox textBox;

	private boolean sendMessageFocused;
	private boolean visible;
	private Timer visibilityTimer;
	private final int visibilityDelay = 1000;
	
	public Chat(int _x, int _y) {
		x=_x;
		y=_y;
		width=300;
		height=400;

		message="Press Enter to chat";
		font = new Font("Arial", Font.PLAIN, 14);

		textBox = new LumberTextBox();
		sendMessage = new Rectangle(x, y+height, width, 25);

		visible=false;
		sendMessageFocused=false;
		
		visibilityTimer = null;
	}

	public void tick() {}
	public void render(Graphics gr) {
		if(visible) {
			Graphics2D g = (Graphics2D) gr;

			g.setFont(font);
			g.fill(sendMessage);

			//if focused, highlight sendMessage box
			if(sendMessageFocused) {
				g.setColor(Color.DARK_GRAY);
				g.draw(sendMessage);
			}

			//draw players sendMessage
			g.setColor(Color.black);
			g.drawString(message, sendMessage.x+5, sendMessage.y+sendMessage.height-8);

			//draw messages received from server
			textBox.setText(Game.getClient().getReceivedMessages());
			textBox.render(g);
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
			Game.getClient().sendData(message.getBytes());
			message = "Press Enter to chat";
		}
		else
			message = "";
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisibility(boolean b) {
		if(!b)
			startVisibilityTimer();
		else 
			visible=b;
	}
	
	public void startVisibilityTimer() {
		if(visibilityTimer == null) {
			visibilityTimer = new Timer();
			visibilityTimer.schedule(new TimerTask() {
				public void run() {
					visible=false;
					visibilityTimer.cancel();
				}
			}, visibilityDelay);
		}
	}
	
	private class LumberTextBox {
		private final Color backColor = new Color(0.125f, 0.125f, 0.125f, 0.5f);
		private String input;
		private StringBuilder oldText;

		public LumberTextBox() {
			input="";
			oldText = new StringBuilder();

		}

		public void setText(String text) {
			input=text;
		}

		public void appendText(String _text) {
			input+=_text;
		}

		//draw each word until end of line is reached, after which start a new line
		//start a new line if it is a new message
		public void render(Graphics g) {

			g.setColor(backColor);
			g.fillRect(x, y, width, height);

			g.setColor(Color.white);
			int sy = y;
			FontMetrics fm = g.getFontMetrics();
			StringTokenizer st = new StringTokenizer(input);
			StringBuffer oneLine = new StringBuffer();

			while(st.hasMoreTokens()) {
				String word = st.nextToken();
				
				if(word!=null) {
					if(word.equals("\\n")) {
						sy+=fm.getHeight();
						break;
					}
					
					if(fm.stringWidth(oneLine.toString() + word) < width) {
						oneLine.append(word + " ");
					}
					else {
						g.drawString(oneLine.toString(), x, sy+fm.getHeight());
						oneLine = new StringBuffer(word + " ");
						sy += fm.getHeight();
					}
				}
			}
			if(oneLine.length() > 0) {
				g.drawString(oneLine.toString(), x, sy+fm.getHeight());
			}
		}
	}
}