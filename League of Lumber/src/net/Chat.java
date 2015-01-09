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

/**
 * Handles user side of chat messages, including displaying text, taking input and sending it to client
 * @author jmankhan
 *
 */
public class Chat {
	private int x, y, width, height;
	private String message;
	private Font font;

	private Rectangle sendMessage;
	private LumberTextBox textBox;

	private boolean sendMessageFocused;
	private boolean visible;
	private Timer visibilityTimer;
	private final int visibilityDelay = 2000;

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

	public void tick() {} //for consistency
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

	
	//focus and display the chatbox when enter is pressed
	//when user is done typing (enter is pressed again), unfocus, and undisplay after a delay
	//if they begin typing after focus is lost but before delay kicks in, cancel the invisibility timer
	public void setFocused(boolean focus) {
		sendMessageFocused = focus;
		if(!focus) {
			Game.getClient().sendData(message.getBytes());
			message = "Press Enter to chat";
			startVisibilityTimer();
		}
		else {
			visible=true;
			message = "";
			if(visibilityTimer != null)
				visibilityTimer.cancel();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	//make textbox invisible after a delay when user is done typing
	//cancel the timer if user wants to type again before it goes invisi
	public void setVisibility(boolean b) {
		if(!b)
			startVisibilityTimer();
		else {
			if(visibilityTimer!=null)
				visibilityTimer.cancel();

			visible=b;
		}
	}

	//make text invisible after a delay
	public void startVisibilityTimer() {
		visibilityTimer = new Timer();
		visibilityTimer.schedule(new TimerTask() {
			public void run() {
				visible=false;
				visibilityTimer.cancel();
			}
		}, visibilityDelay);
	}

	//this took forever to make
	private class LumberTextBox {
		private final Color backColor = new Color(0.125f, 0.125f, 0.125f, 0.5f); //translucent background color
		private String input;

		public LumberTextBox() {
			input="";
		}

		public void setText(String text) {
			input=text;
		}

		//draw each word until end of line is reached, after which start a new line
		//start a new line if it is a new message
		public void render(Graphics g) {

			g.setColor(backColor);
			g.fillRect(x, y, width, height);

			g.setColor(Color.white);						 //text color
			FontMetrics fm = g.getFontMetrics();
			int row = y+fm.getHeight();						 //row on which baseline of text will be drawn
			StringTokenizer st = new StringTokenizer(input); //parses a string into separate words
			StringBuffer oneLine = new StringBuffer(); 		 //words are added to one string until line width exceeds textbox width

			//loop through each word, deciding which line to place it
			while(st.hasMoreTokens()) {
				String word = st.nextToken();

				if(word!=null) {
					//if new message, place on next line
					if(word.equals(Client.NEWLINE.trim())) {
						g.drawString(oneLine.toString(), x, row);
						row+=fm.getHeight();
						oneLine = new StringBuffer();
					}

					//if end of textbox not reached, add to same line
					else if(fm.stringWidth(oneLine.toString() + word) < width) {
						oneLine.append(word + " ");
					}
					//else draw the line and start a new one, placing the overreaching word on new line first
					else {
						g.drawString(oneLine.toString(), x, row);
						oneLine = new StringBuffer(word + " ");
						row += fm.getHeight();
					}
				}
			}
		}
	}
}